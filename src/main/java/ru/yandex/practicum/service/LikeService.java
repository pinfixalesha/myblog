package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.repository.LikeRepository;

@Service
@AllArgsConstructor
public class LikeService {

    @Autowired
    private final LikeRepository likeRepository;

    public void addLike(Long blog) {
        likeRepository.addLike(blog);
    }

}
