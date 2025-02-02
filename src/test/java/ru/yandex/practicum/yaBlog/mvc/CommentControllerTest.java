package ru.yandex.practicum.yaBlog.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.yaBlog.YaBlogApplication;
import ru.yandex.practicum.yaBlog.configuration.DataSourceConfiguration;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.CommentEntity;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;
import ru.yandex.practicum.yaBlog.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = YaBlogApplication.class)
@SpringJUnitConfig(classes = {DataSourceConfiguration.class})
@TestPropertySource(locations = "classpath:application.yml")
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc; // Используется для отправки HTTP-запросов

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();
        BlogEntity blogEntity = BlogEntity.builder()
                .id(1L)
                .title("Title Blog")
                .text("test Blog")
                .build();
        blogRepository.create(blogEntity);
    }

    @Test
    void postNewComment() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        mockMvc.perform(post("/blog/" + idBlog)
                        .param("_method", "newComment")
                        .param("id", "")
                        .param("blog", idBlog.toString())
                        .param("comment", "New comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + idBlog));
        assertEquals(1, blogRepository.findPage(1, 10).get(0).getCommentCount());
        assertEquals(1, commentRepository.findAll(idBlog).size());
        assertEquals("New comment", commentRepository.findAll(idBlog).get(0).getComment());
    }

    @Test
    void postEditComment() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        commentRepository.create(CommentEntity.builder()
                .comment("New comment")
                .blog(idBlog)
                .build());
        var idComment = commentRepository.findAll(idBlog).get(0).getId();
        mockMvc.perform(post("/blog/" + idBlog)
                        .param("_method", "editComment")
                        .param("id", idComment.toString())
                        .param("blog", idBlog.toString())
                        .param("comment", "Edit comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + idBlog));
        assertEquals(1, blogRepository.findPage(1, 10).get(0).getCommentCount());
        assertEquals(1, commentRepository.findAll(idBlog).size());
        assertEquals("Edit comment", commentRepository.findAll(idBlog).get(0).getComment());
    }

    @Test
    void postDeleteComment() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        commentRepository.create(CommentEntity.builder()
                .comment("New comment")
                .blog(idBlog)
                .build());
        var idComment = commentRepository.findAll(idBlog).get(0).getId();
        mockMvc.perform(post("/blog/" + idBlog)
                        .param("_method", "deleteComment")
                        .param("comment", idComment.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + idBlog));
        assertEquals(0, blogRepository.findPage(1, 10).get(0).getCommentCount());
        assertEquals(0, commentRepository.findAll(idBlog).size());
    }

}
