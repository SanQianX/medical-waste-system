package com.hospital.waste.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 根路径控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;

    // ==================== index 测试 ====================

    @Test
    void testIndex() {
        String viewName = indexController.index();

        assertEquals("index", viewName);
    }

    @Test
    void testIndexNotNull() {
        String viewName = indexController.index();

        assertNotNull(viewName);
    }

    @Test
    void testIndexEqualsIndex() {
        String viewName = indexController.index();

        assertEquals("index", viewName);
    }
}
