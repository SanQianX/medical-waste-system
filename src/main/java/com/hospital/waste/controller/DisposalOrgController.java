package com.hospital.waste.controller;

import com.hospital.waste.entity.DisposalOrg;
import com.hospital.waste.mapper.DisposalOrgMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处置机构管理Controller
 */
@Tag(name = "处置机构管理")
@RestController
@RequestMapping("/api/v1/disposal-org")
public class DisposalOrgController {

    @Autowired
    private DisposalOrgMapper disposalOrgMapper;

    @Operation(summary = "获取处置机构列表")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<DisposalOrg> list = disposalOrgMapper.selectAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询处置机构")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        DisposalOrg org = disposalOrgMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        if (org != null) {
            result.put("code", 200);
            result.put("data", org);
        } else {
            result.put("code", 404);
            result.put("message", "处置机构不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建处置机构")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody DisposalOrg org) {
        int rows = disposalOrgMapper.insert(org);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "创建成功" : "创建失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新处置机构")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody DisposalOrg org) {
        org.setId(id);
        int rows = disposalOrgMapper.update(org);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除处置机构")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        int rows = disposalOrgMapper.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", rows > 0 ? 200 : 500);
        result.put("message", rows > 0 ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}
