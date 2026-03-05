package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class SysUser {
    private Long id;
    private String username;         // 用户名
    private String password;         // 密码
    private String realName;         // 真实姓名
    private Long departmentId;       // 所属科室ID
    private Long roleId;             // 角色ID
    private String phone;            // 手机号
    private Integer status;          // 状态(0禁用/1正常)
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
