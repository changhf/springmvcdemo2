package com.changhf.dao.base;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 基础类 DAO
 * 
 * @author sen
 */
public interface IBaseDao<T> {

    /**
     * 获取 序列号 
     * @return
     */
    Long getIdValue();

    /**
     * 插入记录
     * @param obj
     * @return
     */
    int insert(T obj);

    /**
     * 插入记录(有效字段,即非空字段)
     * @param obj
     * @return
     */
    int insertSelective(T obj);

    /**
     * 物理删除记录
     * @param seq
     * @return
     */
    <K> int deleteByPrimaryKey(K seq);

    /**
     * 更新记录
     * @param obj
     * @return
     */
    int updateByPrimaryKey(T obj);

    /**
     * 更新记录(有效字段,即非空字段)
     * @param obj
     * @return
     */
    int updateByPrimaryKeySelective(T obj);

    /**
     * 根据主键 返回记录
     * @param seq
     * @return
     */
    <K> T selectByPrimaryKey(K seq);

    /**
     * 根据 条件返回记录
     * @param params
     * @return
     */
    T selectByParams(@Param(value = "params") Map<String, Object> params);

    /**
     * 查询 符合条件的记录总数
     * @param <P>
     * @param params
     * @return
     */
    <P> int selectCountByParams(@Param(value = "params") P params);

    /**
     * 分页查询 记录
     * @param <P>
     * @param params 查询条件
     * @param pageOffset 开始游标
     * @param pageSize 每页显示的数量
     * @return
     */
    <P> List<T> selectListByParams(@Param(value = "params") P params,
                                   @Param(value = "pageOffset") Integer pageOffset,
                                   @Param(value = "pageSize") Integer pageSize);

}
