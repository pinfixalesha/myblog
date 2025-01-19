package ru.yandex.practicum.repository;

import org.h2.engine.User;
import ru.yandex.practicum.entities.BlogEntity;
import ru.yandex.practicum.entities.BlogsEntity;
import ru.yandex.practicum.entities.CommentEntity;

import java.util.List;

public interface CommentRepository {
    List<CommentEntity> findAll(Long blogId);

    void deleteById(Long id);

    void save(CommentEntity comment);

    void create(CommentEntity comment);

}
