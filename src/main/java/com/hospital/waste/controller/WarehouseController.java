package com.hospital.waste.controller;

import com.hospital.waste.entity.Warehouse;
import com.hospital.waste.mapper.WarehouseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库管理Controller
 */
@Tag(name = "仓库管理")
@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Operation(summary = "获取仓库列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<Warehouse> list = warehouseMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询仓库")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Warehouse warehouse = warehouseMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (warehouse != null) {
            result.put("code", 200);
            result.put("data", warehouse);
        } else {
            result.put("code", 404);
            result.put("message", "仓库不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建仓库")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Warehouse warehouse) {
        int rows = warehouseMapper.insert(warehouse);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "创建成功" : "创建失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新仓库")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Warehouse warehouse) {
        warehouse.setId(id);
        int rows = warehouseMapper.update(warehouse);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除仓库")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = warehouseMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
