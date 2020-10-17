package ru.otus.newsbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.newsbot.domain.NewsUser;
import ru.otus.newsbot.exception.UserNotFoundException;
import ru.otus.newsbot.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserRegistrationService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var userOptional = usersRepository.findByUsername(username);

        return userOptional.orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));
    }

    @Override
    public NewsUser registration(NewsUser newsUser) {
        newsUser.setPassword(new BCryptPasswordEncoder().encode(newsUser.getPassword()));

        return usersRepository.save(newsUser);
    }
}
