package com.pc.test;

import com.github.pagehelper.PageInfo;
import com.pc.Application;
import com.pc.entity.Brand;
import com.pc.entity.Computer;
import com.pc.repository.BrandRepository;
import com.pc.repository.ComputerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test2 {
    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Test
    public void T1(){
        System.out.println("computerRepository = " + computerRepository);
        Computer computer = new Computer();
        computer.setComputer_id(2L);
        computer.setComputer_name("222");
        computer.setComputer_price(7800.0);
       // computer.setComputer_img("111112222111111");
        computer.setBrand_id(1L);
        computerRepository.save(computer);
    }
    @Test
    public void T2(){
        Brand brand = new Brand(2L, "垃圾");
        brandRepository.save(brand);
    }
    @Test
    public void T3(){
        System.out.println("brandRepository ==" + brandRepository);
        Iterable<Brand> all = brandRepository.findAll();
        for (Brand brand : all) {
            System.out.println("brand = " + brand);
        }
    }
}
