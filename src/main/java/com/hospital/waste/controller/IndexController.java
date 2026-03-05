package com.hospital.waste.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 根路径控制器 - 提供欢迎页面
 */
@Tag(name = "系统首页")
@Controller
public class IndexController {

    @Operation(summary = "系统首页")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
