package ru.yandex.practicum.yaBlog.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.yandex.practicum.yaBlog.model.BlogEditModel;
import ru.yandex.practicum.yaBlog.model.BlogModel;
import ru.yandex.practicum.yaBlog.model.BlogsModel;
import ru.yandex.practicum.yaBlog.model.CommentsModel;
import ru.yandex.practicum.yaBlog.model.FilterModel;
import ru.yandex.practicum.yaBlog.service.BlogService;
import ru.yandex.practicum.yaBlog.service.CommentService;
import ru.yandex.practicum.yaBlog.service.LikeService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class LikeController {

    private final static Integer DEFAULT_SIZE_PAGE = 10;

    @Autowired
    private final BlogService blogService;

    @Autowired
    private final CommentService commentService;

    @Autowired
    private final LikeService likeService;

    @PostMapping(value = "/{id}", params = "_method=like")
    public String addLike(@PathVariable(name = "id") Long blogId) {
        likeService.addLike(blogId);
        return "redirect:/blog/"+blogId;
    }

}
