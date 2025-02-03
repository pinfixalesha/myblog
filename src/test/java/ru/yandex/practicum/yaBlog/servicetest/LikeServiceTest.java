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
import ru.yandex.practicum.yaBlog.repository.LikeRepository;
import ru.yandex.practicum.yaBlog.service.LikeService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = YaBlogApplication.class)
public class LikeServiceTest {

    @MockitoBean(reset = MockReset.BEFORE)
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;

    @Captor
    private ArgumentCaptor<Long> addLikeCaptor;

    @Test
    void addLikeTest() {
        likeService.addLike(10L);
        //После вызова likeService.addLike(1L), используется метод Mockito.verify() для проверки, что метод addLike(1L) был вызван один раз
        Mockito.verify(likeRepository, Mockito.times(1)).addLike(10L);

        // Проверка и захват аргументов
        Mockito.verify(likeRepository).addLike(addLikeCaptor.capture());
        assertEquals(10L, addLikeCaptor.getValue());
    }


}
