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
import ru.yandex.practicum.yaBlog.configuration.FileuploadConfiguration;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.CommentEntity;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;
import ru.yandex.practicum.yaBlog.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = YaBlogApplication.class)
@SpringJUnitConfig(classes = {DataSourceConfiguration.class, FileuploadConfiguration.class})
@TestPropertySource(locations = "classpath:application.yml")
public class BlogControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc; // Используется для отправки HTTP-запросов

    @Autowired
    private BlogRepository blogRepository;


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
    void getFirstPageBlogs() throws Exception {
    }

    @Test
    void getNextPageBlogs() throws Exception {
    }

    @Test
    void postFilter() throws Exception {
    }

    @Test
    void getBlog() throws Exception {
    }

    @Test
    void postDeleteBlog() throws Exception {
    }

    @Test
    void postEditBlog() throws Exception {
    }

    @Test
    void postNewBlog() throws Exception {
    }

}
