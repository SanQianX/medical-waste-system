package com.hospital.waste.service;

import com.hospital.waste.entity.StorageRecord;
import com.hospital.waste.mapper.StorageRecordMapper;
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
import static org.mockito.Mockito.*;

/**
 * 贮存记录服务单元测试
 */
@ExtendWith(MockitoExtension.class)
class StorageRecordServiceTest {

    @Mock
    private StorageRecordMapper storageRecordMapper;

    @InjectMocks
    private StorageRecordService storageRecordService;

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

    // ==================== findAll 测试 ====================

    @Test
    void testFindAll() {
        List<StorageRecord> expectedList = Arrays.asList(testRecord);
        when(storageRecordMapper.selectAll()).thenReturn(expectedList);

        List<StorageRecord> result = storageRecordService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storageRecordMapper, times(1)).selectAll();
    }

    @Test
    void testFindAllEmptyList() {
        when(storageRecordMapper.selectAll()).thenReturn(Arrays.asList());

        List<StorageRecord> result = storageRecordService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== findById 测试 ====================

    @Test
    void testFindByIdSuccess() {
        when(storageRecordMapper.selectById(1L)).thenReturn(testRecord);

        StorageRecord result = storageRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(storageRecordMapper.selectById(999L)).thenReturn(null);

        StorageRecord result = storageRecordService.findById(999L);

        assertNull(result);
    }

    // ==================== findByWasteId 测试 ====================

    @Test
    void testFindByWasteIdSuccess() {
        when(storageRecordMapper.selectByWasteId(1L)).thenReturn(testRecord);

        StorageRecord result = storageRecordService.findByWasteId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getWasteId());
    }

    @Test
    void testFindByWasteIdNotFound() {
        when(storageRecordMapper.selectByWasteId(999L)).thenReturn(null);

        StorageRecord result = storageRecordService.findByWasteId(999L);

        assertNull(result);
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(storageRecordMapper.insert(any(StorageRecord.class))).thenReturn(1);

        StorageRecord record = new StorageRecord();
        record.setWasteId(1L);
        record.setWarehouseId(1L);

        StorageRecord result = storageRecordService.create(record);

        assertNotNull(result);
        assertNotNull(result.getInTime());
        assertEquals(1, result.getStatus());
        assertNotNull(result.getCreateTime());
        verify(storageRecordMapper, times(1)).insert(any(StorageRecord.class));
    }

    @Test
    void testCreateWithStatus() {
        when(storageRecordMapper.insert(any(StorageRecord.class))).thenReturn(1);

        StorageRecord record = new StorageRecord();
        record.setStatus(2);
        record.setWasteId(1L);

        StorageRecord result = storageRecordService.create(record);

        assertEquals(2, result.getStatus());
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(storageRecordMapper.update(any(StorageRecord.class))).thenReturn(1);

        StorageRecord record = new StorageRecord();
        record.setId(1L);
        record.setStorageDays(5);

        boolean result = storageRecordService.update(record);

        assertTrue(result);
    }

    @Test
    void testUpdateFailure() {
        when(storageRecordMapper.update(any(StorageRecord.class))).thenReturn(0);

        StorageRecord record = new StorageRecord();
        record.setId(1L);

        boolean result = storageRecordService.update(record);

        assertFalse(result);
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteSuccess() {
        when(storageRecordMapper.deleteById(1L)).thenReturn(1);

        boolean result = storageRecordService.delete(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteFailure() {
        when(storageRecordMapper.deleteById(999L)).thenReturn(0);

        boolean result = storageRecordService.delete(999L);

        assertFalse(result);
    }

    // ==================== findByStatus 测试 ====================

    @Test
    void testFindByStatusValid() {
        List<StorageRecord> expectedList = Arrays.asList(testRecord);
        when(storageRecordMapper.selectByStatus(1)).thenReturn(expectedList);

        List<StorageRecord> result = storageRecordService.findByStatus(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByStatusNotFound() {
        when(storageRecordMapper.selectByStatus(99)).thenReturn(Arrays.asList());

        List<StorageRecord> result = storageRecordService.findByStatus(99);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== outbound 测试 ====================

    @Test
    void testOutboundSuccess() {
        StorageRecord record = new StorageRecord();
        record.setId(1L);
        record.setInTime(LocalDateTime.now().minusDays(5));
        record.setStatus(1);

        when(storageRecordMapper.selectById(1L)).thenReturn(record);
        when(storageRecordMapper.update(any(StorageRecord.class))).thenReturn(1);

        boolean result = storageRecordService.outbound(1L);

        assertTrue(result);
        assertNotNull(record.getOutTime());
        assertEquals(2, record.getStatus());
        assertTrue(record.getStorageDays() >= 5);
    }

    @Test
    void testOutboundNotFound() {
        when(storageRecordMapper.selectById(999L)).thenReturn(null);

        boolean result = storageRecordService.outbound(999L);

        assertFalse(result);
    }

    // ==================== outStorage 测试 ====================

    @Test
    void testOutStorageSuccess() {
        StorageRecord record = new StorageRecord();
        record.setId(1L);
        record.setInTime(LocalDateTime.now().minusDays(3));
        record.setStatus(1);

        when(storageRecordMapper.selectById(1L)).thenReturn(record);
        when(storageRecordMapper.update(any(StorageRecord.class))).thenReturn(1);

        boolean result = storageRecordService.outStorage(1L);

        assertTrue(result);
    }

    @Test
    void testOutStorageNotFound() {
        when(storageRecordMapper.selectById(999L)).thenReturn(null);

        boolean result = storageRecordService.outStorage(999L);

        assertFalse(result);
    }
}
