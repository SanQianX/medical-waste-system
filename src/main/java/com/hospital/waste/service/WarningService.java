package com.hospital.waste.service;

import com.hospital.waste.entity.SysWarning;
import com.hospital.waste.mapper.SysWarningMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警管理业务层
 */
@Service
public class WarningService {

    @Autowired
    private SysWarningMapper sysWarningMapper;

    /**
     * 查询所有预警记录
     */
    public List<SysWarning> findAll() {
        return sysWarningMapper.selectAll();
    }

    /**
     * 根据ID查询
     */
    public SysWarning findById(Long id) {
        return sysWarningMapper.selectById(id);
    }

    /**
     * 创建预警记录
     */
    public SysWarning create(SysWarning warning) {
        sysWarningMapper.insert(warning);
        return warning;
    }

    /**
     * 更新预警记录
     */
    public boolean update(SysWarning warning) {
        return sysWarningMapper.update(warning) > 0;
    }

    /**
     * 删除预警记录
     */
    public boolean delete(Long id) {
        return sysWarningMapper.deleteById(id) > 0;
    }

    /**
     * 根据状态查询
     */
    public List<SysWarning> findByStatus(Integer status) {
        return sysWarningMapper.selectByStatus(status);
    }

    /**
     * 根据预警类型查询
     */
    public List<SysWarning> findByWarningType(String warningType) {
        return sysWarningMapper.selectByWarningType(warningType);
    }

    /**
     * 根据仓库ID查询
     */
    public List<SysWarning> findByWarehouseId(Long warehouseId) {
        return sysWarningMapper.selectByWarehouseId(warehouseId);
    }

    /**
     * 根据预警级别查询
     */
    public List<SysWarning> findByWarningLevel(Integer warningLevel) {
        return sysWarningMapper.selectByWarningLevel(warningLevel);
    }

    /**
     * 查询未处理的预警
     */
    public List<SysWarning> findUnprocessed() {
        return sysWarningMapper.selectUnprocessed();
    }

    /**
     * 处理预警
     */
    public boolean handleWarning(Long id) {
        SysWarning warning = new SysWarning();
        warning.setId(id);
        warning.setStatus(1);
        warning.setHandleTime(LocalDateTime.now());
        return sysWarningMapper.update(warning) > 0;
    }

    /**
     * 创建超时预警
     */
    public SysWarning createExpireWarning(Long wasteId, String message) {
        SysWarning warning = new SysWarning();
        warning.setWarningType("EXPIRE");
        warning.setWasteId(wasteId);
        warning.setWarningMsg(message);
        warning.setWarningLevel(2);
        warning.setStatus(0);
        sysWarningMapper.insert(warning);
        return warning;
    }

    /**
     * 创建超量预警
     */
    public SysWarning createOverflowWarning(Long warehouseId, String message) {
        SysWarning warning = new SysWarning();
        warning.setWarningType("OVERFLOW");
        warning.setWarehouseId(warehouseId);
        warning.setWarningMsg(message);
        warning.setWarningLevel(3);
        warning.setStatus(0);
        sysWarningMapper.insert(warning);
        return warning;
    }

    /**
     * 创建异常预警
     */
    public SysWarning createExceptionWarning(Long wasteId, Long warehouseId, String message) {
        SysWarning warning = new SysWarning();
        warning.setWarningType("EXCEPTION");
        warning.setWasteId(wasteId);
        warning.setWarehouseId(warehouseId);
        warning.setWarningMsg(message);
        warning.setWarningLevel(1);
        warning.setStatus(0);
        sysWarningMapper.insert(warning);
        return warning;
    }
}
