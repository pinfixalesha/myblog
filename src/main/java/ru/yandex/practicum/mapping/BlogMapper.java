package ru.yandex.practicum.mapping;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.entities.BlogEntity;
import ru.yandex.practicum.entities.BlogsEntity;
import ru.yandex.practicum.model.BlogModel;
import ru.yandex.practicum.model.BlogsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class BlogMapper {

    public BlogsModel mapToModel(BlogsEntity entity){
        return BlogsModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .shotText(Arrays.stream(entity.getText().split("\n"))
                        .limit(3)
                        .collect(Collectors.joining("\n"))
                        .replace("\n","<br>"))
                .picture(entity.getPicture())
                .tags(entity.getTags())
                .commentsCount(entity.getCommentCount())
                .likeCount(entity.getLikeCount())
                .build();
    }

    public BlogModel mapToModel(BlogEntity entity){
        return BlogModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .text(entity.getText())
                .picture(entity.getPicture())
                .tags(entity.getTags())
                .likeCount(entity.getLikeCount())
                .build();
    }

}
