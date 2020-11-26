package com.pengjv.gmall.manage.mapper;

import com.pengjv.gmall.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {

    public List<BaseAttrInfo> getBaseAttrInfoByCatalog3Id(String catalog3Id);
}
