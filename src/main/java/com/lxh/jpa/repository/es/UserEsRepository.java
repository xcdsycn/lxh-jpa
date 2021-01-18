package com.lxh.jpa.repository.es;

import com.lxh.jpa.bean.UserEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserEsRepository extends ElasticsearchRepository<UserEsEntity, String> {
}
