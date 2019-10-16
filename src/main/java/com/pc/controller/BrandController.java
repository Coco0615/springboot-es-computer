package com.pc.controller;

import com.pc.entity.Brand;
import com.pc.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;
    @RequestMapping("select")
    @ResponseBody
    public List<Brand> select(){
        Iterable<Brand> all = brandRepository.findAll();
        List<Brand> list = new ArrayList<>();
        for (Brand brand : all) {
            list.add(brand);
        }
        return list;
    }
}

