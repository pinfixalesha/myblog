package ru.yandex.practicum.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entities.CommentEntity;

import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcNativeCommentRepository implements CommentRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CommentEntity> findAll(Long blogId) {
        return jdbcTemplate.query(
                "SELECT c.id, c.datetime, c.blog, c.comment "+
                        "FROM comments as c WHERE c.blog=? ORDER BY c.datetime DESC",
                new Object[]{blogId},
                (rs, rowNum) -> new CommentEntity(
                        rs.getLong("id"),
                        rs.getTimestamp("datetime"),
                        rs.getLong("blog"),
                        rs.getString("comment")
                ));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from comments where id = ?", id);
    }

    @Override
    public void save(CommentEntity comment) {
        jdbcTemplate.update("update comments SET comment=? WHERE id=?",
                comment.getComment(),comment.getId());
    }

    @Override
    public void create(CommentEntity comment) {
        jdbcTemplate.update("insert into comments(blog, comment) values ( ?,?);",
                comment.getBlog(),comment.getComment());
    }
}
