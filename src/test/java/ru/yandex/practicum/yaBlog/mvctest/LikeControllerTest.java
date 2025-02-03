package ru.yandex.practicum.yaBlog.mvctest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.yaBlog.YaBlogApplication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = YaBlogApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
public class LikeControllerTest {

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
    void postAddLike() throws Exception {
        var id = blogRepository.findPage(1, 10).get(0).getId();
        mockMvc.perform(post("/blog/" + id)
                        .param("_method", "like"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + id));
        assertEquals(1, blogRepository.getById(id).getLikeCount());
    }
}
