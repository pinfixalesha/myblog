package ru.yandex.practicum.yaBlog.mvctest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.yaBlog.YaBlogApplication;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@SpringBootTest(classes = YaBlogApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
public class BlogListControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc; // Используется для отправки HTTP-запросов

    @Autowired
    private BlogRepository blogRepository;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();
        for (long id = 1L; id <= 15; id++) {
            BlogEntity blogEntity = BlogEntity.builder()
                    .id(id)
                    .title("Title Blog " + id)
                    .text("test Blog")
                    .tags("#Test #tag" + id)
                    .build();
            blogRepository.create(blogEntity);
        }
    }

    @Test
    void getFirstPageBlogs() throws Exception {
        mockMvc.perform(get("/blog")
                        .param("page", "1")
                        .param("size", "10")
                        .param("filter", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(xpath("//span").string("Текущая страница 1 из 2"))
                .andExpect(xpath("//div[contains(@class, 'post')]").nodeCount(10));
    }

    @Test
    void getNextPageBlogs() throws Exception {
        mockMvc.perform(get("/blog")
                        .param("page", "2")
                        .param("size", "10")
                        .param("filter", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(xpath("//span").string("Текущая страница 2 из 2"))
                .andExpect(xpath("//div[contains(@class, 'post')]").nodeCount(5));
    }

    @Test
    void postFilter() throws Exception {
        mockMvc.perform(get("/blog")
                        .param("page", "1")
                        .param("size", "10")
                        .param("filter", "tag10"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(xpath("//span").string("Текущая страница 1 из 1"))
                .andExpect(xpath("//div[contains(@class, 'post')]").nodeCount(1))
                .andExpect(xpath("//div/h2/a").string("Title Blog 10"));
    }

}
