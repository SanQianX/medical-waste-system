package com.hospital.waste.service;

import com.hospital.waste.entity.TransferRecord;
import com.hospital.waste.mapper.TransferRecordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 转运记录服务单元测试
 */
@ExtendWith(MockitoExtension.class)
class TransferRecordServiceTest {

    @Mock
    private TransferRecordMapper transferRecordMapper;

    @InjectMocks
    private TransferRecordService transferRecordService;

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

    // ==================== generateTransferNo 测试 ====================

    @Test
    void testGenerateTransferNo() {
        String transferNo = transferRecordService.generateTransferNo();

        assertNotNull(transferNo);
        assertTrue(transferNo.startsWith("TR"));
        assertEquals(16, transferNo.length()); // TR + 8位日期 + 6位随机字符
    }

    @Test
    void testGenerateTransferNoFormat() {
        String transferNo = transferRecordService.generateTransferNo();

        // 验证格式：TR + YYYYMMDD + 6位字符
        assertTrue(transferNo.matches("^TR\\d{8}[A-Z0-9]{6}$"));
    }

    // ==================== findAll 测试 ====================

    @Test
    void testFindAll() {
        List<TransferRecord> expectedList = Arrays.asList(testRecord);
        when(transferRecordMapper.selectAll()).thenReturn(expectedList);

        List<TransferRecord> result = transferRecordService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transferRecordMapper, times(1)).selectAll();
    }

    @Test
    void testFindAllEmptyList() {
        when(transferRecordMapper.selectAll()).thenReturn(Arrays.asList());

        List<TransferRecord> result = transferRecordService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== findById 测试 ====================

    @Test
    void testFindByIdSuccess() {
        when(transferRecordMapper.selectById(1L)).thenReturn(testRecord);

        TransferRecord result = transferRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(transferRecordMapper.selectById(999L)).thenReturn(null);

        TransferRecord result = transferRecordService.findById(999L);

        assertNull(result);
    }

    // ==================== findByTransferNo 测试 ====================

    @Test
    void testFindByTransferNoSuccess() {
        when(transferRecordMapper.selectByTransferNo("TR20240101ABCDEF")).thenReturn(testRecord);

        TransferRecord result = transferRecordService.findByTransferNo("TR20240101ABCDEF");

        assertNotNull(result);
        assertEquals("TR20240101ABCDEF", result.getTransferNo());
    }

    @Test
    void testFindByTransferNoNotFound() {
        when(transferRecordMapper.selectByTransferNo("NOTFOUND")).thenReturn(null);

        TransferRecord result = transferRecordService.findByTransferNo("NOTFOUND");

        assertNull(result);
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(transferRecordMapper.insert(any(TransferRecord.class))).thenReturn(1);

        TransferRecord record = new TransferRecord();
        record.setWasteIds("1,2,3");
        record.setFromDepartmentId(1L);
        record.setToWarehouseId(1L);
        record.setTransporterId(1L);

        TransferRecord result = transferRecordService.create(record);

        assertNotNull(result);
        assertNotNull(result.getTransferNo());
        assertTrue(result.getTransferNo().startsWith("TR"));
        assertEquals(1, result.getStatus());
        assertNotNull(result.getCreateTime());
        verify(transferRecordMapper, times(1)).insert(any(TransferRecord.class));
    }

    @Test
    void testCreateWithStatus() {
        when(transferRecordMapper.insert(any(TransferRecord.class))).thenReturn(1);

        TransferRecord record = new TransferRecord();
        record.setStatus(2);
        record.setWasteIds("1,2,3");

        TransferRecord result = transferRecordService.create(record);

        assertEquals(2, result.getStatus());
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(1);

        TransferRecord record = new TransferRecord();
        record.setId(1L);
        record.setRemark("更新备注");

        boolean result = transferRecordService.update(record);

        assertTrue(result);
        verify(transferRecordMapper, times(1)).update(any(TransferRecord.class));
    }

    @Test
    void testUpdateFailure() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(0);

        TransferRecord record = new TransferRecord();
        record.setId(1L);

        boolean result = transferRecordService.update(record);

        assertFalse(result);
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteSuccess() {
        when(transferRecordMapper.deleteById(1L)).thenReturn(1);

        boolean result = transferRecordService.delete(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteFailure() {
        when(transferRecordMapper.deleteById(999L)).thenReturn(0);

        boolean result = transferRecordService.delete(999L);

        assertFalse(result);
    }

    // ==================== findByStatus 测试 ====================

    @Test
    void testFindByStatusValid() {
        List<TransferRecord> expectedList = Arrays.asList(testRecord);
        when(transferRecordMapper.selectByStatus(1)).thenReturn(expectedList);

        List<TransferRecord> result = transferRecordService.findByStatus(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByStatusNotFound() {
        when(transferRecordMapper.selectByStatus(99)).thenReturn(Arrays.asList());

        List<TransferRecord> result = transferRecordService.findByStatus(99);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== updateStatus 测试 ====================

    @Test
    void testUpdateStatusSuccess() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(1);

        boolean result = transferRecordService.updateStatus(1L, 2);

        assertTrue(result);
    }

    @Test
    void testUpdateStatusFailure() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(0);

        boolean result = transferRecordService.updateStatus(999L, 2);

        assertFalse(result);
    }

    // ==================== startTransfer 测试 ====================

    @Test
    void testStartTransferSuccess() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(1);

        boolean result = transferRecordService.startTransfer(1L);

        assertTrue(result);
        verify(transferRecordMapper, times(1)).update(any(TransferRecord.class));
    }

    @Test
    void testStartTransferFailure() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(0);

        boolean result = transferRecordService.startTransfer(999L);

        assertFalse(result);
    }

    // ==================== arrive 测试 ====================

    @Test
    void testArriveSuccess() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(1);

        boolean result = transferRecordService.arrive(1L);

        assertTrue(result);
        verify(transferRecordMapper, times(1)).update(any(TransferRecord.class));
    }

    @Test
    void testArriveFailure() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(0);

        boolean result = transferRecordService.arrive(999L);

        assertFalse(result);
    }

    // ==================== complete 测试 ====================

    @Test
    void testCompleteSuccess() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(1);

        boolean result = transferRecordService.complete(1L);

        assertTrue(result);
        verify(transferRecordMapper, times(1)).update(any(TransferRecord.class));
    }

    @Test
    void testCompleteFailure() {
        when(transferRecordMapper.update(any(TransferRecord.class))).thenReturn(0);

        boolean result = transferRecordService.complete(999L);

        assertFalse(result);
    }
}
