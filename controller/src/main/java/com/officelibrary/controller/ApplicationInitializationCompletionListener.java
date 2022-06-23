package com.officelibrary.controller;

import com.officelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationInitializationCompletionListener {

    private final UserService userService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userService.addAdmin();
    }
}