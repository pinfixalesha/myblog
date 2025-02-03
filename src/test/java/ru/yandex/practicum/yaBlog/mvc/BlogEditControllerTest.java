package ru.yandex.practicum.yaBlog.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.yaBlog.YaBlogApplication;
import ru.yandex.practicum.yaBlog.configuration.DataSourceConfiguration;
import ru.yandex.practicum.yaBlog.configuration.FileuploadConfiguration;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.BlogsEntity;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@SpringBootTest(classes = YaBlogApplication.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class, FileuploadConfiguration.class})
@TestPropertySource(locations = "classpath:application.yml")
public class BlogEditControllerTest {

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
    void getBlog() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        mockMvc.perform(get("/blog/" + idBlog))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(xpath("//h1").string("Title Blog"))
                .andExpect(xpath("//p").string("test Blog"))
                .andExpect(xpath("//div/form/button/span").string("0"));
    }

    @Test
    void postDeleteBlog() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        mockMvc.perform(post("/blog/" + idBlog)
                        .param("_method", "delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
        assertEquals(0, blogRepository.findPage(1, 10).size());
    }

    @Test
    void postEditBlog() throws Exception {
        var idBlog = blogRepository.findPage(1, 10).get(0).getId();
        mockMvc.perform(multipart("/blog/edit")
                        .file("picture", null)
                        .param("id", idBlog.toString())
                        .param("title", "Edit Title Blog")
                        .param("text", "Edit text Blog")
                        .param("tags", "#tagEditText"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/" + idBlog));
        List<BlogsEntity> newBlog = blogRepository.findPageByTag(1, 10, "tagEditText");

        assertEquals(1, newBlog.size());
        if (newBlog.size() > 0) {
            assertEquals("Edit Title Blog", newBlog.get(0).getTitle());
            assertEquals("Edit text Blog", newBlog.get(0).getText());
        }

    }

    @Test
    void postNewBlog() throws Exception {
        mockMvc.perform(multipart("/blog/new")
                        .file("picture", null)
                        .param("id", "")
                        .param("title", "New Title Blog")
                        .param("text", "New text Blog")
                        .param("tags", "#tagNewText"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog"));
        List<BlogsEntity> newBlog = blogRepository.findPageByTag(1, 10, "tagNewText");

        assertEquals(1, newBlog.size());
        if (newBlog.size() > 0) {
            assertEquals("New Title Blog", newBlog.get(0).getTitle());
            assertEquals("New text Blog", newBlog.get(0).getText());
        }
    }

}
