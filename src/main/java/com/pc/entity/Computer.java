package com.pc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "es4",type = "computer")
public class Computer implements Serializable {
    @Id
    private Long computer_id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String computer_name;
    @Field(type = FieldType.Double)
    private Double computer_price;
    @Field(type = FieldType.Keyword)
    private String computer_img;
    @Field(type = FieldType.Long)
    private Long brand_id;
    private Brand brand;
}
