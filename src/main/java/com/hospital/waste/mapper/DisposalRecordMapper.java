package com.hospital.waste.mapper;

import com.hospital.waste.entity.DisposalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 处置记录Mapper接口
 */
@Mapper
public interface DisposalRecordMapper {

    /**
     * 查询所有处置记录
     */
    List<DisposalRecord> selectAll();

    /**
     * 根据ID查询
     */
    DisposalRecord selectById(@Param("id") Long id);

    /**
     * 根据废物ID查询
     */
    DisposalRecord selectByWasteId(@Param("wasteId") Long wasteId);

    /**
     * 插入记录
     */
    int insert(DisposalRecord record);

    /**
     * 更新记录
     */
    int update(DisposalRecord record);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<DisposalRecord> selectByStatus(@Param("status") Integer status);

    /**
     * 统计指定日期范围内的处置记录数量
     */
    int countByDateRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
