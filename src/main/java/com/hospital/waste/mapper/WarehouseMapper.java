package com.hospital.waste.mapper;

import com.hospital.waste.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 仓库Mapper接口
 */
@Mapper
public interface WarehouseMapper {

    /**
     * 查询所有仓库
     */
    List<Warehouse> selectAll();

    /**
     * 根据ID查询
     */
    Warehouse selectById(@Param("id") Long id);

    /**
     * 根据编码查询
     */
    Warehouse selectByWarehouseCode(@Param("warehouseCode") String warehouseCode);

    /**
     * 插入记录
     */
    int insert(Warehouse warehouse);

    /**
     * 更新记录
     */
    int update(Warehouse warehouse);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<Warehouse> selectByStatus(@Param("status") Integer status);
}
