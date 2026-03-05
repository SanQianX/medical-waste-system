package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预警记录实体类
 */
@Data
public class SysWarning {
    private Long id;
    private String warningType;        // 预警类型(EXPIRE/OVERFLOW/EXCEPTION)
    private Long wasteId;              // 关联废物ID
    private Long warehouseId;          // 仓库ID
    private String warningMsg;         // 预警信息
    private Integer warningLevel;      // 预警级别(1低/2中/3高)
    private Integer status;             // 状态(0未处理/1已处理)
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime handleTime;   // 处理时间
}
