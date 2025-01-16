package ru.yandex.practicum.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.BlogModel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcNativeBlogRepository  implements BlogRepository  {

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<BlogModel> findPage(Integer number, Integer size) {
        return jdbcTemplate.query(
                "select id, datetime, title, clob, picture, tags from blogs limit 10 offset 0",
                (rs, rowNum) -> new BlogModel(
                        rs.getLong("id"),
                        rs.getTimestamp("datetime"),
                        rs.getString("title"),
                        rs.getString("clob"),
                        rs.getString("picture"),
                        rs.getString("tags")
                ));
    }
}
