package ru.yandex.practicum.mapping;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.entities.BlogEntity;
import ru.yandex.practicum.entities.BlogsEntity;
import ru.yandex.practicum.entities.CommentEntity;
import ru.yandex.practicum.model.BlogModel;
import ru.yandex.practicum.model.BlogsModel;
import ru.yandex.practicum.model.CommentsModel;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CommentMapper {

    public CommentsModel mapToModel(CommentEntity entity){
        return CommentsModel.builder()
                .id(entity.getId())
                .blog(entity.getBlog())
                .comment(entity.getComment())
                .build();
    }

    public CommentEntity mapToEntity(CommentsModel entity){
        return CommentEntity.builder()
                .id(entity.getId())
                .blog(entity.getBlog())
                .comment(entity.getComment())
                .build();
    }

}
