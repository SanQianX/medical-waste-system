package com.hospital.waste.mapper;

import com.hospital.waste.entity.WasteRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 废物记录Mapper接口
 */
@Mapper
public interface WasteRecordMapper {

    /**
     * 查询所有废物记录
     */
    List<WasteRecord> selectAll();

    /**
     * 根据ID查询
     */
    WasteRecord selectById(@Param("id") Long id);

    /**
     * 根据废物编码查询
     */
    WasteRecord selectByWasteCode(@Param("wasteCode") String wasteCode);

    /**
     * 插入记录
     */
    int insert(WasteRecord record);

    /**
     * 更新记录
     */
    int update(WasteRecord record);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<WasteRecord> selectByStatus(@Param("status") Integer status);

    /**
     * 根据科室查询
     */
    List<WasteRecord> selectByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * 统计指定日期范围内的废物记录数量
     */
    int countByDateRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 统计指定科室在日期范围内的废物记录数量
     */
    int countByDepartmentAndDateRange(@Param("departmentId") Long departmentId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
}
