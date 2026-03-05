package com.hospital.waste.mapper;

import com.hospital.waste.entity.SysWarning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 预警记录Mapper接口
 */
@Mapper
public interface SysWarningMapper {

    /**
     * 查询所有预警记录
     */
    List<SysWarning> selectAll();

    /**
     * 根据ID查询
     */
    SysWarning selectById(@Param("id") Long id);

    /**
     * 插入记录
     */
    int insert(SysWarning sysWarning);

    /**
     * 更新记录
     */
    int update(SysWarning sysWarning);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<SysWarning> selectByStatus(@Param("status") Integer status);

    /**
     * 根据预警类型查询
     */
    List<SysWarning> selectByWarningType(@Param("warningType") String warningType);

    /**
     * 根据仓库ID查询
     */
    List<SysWarning> selectByWarehouseId(@Param("warehouseId") Long warehouseId);

    /**
     * 根据预警级别查询
     */
    List<SysWarning> selectByWarningLevel(@Param("warningLevel") Integer warningLevel);

    /**
     * 查询未处理的预警
     */
    List<SysWarning> selectUnprocessed();

    /**
     * 根据废物ID和预警类型查询
     */
    List<SysWarning> selectByWasteIdAndType(@Param("wasteId") Long wasteId, @Param("warningType") String warningType);
}
