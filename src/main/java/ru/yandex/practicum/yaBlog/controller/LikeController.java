package ru.yandex.practicum.yaBlog.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.yaBlog.service.LikeService;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class LikeController {

    @Autowired
    private final LikeService likeService;

    @PostMapping(value = "/{id}", params = "_method=like")
    public String addLike(@PathVariable(name = "id") Long blogId) {
        likeService.addLike(blogId);
        return "redirect:/blog/" + blogId;
    }

}
