package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色实体类
 */
@Data
public class SysRole {
    private Long id;
    private String roleName;           // 角色名称
    private String roleCode;           // 角色编码
    private String description;        // 描述
    private LocalDateTime createTime;  // 创建时间
}
