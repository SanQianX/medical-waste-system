package com.hospital.waste.mapper;

import com.hospital.waste.entity.TransferRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 转运记录Mapper接口
 */
@Mapper
public interface TransferRecordMapper {

    /**
     * 查询所有转运记录
     */
    List<TransferRecord> selectAll();

    /**
     * 根据ID查询
     */
    TransferRecord selectById(@Param("id") Long id);

    /**
     * 根据转运单号查询
     */
    TransferRecord selectByTransferNo(@Param("transferNo") String transferNo);

    /**
     * 插入记录
     */
    int insert(TransferRecord record);

    /**
     * 更新记录
     */
    int update(TransferRecord record);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<TransferRecord> selectByStatus(@Param("status") Integer status);

    /**
     * 统计指定日期范围内的转运记录数量
     */
    int countByDateRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
