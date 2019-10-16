package com.pc.controller;

import com.pc.entity.Brand;
import com.pc.entity.Computer;
import com.pc.repository.BrandRepository;
import com.pc.repository.ComputerRepository;
import com.pc.repository.CustomComputerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("computer")
public class ComputerController {
    Logger logger = LoggerFactory.getLogger(ComputerController.class);
    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private CustomComputerRepository customComputerRepository;
    @Autowired
    private BrandRepository brandRepository;
    long id = 2;

    @RequestMapping("select")
    @ResponseBody
    public Map select(@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10") Integer limit,
                      String name){
        Map map = new HashMap();

        List<Computer> computers = null;
        if(name == null||name == ""){
            computers = customComputerRepository.selectPage(page, limit);
        }else {
            computers = customComputerRepository.select(page, limit, name);
        }
        long count = 0;
        Iterable<Brand> all = brandRepository.findAll();
        List<Brand> brands = new ArrayList<>();
        for (Brand brand : all) {
            brands.add(brand);
        }

        for (Computer computer : computers) {
            count++;
            for (Brand brand : brands) {
                if(computer.getBrand_id()==brand.getBrand_id()){
                    computer.setBrand(new Brand(brand.getBrand_id(),brand.getBrand_name()));
                }
            }
        }
        map.put("code",0);
        map.put("msg","");
        map.put("count",count);
        map.put("data",computers);
        return map;
     }
     @RequestMapping("upload")
     @ResponseBody
     public Map upload(MultipartFile file){
         logger.debug(file.getOriginalFilename());
         Map map = new HashMap();
         try {
             file.transferTo(new File("E:\\imgs",file.getOriginalFilename()));
             map.put("code",0);
             map.put("msg","");
             map.put("data",file.getOriginalFilename());
         } catch (IOException e) {
             e.printStackTrace();
         }
         return map;
     }
    @RequestMapping("add")
    @ResponseBody
     public Map add(Computer computer){
         Map map = new HashMap();
         logger.debug(""+computer);
         if(computer.getComputer_id() == null){
             id++;
             computer.setComputer_id(id);
         }
         try{
             computerRepository.save(computer);
             map.put("add",true);
         }catch (Exception e){
             map.put("add",false);
         }
         return map;
     }
     @RequestMapping("selectOne")
     @ResponseBody
     public Computer selectOne(Long id){
        logger.debug("11111111111111111111111");
         Optional<Computer> computer = computerRepository.findById(id);
         logger.debug("22222222222222222222");
         logger.debug(""+computer.get());
         return computer.get();
     }
    @RequestMapping("delete")
    @ResponseBody
    public Map delete(Integer[] ids){
        Map map = new HashMap();
        try{
            for (Integer integer : ids) {
                Computer computer = new Computer();
                computer.setComputer_id(Long.parseLong(integer.toString()));
                computerRepository.delete(computer);
            }
            map.put("delete",true);
        }catch (Exception e){
            map.put("delete",false);
        }
        return map;
    }

}
