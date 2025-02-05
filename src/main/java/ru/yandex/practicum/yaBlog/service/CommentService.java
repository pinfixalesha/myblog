package ru.yandex.practicum.yaBlog.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.yaBlog.mapping.CommentMapper;
import ru.yandex.practicum.yaBlog.model.CommentsModel;
import ru.yandex.practicum.yaBlog.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final CommentMapper commentMapper;

    public List<CommentsModel> findAll(Long blog) {
        return commentRepository.findAll(blog).stream()
                .map(commentMapper::mapToModel)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id)
    {
        commentRepository.deleteById(id);
    }

    public void save(CommentsModel commentsModel)
    {
        commentRepository.save(commentMapper.mapToEntity(commentsModel));
    }

    public void create(CommentsModel commentsModel)
    {
        commentRepository.create(commentMapper.mapToEntity(commentsModel));
    }
}
