package com.hospital.waste.service;

import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.mapper.WasteRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 废物记录服务类
 */
@Service
public class WasteRecordService {

    @Autowired
    private WasteRecordMapper wasteRecordMapper;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成废物编码
     * 格式：WF + 日期(YYYYMMDD) + 6位随机数字
     */
    public String generateWasteCode() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = RANDOM.nextInt(1000000);
        return "WF" + date + String.format("%06d", randomNum);
    }

    /**
     * 校验状态值范围
     */
    private boolean isValidStatus(Integer status) {
        return status != null && status >= 1 && status <= 4;
    }

    /**
     * 校验废物记录参数
     */
    private void validateRecord(WasteRecord record) {
        if (record.getWeight() == null || record.getWeight() < 0) {
            throw new IllegalArgumentException("重量不能为空且不能为负数");
        }
        if (record.getContainerCount() == null || record.getContainerCount() < 1) {
            throw new IllegalArgumentException("容器数量不能为空且至少为1");
        }
        if (record.getGenerateTime() == null) {
            throw new IllegalArgumentException("产生时间不能为空");
        }
    }

    /**
     * 查询所有废物记录
     */
    public List<WasteRecord> findAll() {
        return wasteRecordMapper.selectAll();
    }

    /**
     * 根据ID查询
     */
    public WasteRecord findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID无效");
        }
        return wasteRecordMapper.selectById(id);
    }

    /**
     * 根据废物编码查询
     */
    public WasteRecord findByWasteCode(String wasteCode) {
        if (wasteCode == null || wasteCode.trim().isEmpty()) {
            throw new IllegalArgumentException("废物编码不能为空");
        }
        return wasteRecordMapper.selectByWasteCode(wasteCode);
    }

    /**
     * 创建废物记录
     */
    @Transactional(rollbackFor = Exception.class)
    public WasteRecord create(WasteRecord record) {
        // 参数校验
        validateRecord(record);

        // 检查编码是否已存在
        WasteRecord existing = wasteRecordMapper.selectByWasteCode(record.getWasteCode());
        if (existing != null) {
            throw new IllegalArgumentException("废物编码已存在");
        }

        // 生成唯一编码
        record.setWasteCode(generateWasteCode());
        // 设置初始状态为待收集
        if (record.getStatus() == null) {
            record.setStatus(1);
        }
        // 设置创建时间
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());

        wasteRecordMapper.insert(record);
        return record;
    }

    /**
     * 更新废物记录
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(WasteRecord record) {
        if (record.getId() == null || record.getId() <= 0) {
            throw new IllegalArgumentException("ID无效");
        }
        // 校验重量和容器数量
        if (record.getWeight() != null && record.getWeight() < 0) {
            throw new IllegalArgumentException("重量不能为负数");
        }
        if (record.getContainerCount() != null && record.getContainerCount() < 1) {
            throw new IllegalArgumentException("容器数量至少为1");
        }
        record.setUpdateTime(LocalDateTime.now());
        return wasteRecordMapper.update(record) > 0;
    }

    /**
     * 删除废物记录
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID无效");
        }
        return wasteRecordMapper.deleteById(id) > 0;
    }

    /**
     * 根据状态查询
     */
    public List<WasteRecord> findByStatus(Integer status) {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("状态值必须在1-4之间");
        }
        return wasteRecordMapper.selectByStatus(status);
    }

    /**
     * 根据科室查询
     */
    public List<WasteRecord> findByDepartment(Long departmentId) {
        if (departmentId == null || departmentId <= 0) {
            throw new IllegalArgumentException("科室ID无效");
        }
        return wasteRecordMapper.selectByDepartmentId(departmentId);
    }

    /**
     * 更新废物状态
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID无效");
        }
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("状态值必须在1-4之间");
        }
        WasteRecord record = new WasteRecord();
        record.setId(id);
        record.setStatus(status);
        record.setUpdateTime(LocalDateTime.now());
        return wasteRecordMapper.update(record) > 0;
    }
}
