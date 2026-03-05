package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 科室实体类
 */
@Data
public class Department {
    private Long id;
    private String deptCode;        // 科室编码
    private String deptName;         // 科室名称
    private String deptType;         // 科室类型
    private String leader;            // 负责人
    private String phone;            // 联系电话
    private Integer status;          // 状态
    private LocalDateTime createTime; // 创建时间
}
