package com.hospital.waste.mapper;

import com.hospital.waste.entity.FeeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeeRecordMapper {

    List<FeeRecord> selectAll();

    FeeRecord selectById(@Param("id") Long id);

    FeeRecord selectByFeeNo(@Param("feeNo") String feeNo);

    List<FeeRecord> selectByWasteId(@Param("wasteId") Long wasteId);

    List<FeeRecord> selectByDisposalOrgId(@Param("disposalOrgId") Long disposalOrgId);

    List<FeeRecord> selectByStatus(@Param("status") Integer status);

    int insert(FeeRecord feeRecord);

    int update(FeeRecord feeRecord);

    int deleteById(@Param("id") Long id);
}
