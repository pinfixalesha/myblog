package ru.yandex.practicum.mapping;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.entities.CommentEntity;
import ru.yandex.practicum.model.CommentsModel;

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
