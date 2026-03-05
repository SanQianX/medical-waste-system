package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 废物类别实体类
 */
@Data
public class WasteCategory {
    private Long id;
    private String categoryCode;    // 类别编码
    private String categoryName;     // 类别名称
    private String categoryType;    // 类别类型(感染性/损伤性/化学性/药物性/病理类)
    private String description;      // 说明
    private Integer status;          // 状态
    private LocalDateTime createTime; // 创建时间
}
