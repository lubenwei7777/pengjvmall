package com.pengjv.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pengjv.gmall.bean.*;
import com.pengjv.gmall.manage.mapper.*;
import com.pengjv.gmall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageServiceImpl implements ManageService{



    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;
    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;
    @Autowired
    SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    SpuImageMapper spuImageMapper;
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {

        Example example = new Example(BaseCatalog2.class);
        example.createCriteria().andEqualTo("catalog1Id",catalog1Id);
        List<BaseCatalog2> list = baseCatalog2Mapper.selectByExample(example);
        return list;
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        Example example = new Example(BaseCatalog3.class);
        example.createCriteria().andEqualTo("catalog2Id",catalog2Id);
        List<BaseCatalog3> list = baseCatalog3Mapper.selectByExample(example);
        return list;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String cataLog3Id) {


        /*Example example = new Example(BaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",cataLog3Id);
        List<BaseAttrInfo> list = baseAttrInfoMapper.selectByExample(example);

        for (BaseAttrInfo baseAttrInfo : list) {
            String attrInfoId = baseAttrInfo.getId();
            Example example1 = new Example(BaseAttrValue.class);
            example1.createCriteria().andEqualTo("attrId",attrInfoId);
            List<BaseAttrValue> baseAttrValueList = baseAttrValueMapper.selectByExample(example1);
            baseAttrInfo.setAttrValueList(baseAttrValueList);

        }*/
        return baseAttrInfoMapper.getBaseAttrInfoByCatalog3Id(cataLog3Id);


    }

    @Override
    @Transactional
    public void saveBaseAttrInfo(BaseAttrInfo baseAttrInfo) {


        String id = baseAttrInfo.getId();

        if (!StringUtils.isEmpty(id) && baseAttrInfo.getId().length()>0){

            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);

        }else {

            baseAttrInfo.setId(null);
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //先全部删除
        Example example = new Example(BaseAttrValue.class);
        example.createCriteria().andEqualTo("attrId",id);
        baseAttrValueMapper.deleteByExample(example);

        //在统一保存
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue attrValue : attrValueList){
            attrValue.setAttrId(id);
            baseAttrValueMapper.insertSelective(attrValue);
        }




    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {

        Example example = new Example(BaseAttrValue.class);
        example.createCriteria().andEqualTo("attrId",attrId);
        List<BaseAttrValue> list = baseAttrValueMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {


        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存spu基本信息
        spuInfoMapper.insertSelective(spuInfo);

        String spuId = spuInfo.getId();

        // 图片信息

        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage image : spuImageList){
            image.setSpuId(spuId);
            spuImageMapper.insertSelective(image);
        }
        //销售属性

        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSpuId(spuId);
            spuSaleAttrMapper.insertSelective(spuSaleAttr);

            String attrId = spuSaleAttr.getSaleAttrId();

            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            for (SpuSaleAttrValue attrValue : spuSaleAttrValueList) {
                attrValue.setSaleAttrId(attrId);
                attrValue.setSpuId(spuId);
                spuSaleAttrValueMapper.insertSelective(attrValue);
            }
        }


        //销售属性值
    }

    @Override
    public List<SpuInfo> getSpuListByCataId(String catalog3Id) {

        Example example = new Example(SpuInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        List<SpuInfo> list = spuInfoMapper.selectByExample(example);

        return list;
    }

    @Override
    public List<SpuImage> getSpuImageListBySpuId(String spuId) {

        Example example = new Example(SpuImage.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SpuImage> list = spuImageMapper.selectByExample(example);


        return list;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListBySpuId(String spuId) {
        return spuSaleAttrMapper.getSpuSaleAttrListBySpuId(spuId);
    }

    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo skuInfo) {

        if (skuInfo.getId() == null || skuInfo.getId().length()==0){
            skuInfoMapper.insertSelective(skuInfo);
        }else {
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }

        String skuId = skuInfo.getId();

        SkuImage skuImage1 = new SkuImage();
        skuImage1.setSkuId(skuId);
        skuImageMapper.delete(skuImage1);


        SkuAttrValue skuAttrValue1 = new SkuAttrValue();
        skuAttrValue1.setSkuId(skuId);
        skuAttrValueMapper.delete(skuAttrValue1);

        SkuSaleAttrValue skuSaleAttrValue1 = new SkuSaleAttrValue();
        skuSaleAttrValue1.setSkuId(skuId);
        skuSaleAttrValueMapper.delete(skuSaleAttrValue1);


        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insertSelective(skuImage);
        }

        for (SkuAttrValue skuAttrValue : skuInfo.getSkuAttrValueList()) {

            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insertSelective(skuAttrValue);
        }

        for (SkuSaleAttrValue skuSaleAttrValue : skuInfo.getSkuSaleAttrValueList()) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
        }
    }

    @Override
    public SkuInfo getSkuInfo(String skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);

        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);
        skuInfo.setSkuImageList(skuImageList);

        SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuId);
        List<SkuAttrValue> skuAttrValueList = skuAttrValueMapper.select(skuAttrValue);
        skuInfo.setSkuAttrValueList(skuAttrValueList);

        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuId);
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuSaleAttrValueMapper.select(skuSaleAttrValue);
        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValueList);

        return skuInfo;
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListBySpuIdCheckSku(String skuId, String spuId) {
        return spuSaleAttrMapper.getSpuSaleAttrListBySpuIdCheckSku(skuId,spuId);
    }

    @Override
    public Map getSkuValueIdsMap(String spuId) {

        List<Map> mapList = skuSaleAttrValueMapper.getSaleAttrValuesBySpu(spuId);

        Map skuValueIdsMap = new HashMap();
        for (Map map : mapList) {
            Long skuIdl = (Long) map.get("sku_id");
            String skuId = skuIdl+"";
            String skuValueIds = (String) map.get("value_ids");
            skuValueIdsMap.put(skuValueIds,skuId);
        }
        return skuValueIdsMap;
    }


}
