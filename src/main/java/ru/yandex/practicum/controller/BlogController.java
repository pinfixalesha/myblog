package ru.yandex.practicum.controller;

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
import ru.yandex.practicum.model.BlogEditModel;
import ru.yandex.practicum.model.BlogModel;
import ru.yandex.practicum.model.BlogsModel;
import ru.yandex.practicum.model.CommentsModel;
import ru.yandex.practicum.model.FilterModel;
import ru.yandex.practicum.service.BlogService;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.LikeService;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final static Integer DEFAULT_SIZE_PAGE = 10;

    @Autowired
    private final BlogService blogService;

    @Autowired
    private final CommentService commentService;

    @Autowired
    private final LikeService likeService;

    @GetMapping
    public String getFirstPageBlogs(@RequestParam(required = false, name = "p") String page,
                                    @RequestParam(required = false, name = "s") String size,
                                    @RequestParam(required = false, name = "f") String filterTag,
                                    Model model) {
        int intPage, intSize;
        try {
            String safePage = Optional.ofNullable(page).orElse("1");
            intPage = Integer.parseInt(safePage);
        } catch (Exception e) {
            intPage = 1;
        }
        try {
            String safeSize = Optional.ofNullable(size).orElse("0");
            intSize = Integer.parseInt(safeSize);
        } catch (Exception e) {
            intSize = DEFAULT_SIZE_PAGE;
        }

        return getBlogs(intPage, intSize, filterTag, model);
    }

    private String getBlogs(Integer page, Integer size, String filterTags, Model model) {
        if (page < 1) page = 1;
        if (size < 10) size = DEFAULT_SIZE_PAGE;
        String safeFilterTags = Optional.ofNullable(filterTags).orElse("").replaceAll("\\s", "");

        List<BlogsModel> blogs;
        Integer totalBlogs;
        if (StringUtils.isEmpty(safeFilterTags)) {
            blogs = blogService.findPage(page, size);
            totalBlogs = blogService.getCount();
        } else {
            blogs = blogService.findPageByTag(page, size, safeFilterTags);
            totalBlogs = blogService.getCountByTag(safeFilterTags);
        }
        Integer totalPages = (totalBlogs / size) + (totalBlogs % size > 0 ? 1 : 0);
        model.addAttribute("blogs", blogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("sizePage", size);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("filterTags", safeFilterTags);
        return "blogs";
    }

    @PostMapping
    public String filter(@ModelAttribute FilterModel filterModel, Model model) {
        return getBlogs(filterModel.getCurrentPage(), filterModel.getSizePage(), filterModel.getTagFilter(), model);
    }

    @GetMapping("/{id}")
    public String getBlog(@PathVariable(name = "id") Long id, Model model) {
        BlogModel blog=blogService.getById(id);
        List<CommentsModel> comments=commentService.findAll(id);
        model.addAttribute("blog", blog);
        model.addAttribute("comments", comments);
        return "blogView";
    }

    @PostMapping(value = "/{id}", params = "_method=like")
    public String addLike(@PathVariable(name = "id") Long blogId) {
        likeService.addLike(blogId);
        return "redirect:/blog/"+blogId;
    }

    @PostMapping(value = "/{id}", params = "_method=deleteComment")
    public String deleteComment(@PathVariable(name = "id") Long blogId,@RequestParam(name = "comment") Long commentId) {
        commentService.deleteById(commentId);
        return "redirect:/blog/"+blogId;
    }

    @PostMapping(value = "/{id}", params = "_method=editComment")
    public String editComment(@PathVariable(name = "id") Long blogId,
                              CommentsModel commentsModel) {
        commentService.save(commentsModel);
        return "redirect:/blog/"+blogId;
    }

    @PostMapping(value = "/{id}", params = "_method=newComment")
    public String newComment(@PathVariable(name = "id") Long blogId,
                              CommentsModel commentsModel) {
        commentService.create(commentsModel);
        return "redirect:/blog/"+blogId;
    }


    @PostMapping(value = "/{id}", params = "_method=delete")
    public String deleteBlog(@PathVariable(name = "id") Long blogId) {
        blogService.deleteById(blogId);
        return "redirect:/blog";
    }

    @PostMapping(value = "/new")
    public String newBlog(BlogEditModel blogEditModel) {
        if (!StringUtils.isEmpty(blogEditModel.getTitle())) {
            blogService.create(blogEditModel);
        }
        return "redirect:/blog";
    }

    @PostMapping(value = "/edit")
    public String editBlog(BlogEditModel blogEditModel) {
        if (!StringUtils.isEmpty(blogEditModel.getTitle())) {
            blogService.save(blogEditModel);
        }
        return "redirect:/blog/"+blogEditModel.getId();
    }
}
