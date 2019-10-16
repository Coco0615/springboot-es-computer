package com.pc.repository;

import com.pc.entity.Computer;
import org.springframework.data.annotation.Id;

import java.util.List;

public interface CustomComputerRepository {
    List<Computer> select(Integer page,Integer limit,String name);
    List<Computer> selectPage(Integer page,Integer limit);
}
