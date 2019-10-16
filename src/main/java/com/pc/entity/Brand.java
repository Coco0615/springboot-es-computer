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
@Document(indexName = "es5",type = "brand")
public class Brand implements Serializable {
    @Id
    private Long brand_id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String brand_name;
}
