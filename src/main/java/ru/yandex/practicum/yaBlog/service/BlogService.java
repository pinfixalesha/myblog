package ru.yandex.practicum.yaBlog.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.yaBlog.mapping.BlogMapper;
import ru.yandex.practicum.yaBlog.model.BlogEditModel;
import ru.yandex.practicum.yaBlog.model.BlogModel;
import ru.yandex.practicum.yaBlog.model.BlogsModel;
import ru.yandex.practicum.yaBlog.repository.BlogRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogService {

    @Autowired
    private final BlogRepository blogRepository;

    @Autowired
    private final BlogMapper blogMapping;

    public List<BlogsModel> findPage(Integer page, Integer size) {
        return blogRepository.findPage(page, size).stream()
                .map(blogMapping::mapToModel)
                .collect(Collectors.toList());
    }

    public List<BlogsModel> findPageByTag(Integer page, Integer size, String filterTags) {
        return blogRepository.findPageByTag(page, size,filterTags).stream()
                .map(blogMapping::mapToModel)
                .collect(Collectors.toList());
    }

    public Integer getCount() {
        return blogRepository.getCount();
    }


    public Integer getCountByTag(String filterTags) {
        return blogRepository.getCountByTag(filterTags);
    }

    public BlogModel getById(Long id) {
        return blogMapping.mapToModel(blogRepository.getById(id));
    }

    public void deleteById(Long id)
    {
        blogRepository.deleteById(id);
    }

    public void create(BlogEditModel blogEditModel)
    {
        blogRepository.create(blogMapping.mapToEntity(blogEditModel));
    }

    public void save(BlogEditModel blogEditModel)
    {
        blogRepository.save(blogMapping.mapToEntity(blogEditModel));
    }

}
