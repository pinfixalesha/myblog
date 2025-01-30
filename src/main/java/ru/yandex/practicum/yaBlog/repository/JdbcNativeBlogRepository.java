package ru.yandex.practicum.yaBlog.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.yaBlog.entities.BlogEntity;
import ru.yandex.practicum.yaBlog.entities.BlogsEntity;

import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcNativeBlogRepository implements BlogRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<BlogsEntity> findPage(Integer page, Integer size) {
        int offset = (page - 1) * size;
        return jdbcTemplate.query(
                "SELECT b.id, b.datetime, b.title, b.text, b.picture, b.tags, " +
                        "(SELECT count(id) FROM comments as c WHERE c.blog=b.id) ccomments, " +
                        "(SELECT count(id) FROM likes as l WHERE l.blog=b.id) clike " +
                        "FROM blogs as b ORDER BY b.datetime DESC LIMIT ? OFFSET ?",
                new Object[]{size, offset},
                (rs, rowNum) -> new BlogsEntity(
                        rs.getLong("id"),
                        rs.getTimestamp("datetime"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getString("picture"),
                        rs.getString("tags"),
                        rs.getInt("clike"),
                        rs.getInt("ccomments")
                ));
    }

    @Override
    public Integer getCount() {
        return jdbcTemplate.queryForObject("SELECT count(b.id) FROM blogs as b", Integer.class);
    }

    @Override
    public List<BlogsEntity> findPageByTag(Integer page, Integer size, String filterTags) {
        int offset = (page - 1) * size;
        filterTags = "%#" + filterTags.toUpperCase() + "%";
        return jdbcTemplate.query(
                "SELECT b.id, b.datetime, b.title, b.text, b.picture, b.tags, " +
                        "(SELECT count(id) FROM comments as c WHERE c.blog=b.id) ccomments, " +
                        "(SELECT count(id) FROM likes as l WHERE l.blog=b.id) clike " +
                        "FROM blogs as b where upper(tags) like ? ORDER BY b.datetime DESC LIMIT ? OFFSET ?",
                new Object[]{filterTags, size, offset},
                (rs, rowNum) -> new BlogsEntity(
                        rs.getLong("id"),
                        rs.getTimestamp("datetime"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getString("picture"),
                        rs.getString("tags"),
                        rs.getInt("clike"),
                        rs.getInt("ccomments")
                ));
    }

    @Override
    public Integer getCountByTag(String filterTags) {
        filterTags = "%#" + filterTags.toUpperCase() + "%";
        return jdbcTemplate.queryForObject("SELECT count(b.id) FROM blogs as b where upper(tags) like ?",
                new Object[]{filterTags},
                Integer.class);
    }

    @Override
    public BlogEntity getById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT b.id, b.datetime, b.title, b.text, b.picture, b.tags, " +
                        "(SELECT count(id) FROM likes as l WHERE l.blog=b.id) likeCount " +
                        "FROM blogs as b where id=?",
                new Object[]{id},
                (rs, rowNum) -> new BlogEntity(
                        rs.getLong("id"),
                        rs.getTimestamp("datetime"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getString("picture"),
                        rs.getString("tags"),
                        rs.getInt("likeCount")
                ));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from comments where blog = ?; " +
                "delete from likes where blog = ?; " +
                "delete from blogs where id = ?; ", id, id, id);
    }

    @Override
    public void save(BlogEntity blogEntity) {
        jdbcTemplate.update("update blogs set title=?, text=?, picture=?, tags=? where id = ?;",
                blogEntity.getTitle(),
                blogEntity.getText(),
                blogEntity.getPicture(),
                blogEntity.getTags(),
                blogEntity.getId());
    }

    @Override
    public void create(BlogEntity blogEntity) {
        jdbcTemplate.update("insert into blogs(title, text, picture, tags) values (?, ?, ?, ?);",
                blogEntity.getTitle(),
                blogEntity.getText(),
                blogEntity.getPicture(),
                blogEntity.getTags());
    }


}
