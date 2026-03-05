package com.hospital.waste.service;

import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.mapper.WasteRecordMapper;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 废物记录服务单元测试
 */
@ExtendWith(MockitoExtension.class)
class WasteRecordServiceTest {

    @Mock
    private WasteRecordMapper wasteRecordMapper;

    @InjectMocks
    private WasteRecordService wasteRecordService;

    private WasteRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new WasteRecord();
        testRecord.setId(1L);
        testRecord.setWasteCode("WF20240101123456");
        testRecord.setCategoryId(1L);
        testRecord.setDepartmentId(1L);
        testRecord.setWeight(10.5);
        testRecord.setContainerCount(2);
        testRecord.setGeneratorId(1L);
        testRecord.setGenerateTime(LocalDateTime.now());
        testRecord.setStatus(1);
    }

    // ==================== generateWasteCode 测试 ====================

    @Test
    void testGenerateWasteCode() {
        String code = wasteRecordService.generateWasteCode();

        assertNotNull(code);
        assertTrue(code.startsWith("WF"));
        assertEquals(18, code.length()); // WF + 8位日期 + 6位随机数
    }

    // ==================== findAll 测试 ====================

    @Test
    void testFindAll() {
        List<WasteRecord> expectedList = Arrays.asList(testRecord);
        when(wasteRecordMapper.selectAll()).thenReturn(expectedList);

        List<WasteRecord> result = wasteRecordService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(wasteRecordMapper, times(1)).selectAll();
    }

    @Test
    void testFindAllEmptyList() {
        when(wasteRecordMapper.selectAll()).thenReturn(Arrays.asList());

        List<WasteRecord> result = wasteRecordService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== findById 测试 ====================

    @Test
    void testFindByIdSuccess() {
        when(wasteRecordMapper.selectById(1L)).thenReturn(testRecord);

        WasteRecord result = wasteRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindByIdNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findById(null);
        });
    }

    @Test
    void testFindByIdInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findById(0L);
        });
    }

    @Test
    void testFindByIdNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findById(-1L);
        });
    }

    // ==================== findByWasteCode 测试 ====================

    @Test
    void testFindByWasteCodeSuccess() {
        when(wasteRecordMapper.selectByWasteCode("WF20240101123456")).thenReturn(testRecord);

        WasteRecord result = wasteRecordService.findByWasteCode("WF20240101123456");

        assertNotNull(result);
        assertEquals("WF20240101123456", result.getWasteCode());
    }

    @Test
    void testFindByWasteCodeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByWasteCode(null);
        });
    }

    @Test
    void testFindByWasteCodeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByWasteCode("");
        });
    }

    @Test
    void testFindByWasteCodeBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByWasteCode("   ");
        });
    }

    // ==================== create 测试 ====================

    @Test
    void testCreateSuccess() {
        when(wasteRecordMapper.selectByWasteCode(anyString())).thenReturn(null);
        when(wasteRecordMapper.insert(any(WasteRecord.class))).thenReturn(1);

        WasteRecord record = new WasteRecord();
        record.setWeight(10.5);
        record.setContainerCount(2);
        record.setGenerateTime(LocalDateTime.now());

        WasteRecord result = wasteRecordService.create(record);

        assertNotNull(result);
        assertNotNull(result.getWasteCode());
        assertTrue(result.getWasteCode().startsWith("WF"));
        assertEquals(1, result.getStatus());
        verify(wasteRecordMapper, times(1)).insert(any(WasteRecord.class));
    }

    @Test
    void testCreateWithExistingCode() {
        when(wasteRecordMapper.selectByWasteCode("WF20240101123456")).thenReturn(testRecord);

        WasteRecord record = new WasteRecord();
        record.setWasteCode("WF20240101123456");
        record.setWeight(10.5);
        record.setContainerCount(2);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithNullWeight() {
        WasteRecord record = new WasteRecord();
        record.setWeight(null);
        record.setContainerCount(2);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithNegativeWeight() {
        WasteRecord record = new WasteRecord();
        record.setWeight(-1.0);
        record.setContainerCount(2);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithNullContainerCount() {
        WasteRecord record = new WasteRecord();
        record.setWeight(10.5);
        record.setContainerCount(null);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithZeroContainerCount() {
        WasteRecord record = new WasteRecord();
        record.setWeight(10.5);
        record.setContainerCount(0);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithNegativeContainerCount() {
        WasteRecord record = new WasteRecord();
        record.setWeight(10.5);
        record.setContainerCount(-1);
        record.setGenerateTime(LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    @Test
    void testCreateWithNullGenerateTime() {
        WasteRecord record = new WasteRecord();
        record.setWeight(10.5);
        record.setContainerCount(2);
        record.setGenerateTime(null);

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.create(record);
        });
    }

    // ==================== update 测试 ====================

    @Test
    void testUpdateSuccess() {
        when(wasteRecordMapper.update(any(WasteRecord.class))).thenReturn(1);

        WasteRecord record = new WasteRecord();
        record.setId(1L);
        record.setWeight(15.0);

        boolean result = wasteRecordService.update(record);

        assertTrue(result);
        verify(wasteRecordMapper, times(1)).update(any(WasteRecord.class));
    }

    @Test
    void testUpdateWithNullId() {
        WasteRecord record = new WasteRecord();
        record.setId(null);

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.update(record);
        });
    }

    @Test
    void testUpdateWithZeroId() {
        WasteRecord record = new WasteRecord();
        record.setId(0L);

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.update(record);
        });
    }

    @Test
    void testUpdateWithNegativeWeight() {
        WasteRecord record = new WasteRecord();
        record.setId(1L);
        record.setWeight(-5.0);

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.update(record);
        });
    }

    @Test
    void testUpdateWithZeroContainerCount() {
        WasteRecord record = new WasteRecord();
        record.setId(1L);
        record.setContainerCount(0);

        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.update(record);
        });
    }

    @Test
    void testUpdateFailure() {
        when(wasteRecordMapper.update(any(WasteRecord.class))).thenReturn(0);

        WasteRecord record = new WasteRecord();
        record.setId(1L);
        record.setWeight(15.0);

        boolean result = wasteRecordService.update(record);

        assertFalse(result);
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteSuccess() {
        when(wasteRecordMapper.deleteById(1L)).thenReturn(1);

        boolean result = wasteRecordService.delete(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.delete(null);
        });
    }

    @Test
    void testDeleteWithZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.delete(0L);
        });
    }

    @Test
    void testDeleteWithNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.delete(-1L);
        });
    }

    @Test
    void testDeleteFailure() {
        when(wasteRecordMapper.deleteById(1L)).thenReturn(0);

        boolean result = wasteRecordService.delete(1L);

        assertFalse(result);
    }

    // ==================== findByStatus 测试 ====================

    @Test
    void testFindByStatusValid() {
        List<WasteRecord> expectedList = Arrays.asList(testRecord);
        when(wasteRecordMapper.selectByStatus(1)).thenReturn(expectedList);

        List<WasteRecord> result = wasteRecordService.findByStatus(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByStatus(null);
        });
    }

    @Test
    void testFindByStatusInvalidStatus0() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByStatus(0);
        });
    }

    @Test
    void testFindByStatusInvalidStatus5() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByStatus(5);
        });
    }

    // ==================== findByDepartment 测试 ====================

    @Test
    void testFindByDepartmentValid() {
        List<WasteRecord> expectedList = Arrays.asList(testRecord);
        when(wasteRecordMapper.selectByDepartmentId(1L)).thenReturn(expectedList);

        List<WasteRecord> result = wasteRecordService.findByDepartment(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindByDepartmentNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByDepartment(null);
        });
    }

    @Test
    void testFindByDepartmentZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.findByDepartment(0L);
        });
    }

    // ==================== updateStatus 测试 ====================

    @Test
    void testUpdateStatusSuccess() {
        when(wasteRecordMapper.update(any(WasteRecord.class))).thenReturn(1);

        boolean result = wasteRecordService.updateStatus(1L, 2);

        assertTrue(result);
    }

    @Test
    void testUpdateStatusWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.updateStatus(null, 1);
        });
    }

    @Test
    void testUpdateStatusWithZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.updateStatus(0L, 1);
        });
    }

    @Test
    void testUpdateStatusWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.updateStatus(1L, 0);
        });
    }

    @Test
    void testUpdateStatusWithStatus5() {
        assertThrows(IllegalArgumentException.class, () -> {
            wasteRecordService.updateStatus(1L, 5);
        });
    }
}
