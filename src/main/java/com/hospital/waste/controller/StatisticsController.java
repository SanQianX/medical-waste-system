package com.hospital.waste.controller;

import com.hospital.waste.entity.SysWarning;
import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.mapper.DisposalRecordMapper;
import com.hospital.waste.mapper.SysWarningMapper;
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

    @Autowired
    private SysWarningMapper sysWarningMapper;

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

    @Operation(summary = "仪表盘汇总数据")
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = now.toLocalDate().atStartOfDay();
        LocalDateTime monthStart = now.withDayOfMonth(1).toLocalDate().atStartOfDay();

        Map<String, Object> result = new HashMap<>();

        // 今日数据
        int todayWaste = wasteRecordMapper.countByDateRange(todayStart, now);
        int todayTransfer = transferRecordMapper.countByDateRange(todayStart, now);
        int todayDisposal = disposalRecordMapper.countByDateRange(todayStart, now);

        // 本月数据
        int monthWaste = wasteRecordMapper.countByDateRange(monthStart, now);
        int monthTransfer = transferRecordMapper.countByDateRange(monthStart, now);
        int monthDisposal = disposalRecordMapper.countByDateRange(monthStart, now);

        // 预警统计
        List<SysWarning> allWarnings = sysWarningMapper.selectAll();
        int totalWarnings = allWarnings.size();
        int unprocessedWarnings = (int) allWarnings.stream()
            .filter(w -> w.getStatus() == null || w.getStatus() == 0).count();
        int highLevelWarnings = (int) allWarnings.stream()
            .filter(w -> w.getWarningLevel() != null && w.getWarningLevel() >= 3).count();

        // 总计数据
        int totalWaste = wasteRecordMapper.selectAll().size();
        int totalTransfer = transferRecordMapper.selectAll().size();
        int totalDisposal = disposalRecordMapper.selectAll().size();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> today = new HashMap<>();
        today.put("wasteCount", todayWaste);
        today.put("transferCount", todayTransfer);
        today.put("disposalCount", todayDisposal);

        Map<String, Object> month = new HashMap<>();
        month.put("wasteCount", monthWaste);
        month.put("transferCount", monthTransfer);
        month.put("disposalCount", monthDisposal);

        Map<String, Object> totals = new HashMap<>();
        totals.put("wasteCount", totalWaste);
        totals.put("transferCount", totalTransfer);
        totals.put("disposalCount", totalDisposal);

        Map<String, Object> warnings = new HashMap<>();
        warnings.put("total", totalWarnings);
        warnings.put("unprocessed", unprocessedWarnings);
        warnings.put("highLevel", highLevelWarnings);

        data.put("today", today);
        data.put("month", month);
        data.put("totals", totals);
        data.put("warnings", warnings);

        result.put("code", 200);
        result.put("data", data);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "导出日报表CSV", description = "导出日报表数据为CSV格式")
    @GetMapping("/export/daily")
    public ResponseEntity<String> exportDailyCsv(
            @RequestParam(required = false) String date) {

        LocalDate targetDate;
        if (date == null || date.isEmpty()) {
            targetDate = LocalDate.now();
        } else {
            targetDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        }

        LocalDateTime startTime = targetDate.atStartOfDay();
        LocalDateTime endTime = targetDate.atTime(23, 59, 59);

        // 获取数据
        List<WasteRecord> wasteRecords = wasteRecordMapper.selectAll();
        List<WasteRecord> dayRecords = wasteRecords.stream()
            .filter(r -> r.getGenerateTime() != null &&
                !r.getGenerateTime().isBefore(startTime) &&
                !r.getGenerateTime().isAfter(endTime))
            .collect(java.util.stream.Collectors.toList());

        // 构建CSV内容
        StringBuilder csv = new StringBuilder();
        csv.append("日期,废物编码,科室ID,类别ID,重量,容器数,状态,产生时间\n");

        for (WasteRecord record : dayRecords) {
            csv.append(targetDate).append(",");
            csv.append(record.getWasteCode()).append(",");
            csv.append(record.getDepartmentId()).append(",");
            csv.append(record.getCategoryId()).append(",");
            csv.append(record.getWeight()).append(",");
            csv.append(record.getContainerCount()).append(",");
            csv.append(getStatusText(record.getStatus())).append(",");
            csv.append(record.getGenerateTime()).append("\n");
        }

        return ResponseEntity.ok()
            .header("Content-Type", "text/csv;charset=UTF-8")
            .header("Content-Disposition", "attachment; filename=daily_report_" + date + ".csv")
            .body(csv.toString());
    }

    @Operation(summary = "导出预警报表CSV", description = "导出预警数据为CSV格式")
    @GetMapping("/export/warnings")
    public ResponseEntity<String> exportWarningsCsv(
            @RequestParam(required = false) Integer status) {

        List<SysWarning> warnings;
        if (status != null) {
            warnings = sysWarningMapper.selectByStatus(status);
        } else {
            warnings = sysWarningMapper.selectAll();
        }

        // 构建CSV内容
        StringBuilder csv = new StringBuilder();
        csv.append("预警ID,预警类型,预警级别,预警信息,状态,创建时间,处理时间\n");

        for (SysWarning warning : warnings) {
            csv.append(warning.getId()).append(",");
            csv.append(getWarningTypeText(warning.getWarningType())).append(",");
            csv.append(getWarningLevelText(warning.getWarningLevel())).append(",");
            csv.append("\"").append(warning.getWarningMsg().replace("\"", "\"\"")).append("\",");
            csv.append(getWarningStatusText(warning.getStatus())).append(",");
            csv.append(warning.getCreateTime()).append(",");
            csv.append(warning.getHandleTime() != null ? warning.getHandleTime() : "").append("\n");
        }

        return ResponseEntity.ok()
            .header("Content-Type", "text/csv;charset=UTF-8")
            .header("Content-Disposition", "attachment; filename=warnings_report.csv")
            .body(csv.toString());
    }

    // 辅助方法
    private String getStatusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 1: return "待收集";
            case 2: return "已收集";
            case 3: return "转运中";
            case 4: return "已处置";
            default: return "未知";
        }
    }

    private String getWarningTypeText(String type) {
        if (type == null) return "未知";
        switch (type) {
            case "EXPIRE": return "超时预警";
            case "OVERFLOW": return "超量预警";
            case "EXCEPTION": return "异常预警";
            default: return type;
        }
    }

    private String getWarningLevelText(Integer level) {
        if (level == null) return "未知";
        switch (level) {
            case 1: return "低";
            case 2: return "中";
            case 3: return "高";
            default: return "未知";
        }
    }

    private String getWarningStatusText(Integer status) {
        if (status == null || status == 0) return "未处理";
        return "已处理";
    }
}
