package com.hospital.waste.mapper;

import com.hospital.waste.entity.Container;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContainerMapper {

    List<Container> selectAll();

    Container selectById(@Param("id") Long id);

    Container selectByCode(@Param("containerCode") String containerCode);

    List<Container> selectByDepartmentId(@Param("departmentId") Long departmentId);

    List<Container> selectByStatus(@Param("status") Integer status);

    int insert(Container container);

    int update(Container container);

    int deleteById(@Param("id") Long id);
}
