package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.User;
import net.javaguides.springboot.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class RedisController {

    @Autowired
    private UserDao dao;

    @PostMapping
    public User save(@RequestBody User user) {
        return dao.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable int id) {
        return dao.findUserById(id);
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id)   {
        return dao.deleteUser(id);
    }
}
