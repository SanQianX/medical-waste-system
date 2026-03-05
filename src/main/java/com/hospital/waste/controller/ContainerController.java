package com.hospital.waste.controller;

import com.hospital.waste.entity.Container;
import com.hospital.waste.service.ContainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 容器管理控制器
 */
@Tag(name = "容器管理")
@RestController
@RequestMapping("/api/v1/container")
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    @Operation(summary = "查询所有容器")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<Container> list = containerService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询容器")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Container container = containerService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (container != null) {
            result.put("code", 200);
            result.put("data", container);
        } else {
            result.put("code", 404);
            result.put("message", "容器不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据容器编号查询")
    @GetMapping("/code/{containerCode}")
    public ResponseEntity<Map<String, Object>> getByCode(@PathVariable String containerCode) {
        Container container = containerService.findByCode(containerCode);
        Map<String, Object> result = new HashMap<>();
        if (container != null) {
            result.put("code", 200);
            result.put("data", container);
        } else {
            result.put("code", 404);
            result.put("message", "容器不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据科室查询容器")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Map<String, Object>> getByDepartment(@PathVariable Long departmentId) {
        List<Container> list = containerService.findByDepartmentId(departmentId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据状态查询容器")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<Container> list = containerService.findByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建容器")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Container container) {
        Container created = containerService.create(container);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新容器")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Container container) {
        container.setId(id);
        boolean success = containerService.update(container);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除容器")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = containerService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
