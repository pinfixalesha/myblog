package ru.yandex.practicum.repository;

import ru.yandex.practicum.entities.BlogEntity;
import ru.yandex.practicum.entities.BlogsEntity;
import java.util.List;

public interface BlogRepository {
    List<BlogsEntity> findPage(Integer page, Integer size);

    Integer getCount();

    List<BlogsEntity> findPageByTag(Integer page, Integer size, String filterTags);

    Integer getCountByTag(String filterTags);

    BlogEntity getById(Long id);

    void deleteById(Long id);

    void save(BlogEntity blogEntity);

    void create(BlogEntity blogEntity);

}
