package ru.yandex.practicum.yaBlog.mapping;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.BlogsEntity;
import ru.yandex.practicum.yaBlog.model.BlogEditModel;
import ru.yandex.practicum.yaBlog.model.BlogModel;
import ru.yandex.practicum.yaBlog.model.BlogsModel;

import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class BlogMapper {

    public BlogsModel mapToModel(BlogsEntity entity) {
        return BlogsModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .shotText(Arrays.stream(entity.getText().split("\n"))
                        .limit(1)
                        .collect(Collectors.joining("\n")))
                .picture(entity.getPicture())
                .tags(entity.getTags())
                .commentsCount(entity.getCommentCount())
                .likeCount(entity.getLikeCount())
                .build();
    }

    public BlogModel mapToModel(BlogEntity entity) {
        return BlogModel.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .text(entity.getText())
                .textByParagraph(Arrays.stream(entity.getText().split("\n"))
                        .collect(Collectors.toList()))
                .picture(entity.getPicture())
                .tags(entity.getTags())
                .likeCount(entity.getLikeCount())
                .build();
    }

    public BlogEntity mapToEntity(BlogEditModel blogEditModel) {
        BlogEntity blogEntity = BlogEntity.builder()
                .id(blogEditModel.getId())
                .title(blogEditModel.getTitle())
                .text(blogEditModel.getText())
                .tags(blogEditModel.getTags())
                .build();

        if ((blogEditModel.getPicture()!=null) &&
                (!StringUtils.isEmpty(blogEditModel.getPicture().getOriginalFilename()))) {
            try {
                blogEntity.setPicture(Base64.getEncoder().encodeToString(blogEditModel.getPicture().getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return blogEntity;
    }

}
