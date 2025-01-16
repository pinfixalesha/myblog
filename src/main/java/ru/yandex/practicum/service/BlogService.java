package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.BlogModel;
import ru.yandex.practicum.repository.BlogRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BlogService {

    @Autowired
    private final BlogRepository blogRepository;

    public List<BlogModel> findPage(Integer number, Integer size) {
        return blogRepository.findPage(number,size);
    }



}
