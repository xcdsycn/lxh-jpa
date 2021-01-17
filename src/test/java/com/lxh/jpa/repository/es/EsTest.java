package com.lxh.jpa.repository.es;

import com.lxh.jpa.bean.UserEsEntity;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EsTest {
    /**
     * 是spring data es操作ES的一个接口，在4.x的版本它的默认实现是ElasticsearchRestTemplate
     */
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void testSave() {
        UserEsEntity user = new UserEsEntity();
        user.setLastName("张三");
        user.setAge(29);
        user.setBirthDate(LocalDate.ofYearDay(1989, 100));
        user.setId("1");
        user.setIsDeleted(false);
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDate.now());
        // 是4.x新增的一个参数，通过这个参数我们可以再操作ES的时候同时指定多个index。
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(user.getClass());

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(user.getId())
                .withObject(user)
                .build();
        String documentId = elasticsearchOperations.index(indexQuery, indexCoordinates);
    }

    @Test
    public void testQuery() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new MatchAllQueryBuilder())
                .build();

        SearchHits<UserEsEntity> searchHits = elasticsearchOperations.search(searchQuery, UserEsEntity.class);
        long count = searchHits.getTotalHits();
        System.out.println("==> 查到的数量：" + count);
        List<SearchHit<UserEsEntity>> list = searchHits.getSearchHits();
        for (SearchHit hit:list) {
            System.out.println("==> 记录：" + hit.getContent());
        }
    }
}
