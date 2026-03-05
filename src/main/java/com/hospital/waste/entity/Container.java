package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 容器实体类
 */
@Data
public class Container {
    private Long id;
    private String containerCode;      // 容器编号
    private String containerType;       // 容器类型（如：利器盒、塑料袋、周转桶）
    private String specification;       // 规格（如：5L、10L、20L）
    private Double capacity;           // 容量(L)
    private Long departmentId;         // 所属科室ID
    private Integer currentCount;      // 当前废物数量
    private Integer status;            // 状态(1:正常使用 2:已满 3:清洗中 4:报废)
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
