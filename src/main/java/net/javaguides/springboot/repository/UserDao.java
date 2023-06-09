package net.javaguides.springboot.repository;

import net.javaguides.springboot.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    public static final String HASH_KEY = "User";
    @Autowired
    private RedisTemplate template;

    public User save(User user) {
        template.opsForHash().put(HASH_KEY, user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public User findUserById(int id) {
        return (User) template.opsForHash().get(HASH_KEY, id);
    }


    public String deleteUser(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "user removed !!";
    }
}
