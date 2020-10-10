/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.otus.config.mongoAcl.mongodb;

import org.springframework.security.acls.domain.AccessControlEntryImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import ru.otus.config.mongoAcl.dao.AclRepository;
import ru.otus.config.mongoAcl.domain.DomainObjectPermission;
import ru.otus.config.mongoAcl.domain.MongoAcl;
import ru.otus.config.mongoAcl.domain.MongoSid;

import java.util.List;
import java.util.UUID;

/**
 * Provides a base MongoDB implementation of {@link MutableAclService}.
 * <p>
 * This implementation will map ACL related classes like {@link Acl}, {@link AccessControlEntry} and {@link Sid} to a
 * {@link MongoAcl} POJO class which is persisted or accessed via a MongoDB based aclRepository. This POJO will contain all
 * the ACL relevant data for a domain object in a non flat structure. Due to the non-flat structure lookups and updates
 * are relatively trivial compared to the SQL based {@link AclService} implementation.
 *
 * @author Ben Alex
 * @author Johannes Zlattinger
 * @author Roman Vottner
 * @since 4.3
 */
public class MongoDBMutableAclService extends MongoDBAclService implements MutableAclService {

    private final AclCache aclCache;

    public MongoDBMutableAclService(AclRepository repository, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(repository, lookupStrategy);
        this.aclCache = aclCache;
    }

    @Override
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        Assert.notNull(objectIdentity, "Object Identity required");

        List<MongoAcl> availableAcl =
                aclRepository.findByInstanceIdAndClassName(objectIdentity.getIdentifier(), objectIdentity.getType());

        if (null != availableAcl && !availableAcl.isEmpty()) {
            throw new AlreadyExistsException("Object identity '" + objectIdentity + "' already exists");
        }

        // Need to retrieve the current principal, in order to know who "owns" this ACL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalSid sid = new PrincipalSid(auth);

        MongoAcl mongoAcl = new MongoAcl(objectIdentity.getIdentifier().toString(),
                objectIdentity.getType(),
                UUID.randomUUID().toString(),
                new MongoSid(sid.getPrincipal()),
                null,
                true);

        aclRepository.save(mongoAcl);

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        Acl acl = readAclById(objectIdentity);
        Assert.isInstanceOf(MutableAcl.class, acl, "MutableAcl should be returned");

        return (MutableAcl) acl;
    }

    @Override
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {
        Assert.notNull(objectIdentity, "Object Identity required");
        Assert.notNull(objectIdentity.getIdentifier(),
                "Object Identity doesn't provide an identifier");

        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (deleteChildren) {
            if (children != null) {
                for (ObjectIdentity child : children) {
                    deleteAcl(child, true);
                }
            }
        } else if (!children.isEmpty()) {
            throw new ChildrenExistException("Cannot delete '" + objectIdentity + "' (has " + children.size() + " children)");
        }

        Long numRemoved = aclRepository.deleteByInstanceId(objectIdentity.getIdentifier());
        if (null == numRemoved || numRemoved < 1) {
            // TODO: log warning that no ACL was found for the domain object
        }

        // Clear the cache
        aclCache.evictFromCache(objectIdentity);
    }

    @Override
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {

        MongoAcl mongoAcl = aclRepository.findById(acl.getId().toString())
                .orElseThrow(() -> new NotFoundException("No entry for ACL " + acl.getId() + " found"));

        // Delete this ACL's ACEs in the acl_entry table
        mongoAcl.getPermissions().clear();

        for (AccessControlEntry _ace : acl.getEntries()) {
            AccessControlEntryImpl ace = (AccessControlEntryImpl) _ace;
            MongoSid sid = null;
            String aceId = (String) ace.getId();
            if (null == aceId) {
                aceId = UUID.randomUUID().toString();
            }
            if (ace.getSid() instanceof PrincipalSid) {
                PrincipalSid principal = (PrincipalSid) ace.getSid();
                sid = new MongoSid(principal.getPrincipal(), true);
            } else if (ace.getSid() instanceof GrantedAuthoritySid) {
                GrantedAuthoritySid grantedAuthority = (GrantedAuthoritySid) ace.getSid();
                sid = new MongoSid(grantedAuthority.getGrantedAuthority(), false);
            }
            DomainObjectPermission permission =
                    new DomainObjectPermission(aceId, sid, ace.getPermission().getMask(),
                            ace.isGranting(), ace.isAuditSuccess(), ace.isAuditFailure());
            mongoAcl.getPermissions().add(permission);
        }

        // Update the acl entry
        aclRepository.save(mongoAcl);

        // Clear the cache, including children
        clearCacheIncludingChildren(acl.getObjectIdentity());

        // Retrieve the ACL via superclass (ensures cache registration, proper retrieval etc)
        return (MutableAcl) readAclById(acl.getObjectIdentity());
    }

    private void clearCacheIncludingChildren(ObjectIdentity objectIdentity) {
        Assert.notNull(objectIdentity, "ObjectIdentity required");
        List<ObjectIdentity> children = findChildren(objectIdentity);
        if (children != null) {
            for (ObjectIdentity child : children) {
                clearCacheIncludingChildren(child);
            }
        }
        aclCache.evictFromCache(objectIdentity);
    }
}
