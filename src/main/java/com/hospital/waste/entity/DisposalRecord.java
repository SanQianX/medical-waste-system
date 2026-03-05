package com.hospital.waste.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 处置记录实体类
 */
@Data
public class DisposalRecord {
    private Long id;
    private Long wasteId;              // 废物记录ID
    private Long disposalOrgId;        // 处置机构ID
    private LocalDateTime disposalTime;    // 处置时间
    private BigDecimal disposalWeight;     // 处置重量
    private String disposalMethod;      // 处置方式
    private Long operatorId;           // 操作人ID
    private Integer status;            // 状态(1待处置/2已处置)
    private String remark;             // 备注
    private LocalDateTime createTime;  // 创建时间
}
