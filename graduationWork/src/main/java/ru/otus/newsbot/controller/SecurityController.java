package ru.otus.newsbot.controller;

import ru.otus.newsbot.domain.NewsUser;

import java.util.concurrent.Callable;

public interface SecurityController {
    Callable<NewsUser> registration(String username, String password);
}
