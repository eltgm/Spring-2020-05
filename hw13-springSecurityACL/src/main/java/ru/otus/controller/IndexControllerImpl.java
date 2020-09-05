package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexControllerImpl implements IndexController {

    @GetMapping("/")
    @Override
    public String getIndex() {
        return "index";
    }
}
