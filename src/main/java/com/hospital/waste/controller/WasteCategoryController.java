package com.hospital.waste.controller;

import com.hospital.waste.entity.WasteCategory;
import com.hospital.waste.mapper.WasteCategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 废物类别管理Controller
 */
@Tag(name = "废物类别管理")
@RestController
@RequestMapping("/api/v1/waste-category")
public class WasteCategoryController {

    @Autowired
    private WasteCategoryMapper wasteCategoryMapper;

    @Operation(summary = "获取废物类别列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<WasteCategory> list = wasteCategoryMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询废物类别")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        WasteCategory category = wasteCategoryMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (category != null) {
            result.put("code", 200);
            result.put("data", category);
        } else {
            result.put("code", 404);
            result.put("message", "废物类别不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建废物类别")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody WasteCategory category) {
        int rows = wasteCategoryMapper.insert(category);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "创建成功" : "创建失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新废物类别")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody WasteCategory category) {
        category.setId(id);
        int rows = wasteCategoryMapper.update(category);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除废物类别")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = wasteCategoryMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
