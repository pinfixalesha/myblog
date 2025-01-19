package ru.yandex.practicum.repository;

import ru.yandex.practicum.entities.CommentEntity;

import java.util.List;

public interface CommentRepository {
    List<CommentEntity> findAll(Long blogId);

    void deleteById(Long id);

    void save(CommentEntity comment);

    void create(CommentEntity comment);

}
