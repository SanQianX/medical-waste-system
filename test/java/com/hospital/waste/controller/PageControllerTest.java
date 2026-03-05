package com.hospital.waste.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 页面路由控制器单元测试
 */
@ExtendWith(MockitoExtension.class)
class PageControllerTest {

    @InjectMocks
    private PageController pageController;

    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
    }

    // ==================== dashboard 测试 ====================

    @Test
    void testDashboard() {
        String viewName = pageController.dashboard(model);

        assertEquals("dashboard", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    // ==================== 基础数据管理模块测试 ====================

    @Test
    void testDepartment() {
        String viewName = pageController.department(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testWasteCategory() {
        String viewName = pageController.wasteCategory(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testWarehouse() {
        String viewName = pageController.warehouse(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testDisposalOrg() {
        String viewName = pageController.disposalOrg(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testUser() {
        String viewName = pageController.user(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    // ==================== 业务管理模块测试 ====================

    @Test
    void testWasteRecord() {
        String viewName = pageController.wasteRecord(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testTransfer() {
        String viewName = pageController.transfer(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testStorage() {
        String viewName = pageController.storage(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    @Test
    void testDisposal() {
        String viewName = pageController.disposal(model);

        assertEquals("layout", viewName);
        verify(model, times(3)).addAttribute(anyString(), any());
    }

    // ==================== 验证Model属性测试 ====================

    @Test
    void testDashboardModelAttributes() {
        pageController.dashboard(model);

        verify(model).addAttribute("pageTitle", "首页仪表盘");
        verify(model).addAttribute("contentTemplate", "dashboard");
        verify(model).addAttribute("activeMenu", "dashboard");
    }

    @Test
    void testDepartmentModelAttributes() {
        pageController.department(model);

        verify(model).addAttribute("pageTitle", "科室管理");
        verify(model).addAttribute("contentTemplate", "department/list");
        verify(model).addAttribute("activeMenu", "department");
    }

    @Test
    void testWasteCategoryModelAttributes() {
        pageController.wasteCategory(model);

        verify(model).addAttribute("pageTitle", "废物类别管理");
        verify(model).addAttribute("contentTemplate", "waste-category/list");
        verify(model).addAttribute("activeMenu", "waste-category");
    }

    @Test
    void testWarehouseModelAttributes() {
        pageController.warehouse(model);

        verify(model).addAttribute("pageTitle", "仓库管理");
        verify(model).addAttribute("contentTemplate", "warehouse/list");
        verify(model).addAttribute("activeMenu", "warehouse");
    }

    @Test
    void testDisposalOrgModelAttributes() {
        pageController.disposalOrg(model);

        verify(model).addAttribute("pageTitle", "处置机构管理");
        verify(model).addAttribute("contentTemplate", "disposal-org/list");
        verify(model).addAttribute("activeMenu", "disposal-org");
    }

    @Test
    void testUserModelAttributes() {
        pageController.user(model);

        verify(model).addAttribute("pageTitle", "用户管理");
        verify(model).addAttribute("contentTemplate", "user/list");
        verify(model).addAttribute("activeMenu", "user");
    }

    @Test
    void testWasteRecordModelAttributes() {
        pageController.wasteRecord(model);

        verify(model).addAttribute("pageTitle", "废物产生记录");
        verify(model).addAttribute("contentTemplate", "waste-record/list");
        verify(model).addAttribute("activeMenu", "waste-record");
    }

    @Test
    void testTransferModelAttributes() {
        pageController.transfer(model);

        verify(model).addAttribute("pageTitle", "转运管理");
        verify(model).addAttribute("contentTemplate", "transfer/list");
        verify(model).addAttribute("activeMenu", "transfer");
    }

    @Test
    void testStorageModelAttributes() {
        pageController.storage(model);

        verify(model).addAttribute("pageTitle", "贮存管理");
        verify(model).addAttribute("contentTemplate", "storage/list");
        verify(model).addAttribute("activeMenu", "storage");
    }

    @Test
    void testDisposalModelAttributes() {
        pageController.disposal(model);

        verify(model).addAttribute("pageTitle", "处置管理");
        verify(model).addAttribute("contentTemplate", "disposal/list");
        verify(model).addAttribute("activeMenu", "disposal");
    }
}
