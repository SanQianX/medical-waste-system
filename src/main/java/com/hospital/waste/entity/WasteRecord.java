package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 废物记录实体类
 */
@Data
public class WasteRecord {
    private Long id;
    private String wasteCode;           // 废物唯一编码(条形码)
    private Long categoryId;              // 废物类别ID
    private Long departmentId;            // 产生科室ID
    private Double weight;                // 重量(kg)
    private Integer containerCount;       // 容器数量
    private Long generatorId;             // 产生人(护士)ID
    private LocalDateTime generateTime;   // 产生时间
    private Integer status;               // 状态(1待收集/2已收集/3已转运/4已处置)
    private String remark;                // 备注
    private LocalDateTime createTime;     // 创建时间
    private LocalDateTime updateTime;     // 更新时间
}
