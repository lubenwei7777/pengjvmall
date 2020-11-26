package com.pengjv.gmall.service;

import com.pengjv.gmall.bean.*;

import java.util.List;
import java.util.Map;

public interface ManageService {

    //查询一级分类
    public List<BaseCatalog1> getCatalog1();

    //根据一级ID查询二级分类

    public List<BaseCatalog2> getCatalog2(String catalog1Id);
    //根据二级ID查询三级分类

    public List<BaseCatalog3> getCatalog3(String catalog2Id);

    //根据三级分类查询平台属性

    public List<BaseAttrInfo> getAttrList(String cataLog3Id);

    //保存平台属性
    void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo);


    //根据平台属性id查询平台属性详情
    List<BaseAttrValue> getAttrValueList(String attrId);

    //获得基本销售属性
    List<BaseSaleAttr> getBaseSaleAttrList();



    //保存spu
    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuInfo> getSpuListByCataId(String catalog3Id);

    List<SpuImage> getSpuImageListBySpuId(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrListBySpuId(String spuId);

    void saveSkuInfo(SkuInfo skuInfo);


    //查询sku info
    SkuInfo getSkuInfo(String skuId);


    List<SpuSaleAttr> getSpuSaleAttrListBySpuIdCheckSku(String skuId, String spuId);


    //根据spuId查询已有的spu涉及的sku销售属性值清单
    public Map getSkuValueIdsMap(String spuId);
}
