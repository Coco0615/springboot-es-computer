package com.pc.repository;

import com.pc.entity.Brand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BrandRepository extends ElasticsearchRepository<Brand,Long> {

}
