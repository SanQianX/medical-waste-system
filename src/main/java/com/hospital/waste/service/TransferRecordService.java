package com.hospital.waste.service;

import com.hospital.waste.entity.TransferRecord;
import com.hospital.waste.mapper.TransferRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 转运记录服务类
 */
@Service
public class TransferRecordService {

    @Autowired
    private TransferRecordMapper transferRecordMapper;

    /**
     * 生成转运单号
     * 格式：TR + 日期(YYYYMMDD) + 6位随机数
     */
    public String generateTransferNo() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "TR" + date + random;
    }

    /**
     * 查询所有转运记录
     */
    public List<TransferRecord> findAll() {
        return transferRecordMapper.selectAll();
    }

    /**
     * 根据ID查询
     */
    public TransferRecord findById(Long id) {
        return transferRecordMapper.selectById(id);
    }

    /**
     * 根据转运单号查询
     */
    public TransferRecord findByTransferNo(String transferNo) {
        return transferRecordMapper.selectByTransferNo(transferNo);
    }

    /**
     * 创建转运记录
     */
    public TransferRecord create(TransferRecord record) {
        // 生成转运单号
        record.setTransferNo(generateTransferNo());
        // 设置初始状态为待转运
        if (record.getStatus() == null) {
            record.setStatus(1);
        }
        // 设置创建时间
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());

        transferRecordMapper.insert(record);
        return record;
    }

    /**
     * 更新转运记录
     */
    public boolean update(TransferRecord record) {
        record.setUpdateTime(LocalDateTime.now());
        return transferRecordMapper.update(record) > 0;
    }

    /**
     * 删除转运记录
     */
    public boolean delete(Long id) {
        return transferRecordMapper.deleteById(id) > 0;
    }

    /**
     * 根据状态查询
     */
    public List<TransferRecord> findByStatus(Integer status) {
        return transferRecordMapper.selectByStatus(status);
    }

    /**
     * 更新转运状态
     */
    public boolean updateStatus(Long id, Integer status) {
        TransferRecord record = new TransferRecord();
        record.setId(id);
        record.setStatus(status);
        record.setUpdateTime(LocalDateTime.now());
        return transferRecordMapper.update(record) > 0;
    }

    /**
     * 开始转运（更新状态为运输中）
     */
    public boolean startTransfer(Long id) {
        TransferRecord record = new TransferRecord();
        record.setId(id);
        record.setStatus(2); // 运输中
        record.setTransferTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return transferRecordMapper.update(record) > 0;
    }

    /**
     * 到达目的地（更新状态为已到达）
     */
    public boolean arrive(Long id) {
        TransferRecord record = new TransferRecord();
        record.setId(id);
        record.setStatus(3); // 已到达
        record.setReceiveTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        return transferRecordMapper.update(record) > 0;
    }

    /**
     * 完成转运
     */
    public boolean complete(Long id) {
        TransferRecord record = new TransferRecord();
        record.setId(id);
        record.setStatus(4); // 已完成
        record.setUpdateTime(LocalDateTime.now());
        return transferRecordMapper.update(record) > 0;
    }
}
