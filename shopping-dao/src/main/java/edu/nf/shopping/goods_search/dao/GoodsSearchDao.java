package edu.nf.shopping.goods_search.dao;

import edu.nf.shopping.goods_search.entity.GoodsInfo;

import java.util.List;

/**
 * @author 彭哥
 * @date 2020/3/28
 */
public interface GoodsSearchDao {

    /**
     * 搜索
     * @param name
     * @return
     */
    List<GoodsInfo> listGoodsSearchByName(String name);
}