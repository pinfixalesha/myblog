package ru.yandex.practicum.yaBlog.servicetest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.yaBlog.YaBlogApplication;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.BlogsEntity;
import ru.yandex.practicum.yaBlog.mapping.BlogMapper;
import ru.yandex.practicum.yaBlog.model.BlogEditModel;
import ru.yandex.practicum.yaBlog.model.BlogModel;
import ru.yandex.practicum.yaBlog.model.BlogsModel;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;
import ru.yandex.practicum.yaBlog.service.BlogService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YaBlogApplication.class)
public class BlogServiceTest {

    @MockitoBean(reset = MockReset.BEFORE)
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Captor
    private ArgumentCaptor<BlogEntity> blogEntityArgumentCaptor;

    @Test
    void findPageTest() {
        List<BlogsEntity> mockBlogs = new ArrayList<>();
        mockBlogs.add(getBlogsEntity(1L));
        mockBlogs.add(getBlogsEntity(2L));

        Mockito.when(blogRepository.findPage(1,10)).thenReturn(mockBlogs);

        List<BlogsModel> blogsModels = blogService.findPage(1,10);
        Mockito.verify(blogRepository, Mockito.times(1)).findPage(1,10);

        assertEquals(2, blogsModels.size());
        assertEquals(mockBlogs.get(0).getTitle(), blogsModels.get(0).getTitle());
        assertEquals(mockBlogs.get(1).getTitle(), blogsModels.get(1).getTitle());
    }

    @Test
    void findPageByTagTest() {
        List<BlogsEntity> mockBlogs = new ArrayList<>();
        mockBlogs.add(getBlogsEntity(1L));

        Mockito.when(blogRepository.findPageByTag(1,10,"tag1")).thenReturn(mockBlogs);

        List<BlogsModel> blogsModels = blogService.findPageByTag(1,10,"tag1");
        Mockito.verify(blogRepository, Mockito.times(1)).findPageByTag(1,10,"tag1");

        assertEquals(1, blogsModels.size());
        assertEquals(mockBlogs.get(0).getTitle() , blogsModels.get(0).getTitle());
        assertEquals(mockBlogs.get(0).getTags() , blogsModels.get(0).getTags());
        assertEquals(mockBlogs.get(0).getPicture() , blogsModels.get(0).getPicture());
    }

    @Test
    void saveTest() {
        BlogEditModel testBlog = getBlogsEditModel(1L);
        blogService.save(testBlog);
        Mockito.verify(blogRepository, Mockito.times(1)).save(blogMapper.mapToEntity(testBlog));

        // Проверка и захват аргументов
        Mockito.verify(blogRepository).save(blogEntityArgumentCaptor.capture());
        assertEquals(blogMapper.mapToEntity(testBlog), blogEntityArgumentCaptor.getValue());
    }

    @Test
    void createTest() {
        BlogEditModel testBlog = getBlogsEditModel(1L);
        blogService.create(testBlog);
        Mockito.verify(blogRepository, Mockito.times(1)).create(blogMapper.mapToEntity(testBlog));

        // Проверка и захват аргументов
        Mockito.verify(blogRepository).create(blogEntityArgumentCaptor.capture());
        assertEquals(blogMapper.mapToEntity(testBlog), blogEntityArgumentCaptor.getValue());
    }

    @Test
    void getByIdTest() {
        BlogEntity mockBlog = getBlogEntity(1L);

        Mockito.when(blogRepository.getById(1L)).thenReturn(mockBlog);

        BlogModel blogModel = blogService.getById(1L);
        Mockito.verify(blogRepository, Mockito.times(1)).getById(1L);

        assertEquals(blogMapper.mapToModel(mockBlog), blogModel);
    }


    private BlogEditModel getBlogsEditModel(Long idBlog) {
        return BlogEditModel.builder()
                .id(idBlog)
                .title("Title "+idBlog)
                .text("Text "+idBlog)
                .picture(null)
                .tags("#tag"+idBlog)
                .build();
    }

    private BlogsEntity getBlogsEntity(Long idBlog) {
        return BlogsEntity.builder()
                .id(idBlog)
                .title("Title "+idBlog)
                .text("Text "+idBlog)
                .picture(null)
                .tags("#tag"+idBlog)
                .commentCount(1)
                .likeCount(2)
                .build();
    }

    private BlogEntity getBlogEntity(Long idBlog) {
        return BlogEntity.builder()
                .id(idBlog)
                .title("Title "+idBlog)
                .text("Text "+idBlog)
                .picture(null)
                .tags("#tag"+idBlog)
                .build();
    }

}
