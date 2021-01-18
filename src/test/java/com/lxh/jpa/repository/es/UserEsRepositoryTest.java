package com.lxh.jpa.repository.es;

import com.lxh.jpa.bean.UserEsEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserEsRepositoryTest {

    @Autowired
    private UserEsRepository userEsRepository;

    @Test
    void testSave() {
        UserEsEntity entity = new UserEsEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAge(50);
        entity.setLastName("东野圭吾");
        entity.setBirthDate(LocalDate.ofYearDay(1964, 200));
        entity.setCreateTime(LocalDate.now());
        entity.setUpdateTime(LocalDate.now());
        entity.setIsDeleted(false);
        UserEsEntity save = userEsRepository.save(entity);
        Optional<UserEsEntity> byId = userEsRepository.findById(entity.getId());
        assertNotNull(byId.isPresent(), "保存以后的要存在");
    }

}