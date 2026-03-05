package com.hospital.waste.controller;

import com.hospital.waste.entity.StorageRecord;
import com.hospital.waste.service.StorageRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 贮存记录控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class StorageRecordControllerTest {

    @Mock
    private StorageRecordService storageRecordService;

    @InjectMocks
    private StorageRecordController storageRecordController;

    private StorageRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new StorageRecord();
        testRecord.setId(1L);
        testRecord.setWasteId(1L);
        testRecord.setWarehouseId(1L);
        testRecord.setInTime(LocalDateTime.now());
        testRecord.setStatus(1);
    }

    // ==================== list 测试 ====================

    @Test
    void testList() {
        List<StorageRecord> expectedList = Arrays.asList(testRecord);
        when(storageRecordService.findAll()).thenReturn(expectedList);

        ResponseEntity<Map<String, Object>> response = storageRecordController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
        assertEquals(1, body.get("total"));
    }

    @Test
    void testListEmpty() {
        when(storageRecordService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<Map<String, Object>> response = storageRecordController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals(0, body.get("total"));
    }

    // ==================== getById 测试 ====================

    @Test
    void testGetByIdSuccess() {
        when(storageRecordService.findById(1L)).thenReturn(testRecord);

        ResponseEntity<Map<String, Object>> response = storageRecordController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
    }

    @Test
    void testGetByIdNotFound() {
        when(storageRecordService.findById(999L)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = storageRecordController.getById(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("code"));
        assertEquals("贮存记录不存在", body.get("message"));
    }

    // ==================== getByWasteId 测试 ====================

    @Test
    void testGetByWasteIdSuccess() {
        when(storageRecordService.findByWasteId(1L)).thenReturn(testRecord);

        ResponseEntity<Map<String, Object>> response = storageRecordController.getByWasteId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
    }

    @Test
    void testGetByWasteIdNotFound() {
        when(storageRecordService.findByWasteId(999L)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = storageRecordController.getByWasteId(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("code"));
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(storageRecordService.create(any(StorageRecord.class))).thenReturn(testRecord);

        StorageRecord record = new StorageRecord();
        record.setWasteId(1L);
        record.setWarehouseId(1L);

        ResponseEntity<Map<String, Object>> response = storageRecordController.create(record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("入库成功", body.get("message"));
        assertNotNull(body.get("data"));
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(storageRecordService.update(any(StorageRecord.class))).thenReturn(true);

        StorageRecord record = new StorageRecord();

        ResponseEntity<Map<String, Object>> response = storageRecordController.update(1L, record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("更新成功", body.get("message"));
    }

    @Test
    void testUpdateFailure() {
        when(storageRecordService.update(any(StorageRecord.class))).thenReturn(false);

        StorageRecord record = new StorageRecord();

        ResponseEntity<Map<String, Object>> response = storageRecordController.update(1L, record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("更新失败", body.get("message"));
    }

    // ==================== outStorage 测试 ====================

    @Test
    void testOutStorageSuccess() {
        when(storageRecordService.outStorage(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = storageRecordController.outStorage(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("出库成功", body.get("message"));
    }

    @Test
    void testOutStorageFailure() {
        when(storageRecordService.outStorage(999L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = storageRecordController.outStorage(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("出库失败", body.get("message"));
    }

    // ==================== getByStatus 测试 ====================

    @Test
    void testGetByStatus() {
        List<StorageRecord> expectedList = Arrays.asList(testRecord);
        when(storageRecordService.findByStatus(1)).thenReturn(expectedList);

        ResponseEntity<Map<String, Object>> response = storageRecordController.getByStatus(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
        assertEquals(1, body.get("total"));
    }
}
