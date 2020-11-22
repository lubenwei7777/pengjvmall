package com.pengjv.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pengjv.gmall.bean.*;
import com.pengjv.gmall.manage.mapper.*;
import com.pengjv.gmall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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


        Example example = new Example(BaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",cataLog3Id);
        List<BaseAttrInfo> list = baseAttrInfoMapper.selectByExample(example);

        return list;
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
}
