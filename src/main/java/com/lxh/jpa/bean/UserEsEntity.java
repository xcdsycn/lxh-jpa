package com.lxh.jpa.bean;

import lombok.Data;
import lombok.ToString;
import org.elasticsearch.common.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "my_user")
@Data
@ToString
public class UserEsEntity implements Persistable<String> {
    @Id
    @Nullable
    private String id;

    @Field(value = "last-name", type = FieldType.Keyword)
    private String lastName;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Nullable
    @Field(name = "birth-date", type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate birthDate;

    @Field(type = FieldType.Boolean)
    private Boolean isDeleted;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate createTime;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate updateTime;

    @Override
    public boolean isNew() {
        return id == null || (createTime == null);
    }
}
