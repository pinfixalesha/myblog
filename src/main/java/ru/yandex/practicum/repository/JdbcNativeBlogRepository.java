package ru.yandex.practicum.repository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entities.BlogsEntity;

import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcNativeBlogRepository implements BlogRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<BlogsEntity> findPage(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        return jdbcTemplate.query(
                "SELECT b.id, b.datetime, b.title, b.text, b.picture, b.tags, " +
                        "(SELECT count(id) FROM comments as c WHERE c.blog=b.id) ccomments, "+
                        "(SELECT count(id) FROM likes as l WHERE l.blog=b.id) clike "+
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
        Integer offset = (page - 1) * size;
        filterTags="%#"+filterTags.toUpperCase()+"%";
        return jdbcTemplate.query(
                "SELECT b.id, b.datetime, b.title, b.text, b.picture, b.tags, " +
                        "(SELECT count(id) FROM comments as c WHERE c.blog=b.id) ccomments, "+
                        "(SELECT count(id) FROM likes as l WHERE l.blog=b.id) clike "+
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
        filterTags="%#"+filterTags.toUpperCase()+"%";
        return jdbcTemplate.queryForObject("SELECT count(b.id) FROM blogs as b where upper(tags) like ?",
                new Object[]{filterTags},
                Integer.class);
    }


}
