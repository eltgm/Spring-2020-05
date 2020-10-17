package ru.otus.newsbot.service;

import ru.otus.newsbot.domain.NewsUser;

public interface UserRegistrationService {
    NewsUser registration(NewsUser newsUser);

}
