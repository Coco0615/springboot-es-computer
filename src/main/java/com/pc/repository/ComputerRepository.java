package com.pc.repository;

import com.pc.entity.Computer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ComputerRepository extends ElasticsearchRepository<Computer,Long> {

}
