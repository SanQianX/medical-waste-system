package com.hospital.waste.service;

import com.hospital.waste.entity.FeeRecord;
import com.hospital.waste.mapper.FeeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用记录业务层
 */
@Service
public class FeeRecordService {

    @Autowired
    private FeeRecordMapper feeRecordMapper;

    public List<FeeRecord> findAll() {
        return feeRecordMapper.selectAll();
    }

    public FeeRecord findById(Long id) {
        return feeRecordMapper.selectById(id);
    }

    public FeeRecord findByFeeNo(String feeNo) {
        return feeRecordMapper.selectByFeeNo(feeNo);
    }

    public List<FeeRecord> findByWasteId(Long wasteId) {
        return feeRecordMapper.selectByWasteId(wasteId);
    }

    public List<FeeRecord> findByDisposalOrgId(Long disposalOrgId) {
        return feeRecordMapper.selectByDisposalOrgId(disposalOrgId);
    }

    public List<FeeRecord> findByStatus(Integer status) {
        return feeRecordMapper.selectByStatus(status);
    }

    public FeeRecord create(FeeRecord feeRecord) {
        // 生成费用单号
        if (feeRecord.getFeeNo() == null || feeRecord.getFeeNo().isEmpty()) {
            feeRecord.setFeeNo(generateFeeNo());
        }
        if (feeRecord.getStatus() == null) {
            feeRecord.setStatus(1); // 待结算
        }
        // 计算总金额
        if (feeRecord.getWeight() != null && feeRecord.getUnitPrice() != null) {
            feeRecord.setTotalAmount(feeRecord.getWeight().multiply(feeRecord.getUnitPrice()));
        }
        feeRecordMapper.insert(feeRecord);
        return feeRecord;
    }

    public boolean update(FeeRecord feeRecord) {
        // 重新计算总金额
        if (feeRecord.getWeight() != null && feeRecord.getUnitPrice() != null) {
            feeRecord.setTotalAmount(feeRecord.getWeight().multiply(feeRecord.getUnitPrice()));
        }
        return feeRecordMapper.update(feeRecord) > 0;
    }

    public boolean delete(Long id) {
        return feeRecordMapper.deleteById(id) > 0;
    }

    /**
     * 结算费用
     */
    public boolean settle(Long id) {
        FeeRecord feeRecord = feeRecordMapper.selectById(id);
        if (feeRecord == null) {
            return false;
        }
        feeRecord.setStatus(2); // 已结算
        feeRecord.setSettlementDate(LocalDateTime.now());
        return feeRecordMapper.update(feeRecord) > 0;
    }

    /**
     * 标记已支付
     */
    public boolean pay(Long id) {
        FeeRecord feeRecord = feeRecordMapper.selectById(id);
        if (feeRecord == null) {
            return false;
        }
        feeRecord.setStatus(3); // 已支付
        return feeRecordMapper.update(feeRecord) > 0;
    }

    /**
     * 生成费用单号
     * 格式: FEE + 年月日 + 4位流水号
     */
    private String generateFeeNo() {
        String dateStr = java.time.LocalDate.now().toString().replace("-", "");
        String serial = String.format("%04d", System.currentTimeMillis() % 10000);
        return "FEE" + dateStr + serial;
    }
}
