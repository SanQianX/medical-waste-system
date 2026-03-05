package com.hospital.waste.mapper;

import com.hospital.waste.entity.DisposalOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 处置机构Mapper接口
 */
@Mapper
public interface DisposalOrgMapper {

    /**
     * 查询所有处置机构
     */
    List<DisposalOrg> selectAll();

    /**
     * 根据ID查询
     */
    DisposalOrg selectById(@Param("id") Long id);

    /**
     * 根据编码查询
     */
    DisposalOrg selectByOrgCode(@Param("orgCode") String orgCode);

    /**
     * 插入记录
     */
    int insert(DisposalOrg disposalOrg);

    /**
     * 更新记录
     */
    int update(DisposalOrg disposalOrg);

    /**
     * 删除记录
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据状态查询
     */
    List<DisposalOrg> selectByStatus(@Param("status") Integer status);
}
