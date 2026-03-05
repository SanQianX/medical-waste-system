package com.hospital.waste.controller;

import com.hospital.waste.mapper.DisposalRecordMapper;
import com.hospital.waste.mapper.TransferRecordMapper;
import com.hospital.waste.mapper.WasteRecordMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计管理控制器
 */
@Tag(name = "统计管理")
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Autowired
    private WasteRecordMapper wasteRecordMapper;

    @Autowired
    private TransferRecordMapper transferRecordMapper;

    @Autowired
    private DisposalRecordMapper disposalRecordMapper;

    @Operation(summary = "日报表统计")
    @GetMapping("/daily")
    public ResponseEntity<Map<String, Object>> getDailyStatistics(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long departmentId) {

        LocalDate queryDate;
        if (date == null || date.isEmpty()) {
            queryDate = LocalDate.now();
        } else {
            queryDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }

        LocalDateTime startOfDay = queryDate.atStartOfDay();
        LocalDateTime endOfDay = queryDate.plusDays(1).atStartOfDay();

        // 统计当日废物产生数量
        int wasteCount = wasteRecordMapper.countByDateRange(startOfDay, endOfDay);

        // 统计当日转运数量
        int transferCount = transferRecordMapper.countByDateRange(startOfDay, endOfDay);

        // 统计当日处置数量
        int disposalCount = disposalRecordMapper.countByDateRange(startOfDay, endOfDay);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("date", queryDate.toString());
        Map<String, Object> data = new HashMap<>();
        data.put("wasteCount", wasteCount);
        data.put("transferCount", transferCount);
        data.put("disposalCount", disposalCount);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "月报表统计")
    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyStatistics(
            @RequestParam(required = false) String yearMonth) {

        LocalDate queryDate;
        if (yearMonth == null || yearMonth.isEmpty()) {
            queryDate = LocalDate.now();
        } else {
            queryDate = LocalDate.parse(yearMonth + "-01", DateTimeFormatter.ISO_DATE);
        }

        LocalDateTime startOfMonth = queryDate.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = queryDate.withDayOfMonth(queryDate.lengthOfMonth()).atTime(23, 59, 59);

        // 统计当月废物产生数量
        int wasteCount = wasteRecordMapper.countByDateRange(startOfMonth, endOfMonth);

        // 统计当月转运数量
        int transferCount = transferRecordMapper.countByDateRange(startOfMonth, endOfMonth);

        // 统计当月处置数量
        int disposalCount = disposalRecordMapper.countByDateRange(startOfMonth, endOfMonth);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("yearMonth", yearMonth != null ? yearMonth : queryDate.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        Map<String, Object> data = new HashMap<>();
        data.put("wasteCount", wasteCount);
        data.put("transferCount", transferCount);
        data.put("disposalCount", disposalCount);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "年报表统计")
    @GetMapping("/yearly")
    public ResponseEntity<Map<String, Object>> getYearlyStatistics(
            @RequestParam(required = false) String year) {

        int queryYear;
        if (year == null || year.isEmpty()) {
            queryYear = LocalDate.now().getYear();
        } else {
            queryYear = Integer.parseInt(year);
        }

        LocalDateTime startOfYear = LocalDate.of(queryYear, 1, 1).atStartOfDay();
        LocalDateTime endOfYear = LocalDate.of(queryYear, 12, 31).atTime(23, 59, 59);

        // 统计当年废物产生数量
        int wasteCount = wasteRecordMapper.countByDateRange(startOfYear, endOfYear);

        // 统计当年转运数量
        int transferCount = transferRecordMapper.countByDateRange(startOfYear, endOfYear);

        // 统计当年处置数量
        int disposalCount = disposalRecordMapper.countByDateRange(startOfYear, endOfYear);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("year", queryYear);
        Map<String, Object> data = new HashMap<>();
        data.put("wasteCount", wasteCount);
        data.put("transferCount", transferCount);
        data.put("disposalCount", disposalCount);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "科室统计")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Map<String, Object>> getDepartmentStatistics(
            @PathVariable Long departmentId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        LocalDateTime startTime;
        LocalDateTime endTime;

        if (startDate == null || startDate.isEmpty()) {
            startTime = LocalDate.now().atStartOfDay();
        } else {
            startTime = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE).atStartOfDay();
        }

        if (endDate == null || endDate.isEmpty()) {
            endTime = LocalDateTime.now();
        } else {
            endTime = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE).atTime(23, 59, 59);
        }

        // 统计科室废物产生数量
        int wasteCount = wasteRecordMapper.countByDepartmentAndDateRange(departmentId, startTime, endTime);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("departmentId", departmentId);
        result.put("startDate", startTime.toLocalDate().toString());
        result.put("endDate", endTime.toLocalDate().toString());
        Map<String, Object> data = new HashMap<>();
        data.put("wasteCount", wasteCount);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }
}
