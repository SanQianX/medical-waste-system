package com.hospital.waste.service;

import com.hospital.waste.entity.DisposalRecord;
import com.hospital.waste.mapper.DisposalRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 处置记录服务类
 */
@Service
public class DisposalRecordService {

    @Autowired
    private DisposalRecordMapper disposalRecordMapper;

    /**
     * 查询所有处置记录
     */
    public List<DisposalRecord> findAll() {
        return disposalRecordMapper.selectAll();
    }

    /**
     * 根据ID查询
     */
    public DisposalRecord findById(Long id) {
        return disposalRecordMapper.selectById(id);
    }

    /**
     * 根据废物ID查询
     */
    public DisposalRecord findByWasteId(Long wasteId) {
        return disposalRecordMapper.selectByWasteId(wasteId);
    }

    /**
     * 创建处置记录
     */
    public DisposalRecord create(DisposalRecord record) {
        // 设置初始状态为待处置
        if (record.getStatus() == null) {
            record.setStatus(1);
        }
        // 设置创建时间
        record.setCreateTime(LocalDateTime.now());

        disposalRecordMapper.insert(record);
        return record;
    }

    /**
     * 更新处置记录
     */
    public boolean update(DisposalRecord record) {
        return disposalRecordMapper.update(record) > 0;
    }

    /**
     * 删除处置记录
     */
    public boolean delete(Long id) {
        return disposalRecordMapper.deleteById(id) > 0;
    }

    /**
     * 根据状态查询
     */
    public List<DisposalRecord> findByStatus(Integer status) {
        return disposalRecordMapper.selectByStatus(status);
    }

    /**
     * 执行处置（更新状态为已处置）
     */
    public boolean completeDisposal(Long id) {
        DisposalRecord record = new DisposalRecord();
        record.setId(id);
        record.setStatus(2); // 已处置
        record.setDisposalTime(LocalDateTime.now());

        return disposalRecordMapper.update(record) > 0;
    }
}
