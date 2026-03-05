package com.hospital.waste.mapper;

import com.hospital.waste.entity.WasteCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 废物类别Mapper接口
 */
@Mapper
public interface WasteCategoryMapper {

    /**
     * 查询所有废物类别
     */
    List<WasteCategory> selectAll();

    /**
     * 根据ID查询
     */
    WasteCategory selectById(@Param("id") Long id);

    /**
     * 根据编码查询
     */
    WasteCategory selectByCategoryCode(@Param("categoryCode") String categoryCode);

    /**
     * 插入记录
     */
    int insert(WasteCategory wasteCategory);

    /**
     * 更新记录
     */
    int update(WasteCategory wasteCategory);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<WasteCategory> selectByStatus(@Param("status") Integer status);

    /**
     * 根据类别类型查询
     */
    List<WasteCategory> selectByCategoryType(@Param("categoryType") String categoryType);
}
