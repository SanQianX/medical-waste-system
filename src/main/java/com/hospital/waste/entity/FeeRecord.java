package com.hospital.waste.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用记录实体类
 */
@Data
public class FeeRecord {
    private Long id;
    private String feeNo;              // 费用单号
    private Long wasteId;             // 废物记录ID
    private Long disposalOrgId;        // 处置机构
    private BigDecimal weight;         // 重量(kg)
    private BigDecimal unitPrice;      // 单价(元/kg)
    private BigDecimal totalAmount;    // 总金额(元)
    private String feeType;           // 费用类型(处置费/运输费)
    private Integer status;           // 状态(1:待结算 2:已结算 3:已支付)
    private LocalDateTime settlementDate; // 结算日期
    private String remark;             // 备注
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
