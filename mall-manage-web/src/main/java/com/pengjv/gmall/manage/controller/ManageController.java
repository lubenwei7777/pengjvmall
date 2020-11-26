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


    //xhr.js:178 POST http://localhost:8082/baseSaleAttrList
    @PostMapping("/baseSaleAttrList")
    public List<BaseSaleAttr> baseSaleAttrList(){
        return manageService.getBaseSaleAttrList();

    }

    //xhr.js:178 GET http://localhost:8082/spuList?catalog3Id=61
    @GetMapping("/spuList")
    public List<SpuInfo> getSpuList(@RequestParam("catalog3Id") String catalog3Id){
        return manageService.getSpuListByCataId(catalog3Id);
    }





    //http://localhost:8082/saveSpuInfo
    @PostMapping("/saveSpuInfo")
    public String saveSpuInfo(@RequestBody SpuInfo spuInfo){

        manageService.saveSpuInfo(spuInfo);

        return "ok";
    }



    //xhr.js:178 GET http://localhost:8082/spuImageList?spuId=60
    @GetMapping("/spuImageList")
    public List<SpuImage> getSpuImageList(@RequestParam("spuId") String spuId){
        return manageService.getSpuImageListBySpuId(spuId);
    }



    //xhr.js:178 GET http://localhost:8082/spuSaleAttrList?spuId=60
    @GetMapping("/spuSaleAttrList")
    public List<SpuSaleAttr> getSpuSaleAttrList(@RequestParam("spuId") String spuId){

        return manageService.getSpuSaleAttrListBySpuId(spuId);
    }





}
