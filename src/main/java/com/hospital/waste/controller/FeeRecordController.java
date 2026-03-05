package com.hospital.waste.controller;

import com.hospital.waste.entity.FeeRecord;
import com.hospital.waste.service.FeeRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费用结算控制器
 */
@Tag(name = "费用结算管理")
@RestController
@RequestMapping("/api/v1/fee")
public class FeeRecordController {

    @Autowired
    private FeeRecordService feeRecordService;

    @Operation(summary = "查询所有费用记录")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list() {
        List<FeeRecord> list = feeRecordService.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据ID查询费用记录")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        FeeRecord feeRecord = feeRecordService.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (feeRecord != null) {
            result.put("code", 200);
            result.put("data", feeRecord);
        } else {
            result.put("code", 404);
            result.put("message", "费用记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据费用单号查询")
    @GetMapping("/no/{feeNo}")
    public ResponseEntity<Map<String, Object>> getByFeeNo(@PathVariable String feeNo) {
        FeeRecord feeRecord = feeRecordService.findByFeeNo(feeNo);
        Map<String, Object> result = new HashMap<>();
        if (feeRecord != null) {
            result.put("code", 200);
            result.put("data", feeRecord);
        } else {
            result.put("code", 404);
            result.put("message", "费用记录不存在");
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据废物ID查询费用记录")
    @GetMapping("/waste/{wasteId}")
    public ResponseEntity<Map<String, Object>> getByWasteId(@PathVariable Long wasteId) {
        List<FeeRecord> list = feeRecordService.findByWasteId(wasteId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据处置机构查询费用记录")
    @GetMapping("/org/{disposalOrgId}")
    public ResponseEntity<Map<String, Object>> getByOrgId(@PathVariable Long disposalOrgId) {
        List<FeeRecord> list = feeRecordService.findByDisposalOrgId(disposalOrgId);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "根据状态查询费用记录")
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getByStatus(@PathVariable Integer status) {
        List<FeeRecord> list = feeRecordService.findByStatus(status);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", list);
        result.put("total", list.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "创建费用记录")
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody FeeRecord feeRecord) {
        FeeRecord created = feeRecordService.create(feeRecord);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", created);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "更新费用记录")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody FeeRecord feeRecord) {
        feeRecord.setId(id);
        boolean success = feeRecordService.update(feeRecord);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "删除费用记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = feeRecordService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "结算费用")
    @PutMapping("/{id}/settle")
    public ResponseEntity<Map<String, Object>> settle(@PathVariable Long id) {
        boolean success = feeRecordService.settle(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "结算成功" : "结算失败");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "标记已支付")
    @PutMapping("/{id}/pay")
    public ResponseEntity<Map<String, Object>> pay(@PathVariable Long id) {
        boolean success = feeRecordService.pay(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", success ? 200 : 500);
        result.put("message", success ? "支付成功" : "支付失败");
        return ResponseEntity.ok(result);
    }
}
