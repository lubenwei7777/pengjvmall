package com.pengjv.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pengjv.gmall.bean.*;
import com.pengjv.gmall.service.ManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ManageController {

    @Reference
    ManageService manageService;


    @PostMapping("/getCatalog1")
    public List<BaseCatalog1> getCatalog1List(){

        return manageService.getCatalog1();
    }

    //getCatalog2?catalog1Id=5
    @PostMapping("/getCatalog2")
    public List<BaseCatalog2> getCatalog2(@RequestParam("catalog1Id") String catalog1Id){
        return manageService.getCatalog2(catalog1Id);
    }


    //getCatalog3?catalog2Id=23
    @PostMapping("/getCatalog3")
    public List<BaseCatalog3> getCatalog3(@RequestParam("catalog2Id") String catalog2Id){
        return manageService.getCatalog3(catalog2Id);
    }


    @GetMapping("/attrInfoList")
    public List<BaseAttrInfo> spuList(@RequestParam("catalog3Id") String catalog3Id){
        return manageService.getAttrList(catalog3Id);
    }

    //xhr.js:178 POST http://localhost:8082/saveAttrInfo
    @PostMapping("/saveAttrInfo")
    public String saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        manageService.saveBaseAttrInfo(baseAttrInfo);
        return "SUCCESS";

    }

    //xhr.js:178 POST http://localhost:8082/getAttrValueList?attrId=96
    @PostMapping("/getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(@RequestParam("attrId") String attrId){
        return manageService.getAttrValueList(attrId);
    }

}
