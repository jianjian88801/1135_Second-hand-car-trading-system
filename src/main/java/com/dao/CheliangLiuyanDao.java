package com.dao;

import com.entity.CheliangLiuyanEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.CheliangLiuyanView;

/**
 * 汽车留言 Dao 接口
 *
 * @author 
 */
public interface CheliangLiuyanDao extends BaseMapper<CheliangLiuyanEntity> {

   List<CheliangLiuyanView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
