package com.hospital.waste.mapper;

import com.hospital.waste.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志Mapper接口
 */
@Mapper
public interface OperationLogMapper {

    /**
     * 查询所有操作日志
     */
    List<OperationLog> selectAll();

    /**
     * 根据ID查询
     */
    OperationLog selectById(@Param("id") Long id);

    /**
     * 插入日志
     */
    int insert(OperationLog log);

    /**
     * 根据用户ID查询
     */
    List<OperationLog> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据模块查询
     */
    List<OperationLog> selectByModule(@Param("module") String module);

    /**
     * 根据时间范围查询
     */
    List<OperationLog> selectByDateRange(@Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    /**
     * 删除历史日志
     */
    int deleteByDateRange(@Param("beforeTime") LocalDateTime beforeTime);
}
