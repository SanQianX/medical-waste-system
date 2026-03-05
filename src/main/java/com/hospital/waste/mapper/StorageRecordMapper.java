package com.hospital.waste.mapper;

import com.hospital.waste.entity.StorageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 贮存记录Mapper接口
 */
@Mapper
public interface StorageRecordMapper {

    /**
     * 查询所有贮存记录
     */
    List<StorageRecord> selectAll();

    /**
     * 根据ID查询
     */
    StorageRecord selectById(@Param("id") Long id);

    /**
     * 根据废物ID查询
     */
    StorageRecord selectByWasteId(@Param("wasteId") Long wasteId);

    /**
     * 插入记录
     */
    int insert(StorageRecord record);

    /**
     * 更新记录
     */
    int update(StorageRecord record);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<StorageRecord> selectByStatus(@Param("status") Integer status);
}
