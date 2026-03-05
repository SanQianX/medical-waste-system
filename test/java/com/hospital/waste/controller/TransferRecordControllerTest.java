package com.hospital.waste.controller;

import com.hospital.waste.entity.TransferRecord;
import com.hospital.waste.service.TransferRecordService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * 转运记录控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class TransferRecordControllerTest {

    @Mock
    private TransferRecordService transferRecordService;

    @InjectMocks
    private TransferRecordController transferRecordController;

    private TransferRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new TransferRecord();
        testRecord.setId(1L);
        testRecord.setTransferNo("TR20240101ABCDEF");
        testRecord.setWasteIds("1,2,3");
        testRecord.setFromDepartmentId(1L);
        testRecord.setToWarehouseId(1L);
        testRecord.setTransporterId(1L);
        testRecord.setStatus(1);
    }

    // ==================== list 测试 ====================

    @Test
    void testList() {
        List<TransferRecord> expectedList = Arrays.asList(testRecord);
        when(transferRecordService.findAll()).thenReturn(expectedList);

        ResponseEntity<Map<String, Object>> response = transferRecordController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("success", body.get("message"));
        assertNotNull(body.get("data"));
        assertEquals(1, body.get("total"));
    }

    @Test
    void testListEmpty() {
        when(transferRecordService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<Map<String, Object>> response = transferRecordController.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals(0, body.get("total"));
    }

    // ==================== getById 测试 ====================

    @Test
    void testGetByIdSuccess() {
        when(transferRecordService.findById(1L)).thenReturn(testRecord);

        ResponseEntity<Map<String, Object>> response = transferRecordController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("success", body.get("message"));
        assertNotNull(body.get("data"));
    }

    @Test
    void testGetByIdNotFound() {
        when(transferRecordService.findById(999L)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = transferRecordController.getById(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("code"));
        assertEquals("记录不存在", body.get("message"));
    }

    // ==================== getByTransferNo 测试 ====================

    @Test
    void testGetByTransferNoSuccess() {
        when(transferRecordService.findByTransferNo("TR20240101ABCDEF")).thenReturn(testRecord);

        ResponseEntity<Map<String, Object>> response = transferRecordController.getByTransferNo("TR20240101ABCDEF");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
    }

    @Test
    void testGetByTransferNoNotFound() {
        when(transferRecordService.findByTransferNo("NOTFOUND")).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = transferRecordController.getByTransferNo("NOTFOUND");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("code"));
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(transferRecordService.create(any(TransferRecord.class))).thenReturn(testRecord);

        TransferRecord record = new TransferRecord();
        record.setWasteIds("1,2,3");
        record.setFromDepartmentId(1L);
        record.setToWarehouseId(1L);

        ResponseEntity<Map<String, Object>> response = transferRecordController.create(record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("创建成功", body.get("message"));
        assertNotNull(body.get("data"));
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(transferRecordService.update(any(TransferRecord.class))).thenReturn(true);

        TransferRecord record = new TransferRecord();
        record.setRemark("更新备注");

        ResponseEntity<Map<String, Object>> response = transferRecordController.update(1L, record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("更新成功", body.get("message"));
    }

    @Test
    void testUpdateFailure() {
        when(transferRecordService.update(any(TransferRecord.class))).thenReturn(false);

        TransferRecord record = new TransferRecord();

        ResponseEntity<Map<String, Object>> response = transferRecordController.update(1L, record);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("更新失败", body.get("message"));
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteSuccess() {
        when(transferRecordService.delete(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferRecordController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("删除成功", body.get("message"));
    }

    @Test
    void testDeleteFailure() {
        when(transferRecordService.delete(999L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferRecordController.delete(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("删除失败", body.get("message"));
    }

    // ==================== getByStatus 测试 ====================

    @Test
    void testGetByStatus() {
        List<TransferRecord> expectedList = Arrays.asList(testRecord);
        when(transferRecordService.findByStatus(1)).thenReturn(expectedList);

        ResponseEntity<Map<String, Object>> response = transferRecordController.getByStatus(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertNotNull(body.get("data"));
        assertEquals(1, body.get("total"));
    }

    // ==================== startTransfer 测试 ====================

    @Test
    void testStartTransferSuccess() {
        when(transferRecordService.startTransfer(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferRecordController.startTransfer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("已开始转运", body.get("message"));
    }

    @Test
    void testStartTransferFailure() {
        when(transferRecordService.startTransfer(999L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferRecordController.startTransfer(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("操作失败", body.get("message"));
    }

    // ==================== arrive 测试 ====================

    @Test
    void testArriveSuccess() {
        when(transferRecordService.arrive(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferRecordController.arrive(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("已到达目的地", body.get("message"));
    }

    @Test
    void testArriveFailure() {
        when(transferRecordService.arrive(999L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferRecordController.arrive(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("操作失败", body.get("message"));
    }

    // ==================== complete 测试 ====================

    @Test
    void testCompleteSuccess() {
        when(transferRecordService.complete(1L)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferRecordController.complete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("转运已完成", body.get("message"));
    }

    @Test
    void testCompleteFailure() {
        when(transferRecordService.complete(999L)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferRecordController.complete(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("操作失败", body.get("message"));
    }

    // ==================== updateStatus 测试 ====================

    @Test
    void testUpdateStatusSuccess() {
        when(transferRecordService.updateStatus(1L, 2)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = transferRecordController.updateStatus(1L, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(200, body.get("code"));
        assertEquals("状态更新成功", body.get("message"));
    }

    @Test
    void testUpdateStatusFailure() {
        when(transferRecordService.updateStatus(999L, 2)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = transferRecordController.updateStatus(999L, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("code"));
        assertEquals("状态更新失败", body.get("message"));
    }
}
