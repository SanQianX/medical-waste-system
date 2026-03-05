package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 贮存记录实体类
 */
@Data
public class StorageRecord {
    private Long id;
    private Long wasteId;              // 废物记录ID
    private Long warehouseId;           // 仓库ID
    private LocalDateTime inTime;       // 入库时间
    private LocalDateTime outTime;      // 出库时间
    private Integer storageDays;        // 存储天数
    private Integer status;            // 状态(1贮存中/2已出库)
    private LocalDateTime createTime;   // 创建时间
}
