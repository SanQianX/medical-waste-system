package com.hospital.waste.service;

import com.hospital.waste.entity.StorageRecord;
import com.hospital.waste.mapper.StorageRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 贮存记录服务类
 */
@Service
public class StorageRecordService {

    @Autowired
    private StorageRecordMapper storageRecordMapper;

    /**
     * 查询所有贮存记录
     */
    public List<StorageRecord> findAll() {
        return storageRecordMapper.selectAll();
    }

    /**
     * 根据ID查询
     */
    public StorageRecord findById(Long id) {
        return storageRecordMapper.selectById(id);
    }

    /**
     * 根据废物ID查询
     */
    public StorageRecord findByWasteId(Long wasteId) {
        return storageRecordMapper.selectByWasteId(wasteId);
    }

    /**
     * 创建贮存记录（入库）
     */
    public StorageRecord create(StorageRecord record) {
        // 设置入库时间
        record.setInTime(LocalDateTime.now());
        // 设置初始状态为贮存中
        if (record.getStatus() == null) {
            record.setStatus(1);
        }
        // 设置创建时间
        record.setCreateTime(LocalDateTime.now());

        storageRecordMapper.insert(record);
        return record;
    }

    /**
     * 更新贮存记录
     */
    public boolean update(StorageRecord record) {
        return storageRecordMapper.update(record) > 0;
    }

    /**
     * 删除贮存记录
     */
    public boolean delete(Long id) {
        return storageRecordMapper.deleteById(id) > 0;
    }

    /**
     * 根据状态查询
     */
    public List<StorageRecord> findByStatus(Integer status) {
        return storageRecordMapper.selectByStatus(status);
    }

    /**
     * 出库
     */
    public boolean outbound(Long id) {
        StorageRecord record = storageRecordMapper.selectById(id);
        if (record == null) {
            return false;
        }
        record.setOutTime(LocalDateTime.now());
        // 计算存储天数
        long days = ChronoUnit.DAYS.between(record.getInTime(), record.getOutTime());
        record.setStorageDays((int) days);
        record.setStatus(2); // 已出库

        return storageRecordMapper.update(record) > 0;
    }

    /**
     * 出库（outStorage别名）
     */
    public boolean outStorage(Long id) {
        return outbound(id);
    }
}
