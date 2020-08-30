package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final var user = usersRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с таким username не найден");
        }

        return user;
    }
}
