package com.hospital.waste.controller;

import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.entity.Department;
import com.hospital.waste.mapper.WasteRecordMapper;
import com.hospital.waste.mapper.DepartmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HIS/LIS系统对接控制器
 * 提供与医院HIS/LIS系统的数据交互接口
 */
@Tag(name = "HIS/LIS对接", description = "医院信息系统对接接口")
@RestController
@RequestMapping("/api/v1/his")
public class HisController {

    @Autowired
    private WasteRecordMapper wasteRecordMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Operation(summary = "健康检查", description = "HIS/LIS对接接口健康状态")
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "HIS/LIS对接服务正常运行");
        result.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "同步科室信息", description = "从HIS系统同步科室数据")
    @GetMapping("/sync/departments")
    public ResponseEntity<Map<String, Object>> syncDepartments() {
        Map<String, Object> result = new HashMap<>();
        List<Department> departments = departmentMapper.selectAll();

        result.put("code", 200);
        result.put("message", "科室数据同步成功");
        result.put("data", departments);
        result.put("total", departments.size());
        result.put("syncTime", System.currentTimeMillis());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "获取废物记录", description = "根据时间范围获取废物记录用于HIS同步")
    @GetMapping("/waste/records")
    public ResponseEntity<Map<String, Object>> getWasteRecords(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "100") Integer pageSize) {

        Map<String, Object> result = new HashMap<>();
        List<WasteRecord> records = wasteRecordMapper.selectAll();

        // 简单分页
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, records.size());
        List<WasteRecord> pageData = start < records.size() ? records.subList(start, end) : new java.util.ArrayList<>();

        result.put("code", 200);
        result.put("data", pageData);
        result.put("total", records.size());
        result.put("page", page);
        result.put("pageSize", pageSize);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "推送废物记录", description = "接收HIS系统推送的废物记录")
    @PostMapping("/waste/push")
    public ResponseEntity<Map<String, Object>> pushWasteRecord(@RequestBody WasteRecord record) {
        Map<String, Object> result = new HashMap<>();

        // 生成追溯码
        if (record.getWasteCode() == null || record.getWasteCode().isEmpty()) {
            String wasteCode = generateWasteCode(record);
            record.setWasteCode(wasteCode);
        }

        wasteRecordMapper.insert(record);

        result.put("code", 200);
        result.put("message", "废物记录推送成功");
        result.put("data", record);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "批量推送废物记录", description = "批量接收HIS系统推送的废物记录")
    @PostMapping("/waste/batch")
    public ResponseEntity<Map<String, Object>> batchPushWasteRecords(@RequestBody List<WasteRecord> records) {
        Map<String, Object> result = new HashMap<>();

        int successCount = 0;
        for (WasteRecord record : records) {
            if (record.getWasteCode() == null || record.getWasteCode().isEmpty()) {
                String wasteCode = generateWasteCode(record);
                record.setWasteCode(wasteCode);
            }
            wasteRecordMapper.insert(record);
            successCount++;
        }

        result.put("code", 200);
        result.put("message", "批量推送成功");
        result.put("successCount", successCount);
        result.put("totalCount", records.size());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "查询废物追溯码", description = "根据追溯码查询废物记录用于追溯")
    @GetMapping("/waste/trace/{wasteCode}")
    public ResponseEntity<Map<String, Object>> traceWaste(@PathVariable String wasteCode) {
        Map<String, Object> result = new HashMap<>();

        WasteRecord record = wasteRecordMapper.selectByWasteCode(wasteCode);

        if (record != null) {
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", record);
        } else {
            result.put("code", 404);
            result.put("message", "未找到该追溯码对应的记录");
        }

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新废物状态", description = "HIS系统更新废物状态")
    @PutMapping("/waste/{wasteCode}/status")
    public ResponseEntity<Map<String, Object>> updateWasteStatus(
            @PathVariable String wasteCode,
            @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();

        WasteRecord record = wasteRecordMapper.selectByWasteCode(wasteCode);
        if (record == null) {
            result.put("code", 404);
            result.put("message", "未找到该废物记录");
            return ResponseEntity.ok(result);
        }

        record.setStatus(status);
        wasteRecordMapper.update(record);

        result.put("code", 200);
        result.put("message", "状态更新成功");

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "获取统计数据", description = "获取指定时间范围的统计数据供HIS系统调用")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        Map<String, Object> result = new HashMap<>();

        List<WasteRecord> records = wasteRecordMapper.selectAll();

        int totalCount = records.size();
        double totalWeight = records.stream()
                .mapToDouble(r -> r.getWeight() != null ? r.getWeight() : 0)
                .sum();

        // 状态统计
        long status1 = records.stream().filter(r -> r.getStatus() != null && r.getStatus() == 1).count();
        long status2 = records.stream().filter(r -> r.getStatus() != null && r.getStatus() == 2).count();
        long status3 = records.stream().filter(r -> r.getStatus() != null && r.getStatus() == 3).count();
        long status4 = records.stream().filter(r -> r.getStatus() != null && r.getStatus() == 4).count();

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", totalCount);
        data.put("totalWeight", totalWeight);
        data.put("statusWaiting", status1);
        data.put("statusCollected", status2);
        data.put("statusInTransit", status3);
        data.put("statusDisposed", status4);

        result.put("code", 200);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    /**
     * 生成废物追溯码
     * 格式: YYYYMMDD + 科室编码 + 4位流水号
     */
    private String generateWasteCode(WasteRecord record) {
        String dateStr = java.time.LocalDate.now().toString().replace("-", "");
        String deptCode = "D001"; // 默认科室编码
        if (record.getDepartmentId() != null) {
            Department dept = departmentMapper.selectById(record.getDepartmentId());
            if (dept != null && dept.getDeptCode() != null) {
                deptCode = dept.getDeptCode();
            }
        }
        // 生成4位流水号
        String serial = String.format("%04d", System.currentTimeMillis() % 10000);
        return dateStr + deptCode + serial;
    }
}
