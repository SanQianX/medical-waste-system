package com.hospital.waste.mapper;

import com.hospital.waste.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色Mapper接口
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 查询所有角色
     */
    List<SysRole> selectAll();

    /**
     * 根据ID查询
     */
    SysRole selectById(@Param("id") Long id);

    /**
     * 根据编码查询
     */
    SysRole selectByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 插入记录
     */
    int insert(SysRole sysRole);

    /**
     * 更新记录
     */
    int update(SysRole sysRole);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);
}
