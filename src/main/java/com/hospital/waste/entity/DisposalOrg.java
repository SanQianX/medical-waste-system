package com.hospital.waste.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 处置机构实体类
 */
@Data
public class DisposalOrg {
    private Long id;
    private String orgCode;            // 机构编码
    private String orgName;            // 机构名称
    private String licenseNo;          // 许可证号
    private String contactPerson;      // 联系人
    private String phone;              // 联系电话
    private String address;            // 地址
    private Integer status;            // 状态
    private LocalDateTime createTime;  // 创建时间
}
