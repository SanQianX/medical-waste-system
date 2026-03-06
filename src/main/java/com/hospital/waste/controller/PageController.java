package com.hospital.waste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面路由控制器 - 提供前端页面跳转
 */
@Controller
public class PageController {

    private Map<String, String> pageTitles = new HashMap<>();
    private Map<String, String> contentTemplates = new HashMap<>();

    public PageController() {
        // 初始化页面标题和模板路径
        // 概览
        pageTitles.put("dashboard", "首页仪表盘");
        contentTemplates.put("dashboard", "dashboard");

        // 基础数据
        pageTitles.put("department", "科室管理");
        contentTemplates.put("department", "department/list");

        pageTitles.put("waste-category", "废物类别管理");
        contentTemplates.put("waste-category", "waste-category/list");

        pageTitles.put("warehouse", "仓库管理");
        contentTemplates.put("warehouse", "warehouse/list");

        pageTitles.put("container", "容器管理");
        contentTemplates.put("container", "container/list");

        pageTitles.put("disposal-org", "处置机构管理");
        contentTemplates.put("disposal-org", "disposal-org/list");

        pageTitles.put("user", "用户管理");
        contentTemplates.put("user", "user/list");

        // 业务管理
        pageTitles.put("waste-record", "废物产生记录");
        contentTemplates.put("waste-record", "waste-record/list");

        pageTitles.put("transfer", "转运管理");
        contentTemplates.put("transfer", "transfer/list");

        pageTitles.put("storage", "贮存管理");
        contentTemplates.put("storage", "storage/list");

        pageTitles.put("disposal", "处置管理");
        contentTemplates.put("disposal", "disposal/list");

        pageTitles.put("fee", "费用管理");
        contentTemplates.put("fee", "fee/list");

        // 预警管理
        pageTitles.put("warning", "预警管理");
        contentTemplates.put("warning", "warning/list");

        // 操作日志
        pageTitles.put("operation-log", "操作日志");
        contentTemplates.put("operation-log", "operation-log/list");

        // 数据统计
        pageTitles.put("statistics", "数据统计");
        contentTemplates.put("statistics", "statistics");

        // 系统设置
        pageTitles.put("system-settings", "系统设置");
        contentTemplates.put("system-settings", "system-settings");
    }

    private void setPageModel(Model model, String page) {
        model.addAttribute("pageTitle", pageTitles.getOrDefault(page, "页面"));
        model.addAttribute("contentTemplate", contentTemplates.getOrDefault(page, page));
        model.addAttribute("activeMenu", page);
    }

    /**
     * 首页仪表盘
     */
    @GetMapping("/page/dashboard")
    public String dashboard(Model model) {
        setPageModel(model, "dashboard");
        return "dashboard";
    }

    // ========== 基础数据管理模块 ==========

    /**
     * 科室管理页面
     */
    @GetMapping("/page/department")
    public String department(Model model) {
        setPageModel(model, "department");
        return "department/list";
    }

    /**
     * 废物类别管理页面
     */
    @GetMapping("/page/waste-category")
    public String wasteCategory(Model model) {
        setPageModel(model, "waste-category");
        return "waste-category/list";
    }

    /**
     * 仓库管理页面
     */
    @GetMapping("/page/warehouse")
    public String warehouse(Model model) {
        setPageModel(model, "warehouse");
        return "warehouse/list";
    }

    /**
     * 容器管理页面
     */
    @GetMapping("/page/container")
    public String container(Model model) {
        setPageModel(model, "container");
        return "container/list";
    }

    /**
     * 处置机构管理页面
     */
    @GetMapping("/page/disposal-org")
    public String disposalOrg(Model model) {
        setPageModel(model, "disposal-org");
        return "disposal-org/list";
    }

    /**
     * 用户管理页面
     */
    @GetMapping("/page/user")
    public String user(Model model) {
        setPageModel(model, "user");
        return "user/list";
    }

    // ========== 业务管理模块 ==========

    /**
     * 废物产生记录页面
     */
    @GetMapping("/page/waste-record")
    public String wasteRecord(Model model) {
        setPageModel(model, "waste-record");
        return "waste-record/list";
    }

    /**
     * 转运管理页面
     */
    @GetMapping("/page/transfer")
    public String transfer(Model model) {
        setPageModel(model, "transfer");
        return "transfer/list";
    }

    /**
     * 贮存管理页面
     */
    @GetMapping("/page/storage")
    public String storage(Model model) {
        setPageModel(model, "storage");
        return "storage/list";
    }

    /**
     * 处置管理页面
     */
    @GetMapping("/page/disposal")
    public String disposal(Model model) {
        setPageModel(model, "disposal");
        return "disposal/list";
    }

    /**
     * 费用管理页面
     */
    @GetMapping("/page/fee")
    public String fee(Model model) {
        setPageModel(model, "fee");
        return "fee/list";
    }

    /**
     * 预警管理页面
     */
    @GetMapping("/page/warning")
    public String warning(Model model) {
        setPageModel(model, "warning");
        return "warning/list";
    }

    /**
     * 数据统计页面
     */
    @GetMapping("/page/statistics")
    public String statistics(Model model) {
        setPageModel(model, "statistics");
        return "statistics";
    }

    /**
     * 操作日志页面
     */
    @GetMapping("/page/operation-log")
    public String operationLog(Model model) {
        setPageModel(model, "operation-log");
        return "operation-log/list";
    }

    /**
     * 系统设置页面
     */
    @GetMapping("/page/system-settings")
    public String systemSettings(Model model) {
        setPageModel(model, "system-settings");
        return "system-settings";
    }

    /**
     * 根路径跳转到登录页
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/page/login";
    }

    /**
     * 登录页面
     */
    @GetMapping("/page/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面 - 跳转到登录页的一体化注册标签
     */
    @GetMapping("/page/register")
    public String register() {
        return "redirect:/page/login?tab=register";
    }

    /**
     * 用户中心页面
     */
    @GetMapping("/page/user-center")
    public String userCenter() {
        return "user-center";
    }
}
