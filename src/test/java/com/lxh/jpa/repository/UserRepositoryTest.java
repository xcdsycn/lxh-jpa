package com.lxh.jpa.repository;

import com.github.javafaker.Faker;
import com.lxh.jpa.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest  {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Faker faker;

    @Test
    public void baseTest() {
        User user = new User();
        user.setUsername("jpa");
        user.setPassword("123456");
        user.setDeleted(0);
        User save = userRepository.save(user);
        Assert.notNull(save.getId(),"The value must not be null");
    }

    @Test
    public void findByUsernameAndPassword() {
        System.out.println(faker.address().fullAddress());
        List<User> userList = userRepository.findByUsernameAndPassword("jpa", "123456");
        Assert.notEmpty(userList,"The list must not be notEmpty");
        userList.forEach(System.out::println);
    }
}