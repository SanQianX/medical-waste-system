package com.hospital.waste.controller;

import com.hospital.waste.mapper.DisposalRecordMapper;
import com.hospital.waste.mapper.TransferRecordMapper;
import com.hospital.waste.mapper.WasteRecordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 统计控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {

    @Mock
    private WasteRecordMapper wasteRecordMapper;

    @Mock
    private TransferRecordMapper transferRecordMapper;

    @Mock
    private DisposalRecordMapper disposalRecordMapper;

    @InjectMocks
    private StatisticsController statisticsController;

    // ==================== 日报表测试 ====================

    @Test
    void testGetDailyStatistics() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(10);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(5);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(3);

        ResponseEntity<Map<String, Object>> response = statisticsController.getDailyStatistics(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(10, data.get("wasteCount"));
        assertEquals(5, data.get("transferCount"));
        assertEquals(3, data.get("disposalCount"));
    }

    @Test
    void testGetDailyStatisticsWithDate() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(20);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(15);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(12);

        ResponseEntity<Map<String, Object>> response = statisticsController.getDailyStatistics("2024-01-15", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(20, data.get("wasteCount"));
        assertEquals(15, data.get("transferCount"));
        assertEquals(12, data.get("disposalCount"));
    }

    // ==================== 月报表测试 ====================

    @Test
    void testGetMonthlyStatistics() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(100);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(80);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(75);

        ResponseEntity<Map<String, Object>> response = statisticsController.getMonthlyStatistics(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(100, data.get("wasteCount"));
        assertEquals(80, data.get("transferCount"));
        assertEquals(75, data.get("disposalCount"));
    }

    @Test
    void testGetMonthlyStatisticsWithYearMonth() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(300);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(250);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(200);

        ResponseEntity<Map<String, Object>> response = statisticsController.getMonthlyStatistics("2024-01");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(300, data.get("wasteCount"));
        assertEquals(250, data.get("transferCount"));
        assertEquals(200, data.get("disposalCount"));
    }

    // ==================== 年报表测试 ====================

    @Test
    void testGetYearlyStatistics() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(3650);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(3000);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(2800);

        ResponseEntity<Map<String, Object>> response = statisticsController.getYearlyStatistics(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(3650, data.get("wasteCount"));
        assertEquals(3000, data.get("transferCount"));
        assertEquals(2800, data.get("disposalCount"));
    }

    @Test
    void testGetYearlyStatisticsWithYear() {
        when(wasteRecordMapper.countByDateRange(any(), any())).thenReturn(5000);
        when(transferRecordMapper.countByDateRange(any(), any())).thenReturn(4500);
        when(disposalRecordMapper.countByDateRange(any(), any())).thenReturn(4000);

        ResponseEntity<Map<String, Object>> response = statisticsController.getYearlyStatistics("2024");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(5000, data.get("wasteCount"));
        assertEquals(4500, data.get("transferCount"));
        assertEquals(4000, data.get("disposalCount"));
    }

    // ==================== 科室统计测试 ====================

    @Test
    void testGetDepartmentStatistics() {
        when(wasteRecordMapper.countByDepartmentAndDateRange(any(), any(), any())).thenReturn(50);

        ResponseEntity<Map<String, Object>> response = statisticsController.getDepartmentStatistics(1L, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals(1L, body.get("departmentId"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(50, data.get("wasteCount"));
    }

    @Test
    void testGetDepartmentStatisticsWithDateRange() {
        when(wasteRecordMapper.countByDepartmentAndDateRange(any(), any(), any())).thenReturn(30);

        ResponseEntity<Map<String, Object>> response = statisticsController.getDepartmentStatistics(
                1L, "2024-01-01", "2024-01-31");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        assertEquals(30, data.get("wasteCount"));
    }
}
