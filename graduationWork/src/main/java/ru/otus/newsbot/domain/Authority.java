package ru.otus.newsbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}

