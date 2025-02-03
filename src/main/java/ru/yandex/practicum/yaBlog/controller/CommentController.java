package ru.yandex.practicum.yaBlog.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.yaBlog.model.CommentsModel;
import ru.yandex.practicum.yaBlog.service.CommentService;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PostMapping(value = "/{id}", params = "_method=deleteComment")
    public String deleteComment(@PathVariable(name = "id") Long blogId, @RequestParam(name = "comment") Long commentId) {
        commentService.deleteById(commentId);
        return "redirect:/blog/" + blogId;
    }

    @PostMapping(value = "/{id}", params = "_method=editComment")
    public String editComment(@PathVariable(name = "id") Long blogId,
                              CommentsModel commentsModel) {
        commentService.save(commentsModel);
        return "redirect:/blog/" + blogId;
    }

    @PostMapping(value = "/{id}", params = "_method=newComment")
    public String newComment(@PathVariable(name = "id") Long blogId,
                             CommentsModel commentsModel) {
        commentService.create(commentsModel);
        return "redirect:/blog/" + blogId;
    }

}
