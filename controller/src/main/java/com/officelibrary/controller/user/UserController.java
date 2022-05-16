package com.officelibrary.controller.user;

import java.util.List;

import com.officelibrary.controller.user.model.PostUserDto;
import com.officelibrary.controller.user.model.UserDto;
import com.officelibrary.persistence.model.User;
import com.officelibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/moderators")
    public List<UserDto> getAllModerators() {
        return userService.getAllModerators()
            .stream()
            .map(UserDto::new)
            .toList();
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModerator(@PathVariable("id") String id) {
        userService.remove(id);
    }

    @PostMapping("/moderators")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addModerator(@RequestBody PostUserDto postUser) {
        User user = userService.addModerator(
            postUser.getNickname(),
            postUser.getPassword());
        return new UserDto(user);
    }
}
