package ru.yandex.practicum.repository;

import ru.yandex.practicum.entities.CommentEntity;

import java.util.List;

public interface LikeRepository {
    void addLike(Long blogId);
}
