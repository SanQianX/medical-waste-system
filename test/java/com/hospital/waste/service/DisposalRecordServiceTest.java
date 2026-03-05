package com.hospital.waste.service;

import com.hospital.waste.entity.DisposalRecord;
import com.hospital.waste.mapper.DisposalRecordMapper;
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
import static org.mockito.Mockito.*;

/**
 * 处置记录服务单元测试
 */
@ExtendWith(MockitoExtension.class)
class DisposalRecordServiceTest {

    @Mock
    private DisposalRecordMapper disposalRecordMapper;

    @InjectMocks
    private DisposalRecordService disposalRecordService;

    private DisposalRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new DisposalRecord();
        testRecord.setId(1L);
        testRecord.setWasteId(1L);
        testRecord.setDisposalOrgId(1L);
        testRecord.setDisposalMethod("焚烧");
        testRecord.setStatus(1);
    }

    // ==================== findAll 测试 ====================

    @Test
    void testFindAll() {
        List<DisposalRecord> expectedList = Arrays.asList(testRecord);
        when(disposalRecordMapper.selectAll()).thenReturn(expectedList);

        List<DisposalRecord> result = disposalRecordService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(disposalRecordMapper, times(1)).selectAll();
    }

    @Test
    void testFindAllEmptyList() {
        when(disposalRecordMapper.selectAll()).thenReturn(Arrays.asList());

        List<DisposalRecord> result = disposalRecordService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== findById 测试 ====================

    @Test
    void testFindByIdSuccess() {
        when(disposalRecordMapper.selectById(1L)).thenReturn(testRecord);

        DisposalRecord result = disposalRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(disposalRecordMapper.selectById(999L)).thenReturn(null);

        DisposalRecord result = disposalRecordService.findById(999L);

        assertNull(result);
    }

    // ==================== findByWasteId 测试 ====================

    @Test
    void testFindByWasteIdSuccess() {
        when(disposalRecordMapper.selectByWasteId(1L)).thenReturn(testRecord);

        DisposalRecord result = disposalRecordService.findByWasteId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getWasteId());
    }

    @Test
    void testFindByWasteIdNotFound() {
        when(disposalRecordMapper.selectByWasteId(999L)).thenReturn(null);

        DisposalRecord result = disposalRecordService.findByWasteId(999L);

        assertNull(result);
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(disposalRecordMapper.insert(any(DisposalRecord.class))).thenReturn(1);

        DisposalRecord record = new DisposalRecord();
        record.setWasteId(1L);
        record.setDisposalOrgId(1L);
        record.setDisposalMethod("焚烧");

        DisposalRecord result = disposalRecordService.create(record);

        assertNotNull(result);
        assertEquals(1, result.getStatus());
        assertNotNull(result.getCreateTime());
        verify(disposalRecordMapper, times(1)).insert(any(DisposalRecord.class));
    }

    @Test
    void testCreateWithStatus() {
        when(disposalRecordMapper.insert(any(DisposalRecord.class))).thenReturn(1);

        DisposalRecord record = new DisposalRecord();
        record.setStatus(2);
        record.setWasteId(1L);

        DisposalRecord result = disposalRecordService.create(record);

        assertEquals(2, result.getStatus());
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(disposalRecordMapper.update(any(DisposalRecord.class))).thenReturn(1);

        DisposalRecord record = new DisposalRecord();
        record.setId(1L);
        record.setRemark("更新备注");

        boolean result = disposalRecordService.update(record);

        assertTrue(result);
    }

    @Test
    void testUpdateFailure() {
        when(disposalRecordMapper.update(any(DisposalRecord.class))).thenReturn(0);

        DisposalRecord record = new DisposalRecord();
        record.setId(1L);

        boolean result = disposalRecordService.update(record);

        assertFalse(result);
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteSuccess() {
        when(disposalRecordMapper.deleteById(1L)).thenReturn(1);

        boolean result = disposalRecordService.delete(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteFailure() {
        when(disposalRecordMapper.deleteById(999L)).thenReturn(0);

        boolean result = disposalRecordService.delete(999L);

        assertFalse(result);
    }

    // ==================== findByStatus 测试 ====================

    @Test
    void testFindByStatusValid() {
        List<DisposalRecord> expectedList = Arrays.asList(testRecord);
        when(disposalRecordMapper.selectByStatus(1)).thenReturn(expectedList);

        List<DisposalRecord> result = disposalRecordService.findByStatus(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByStatusNotFound() {
        when(disposalRecordMapper.selectByStatus(99)).thenReturn(Arrays.asList());

        List<DisposalRecord> result = disposalRecordService.findByStatus(99);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== completeDisposal 测试 ====================

    @Test
    void testCompleteDisposalSuccess() {
        when(disposalRecordMapper.update(any(DisposalRecord.class))).thenReturn(1);

        boolean result = disposalRecordService.completeDisposal(1L);

        assertTrue(result);
        verify(disposalRecordMapper, times(1)).update(any(DisposalRecord.class));
    }

    @Test
    void testCompleteDisposalFailure() {
        when(disposalRecordMapper.update(any(DisposalRecord.class))).thenReturn(0);

        boolean result = disposalRecordService.completeDisposal(999L);

        assertFalse(result);
    }
}
