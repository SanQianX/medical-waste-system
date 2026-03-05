package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 仓库实体类
 */
@Data
public class Warehouse {
    private Long id;
    private String warehouseCode;      // 仓库编码
    private String warehouseName;      // 仓库名称
    private String warehouseType;      // 仓库类型
    private String address;            // 地址
    private Double capacity;           // 容量(kg)
    private String manager;            // 管理员
    private Integer status;            // 状态
    private LocalDateTime createTime;  // 创建时间
}
