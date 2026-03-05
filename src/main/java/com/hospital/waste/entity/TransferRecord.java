package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 转运记录实体类
 */
@Data
public class TransferRecord {
    private Long id;
    private String transferNo;            // 转运单号
    private String wasteIds;              // 废物ID列表(逗号分隔)
    private Long fromDepartmentId;        // 来源科室ID
    private Long toWarehouseId;           // 目标仓库ID
    private Long transporterId;           // 运送人员ID
    private LocalDateTime transferTime;  // 转运时间
    private LocalDateTime receiveTime;    // 接收时间
    private Integer status;               // 状态(1待转运/2运输中/3已到达/4已完成)
    private String remark;                // 备注
    private LocalDateTime createTime;     // 创建时间
    private LocalDateTime updateTime;      // 更新时间
}
