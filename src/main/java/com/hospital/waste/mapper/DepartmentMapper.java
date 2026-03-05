package com.hospital.waste.mapper;

import com.hospital.waste.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 科室Mapper接口
 */
@Mapper
public interface DepartmentMapper {

    /**
     * 查询所有科室
     */
    List<Department> selectAll();

    /**
     * 根据ID查询
     */
    Department selectById(@Param("id") Long id);

    /**
     * 根据编码查询
     */
    Department selectByDeptCode(@Param("deptCode") String deptCode);

    /**
     * 插入记录
     */
    int insert(Department department);

    /**
     * 更新记录
     */
    int update(Department department);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<Department> selectByStatus(@Param("status") Integer status);
}
