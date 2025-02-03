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
import ru.yandex.practicum.yaBlog.entities.CommentEntity;
import ru.yandex.practicum.yaBlog.mapping.CommentMapper;
import ru.yandex.practicum.yaBlog.model.CommentsModel;
import ru.yandex.practicum.yaBlog.repository.CommentRepository;
import ru.yandex.practicum.yaBlog.service.CommentService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YaBlogApplication.class)
public class CommentServiceTest {

    @MockitoBean(reset = MockReset.BEFORE)
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Captor
    private ArgumentCaptor<CommentEntity> commentEntityArgumentCaptor;

    @Test
    void findAllTest() {
        List<CommentEntity> mockComments = new ArrayList<>();
        mockComments.add(getCommentEntity(1L));
        mockComments.add(getCommentEntity(2L));

        Mockito.when(commentRepository.findAll(1L)).thenReturn(mockComments);

        List<CommentsModel> commentsModels = commentService.findAll(1L);
        Mockito.verify(commentRepository, Mockito.times(1)).findAll(1L);

        assertEquals(2, commentsModels.size());
        assertEquals(mockComments.get(0).getComment(), commentsModels.get(0).getComment());
        assertEquals(mockComments.get(1).getComment(), commentsModels.get(1).getComment());
    }

    @Test
    void saveTest() {
        CommentsModel testComment = getCommentsModel(1L);
        commentService.save(testComment);
        Mockito.verify(commentRepository, Mockito.times(1)).save(commentMapper.mapToEntity(testComment));

        // Проверка и захват аргументов
        Mockito.verify(commentRepository).save(commentEntityArgumentCaptor.capture());
        assertEquals(commentMapper.mapToEntity(testComment), commentEntityArgumentCaptor.getValue());

    }

    @Test
    void createTest() {
        CommentsModel testComment = getCommentsModel(1L);
        commentService.create(testComment);
        Mockito.verify(commentRepository, Mockito.times(1)).create(commentMapper.mapToEntity(testComment));

        // Проверка и захват аргументов
        Mockito.verify(commentRepository).create(commentEntityArgumentCaptor.capture());
        assertEquals(commentMapper.mapToEntity(testComment), commentEntityArgumentCaptor.getValue());
    }


    private CommentsModel getCommentsModel(Long idComment) {
        return CommentsModel.builder()
                .id(idComment)
                .blog(1L)
                .comment("test comment " + idComment)
                .build();

    }

    private CommentEntity getCommentEntity(Long idComment) {
        return CommentEntity.builder()
                .id(idComment)
                .blog(1L)
                .comment("test comment " + idComment)
                .build();

    }


}
