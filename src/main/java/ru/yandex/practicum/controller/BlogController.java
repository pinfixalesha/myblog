package ru.yandex.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.model.BlogModel;
import ru.yandex.practicum.service.BlogService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private static Integer DEFAULT_SIZE_PAGE=10;
    @Autowired
    private final BlogService blogService;

    @GetMapping // GET запрос /users
    public String blogs(Model model) {
        List<BlogModel> blogs = blogService.findPage(0,DEFAULT_SIZE_PAGE);

        model.addAttribute("blogs", blogs);
        return "blogs";
    }
}
