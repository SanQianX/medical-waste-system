package com.hospital.waste.mapper;

import com.hospital.waste.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface SysUserMapper {

    /**
     * 查询所有用户
     */
    List<SysUser> selectAll();

    /**
     * 根据ID查询
     */
    SysUser selectById(@Param("id") Long id);

    /**
     * 根据用户名查询
     */
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 插入记录
     */
    int insert(SysUser sysUser);

    /**
     * 更新记录
     */
    int update(SysUser sysUser);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<SysUser> selectByStatus(@Param("status") Integer status);

    /**
     * 根据科室查询
     */
    List<SysUser> selectByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * 根据角色查询
     */
    List<SysUser> selectByRoleId(@Param("roleId") Long roleId);
}
