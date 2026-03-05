package com.hospital.waste.service;

import com.hospital.waste.entity.SysWarning;
import com.hospital.waste.entity.StorageRecord;
import com.hospital.waste.entity.WasteRecord;
import com.hospital.waste.entity.Warehouse;
import com.hospital.waste.mapper.SysWarningMapper;
import com.hospital.waste.mapper.WasteRecordMapper;
import com.hospital.waste.mapper.WarehouseMapper;
import com.hospital.waste.mapper.StorageRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 预警自动生成定时任务服务
 */
@Service
public class WarningSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(WarningSchedulerService.class);

    @Autowired
    private SysWarningMapper sysWarningMapper;

    @Autowired
    private WasteRecordMapper wasteRecordMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private StorageRecordMapper storageRecordMapper;

    /**
     * 检查超时未转运的废物 - 每小时执行一次
     * 超时阈值：超过24小时未转运（状态1=待收集）
     */
    @Scheduled(fixedRate = 3600000) // 每小时执行
    public void checkExpiredWaste() {
        logger.info("开始检查超时未转运的废物...");

        List<WasteRecord> wasteRecords = wasteRecordMapper.selectAll();
        LocalDateTime now = LocalDateTime.now();
        int warningCount = 0;

        for (WasteRecord waste : wasteRecords) {
            // 只检查待收集状态的废物（状态1=待收集）
            if (waste.getStatus() != null && waste.getStatus() == 1) {
                LocalDateTime checkTime = waste.getGenerateTime() != null ? waste.getGenerateTime() : waste.getCreateTime();
                if (checkTime == null) continue;

                long hoursSinceCreation = ChronoUnit.HOURS.between(checkTime, now);
                if (hoursSinceCreation > 24) {
                    // 检查是否已存在未处理的超时预警
                    List<SysWarning> existingWarnings = sysWarningMapper.selectByWasteIdAndType(
                        waste.getId(), "EXPIRE");
                    boolean hasUnprocessedWarning = existingWarnings.stream()
                        .anyMatch(w -> w.getStatus() == null || w.getStatus() == 0);

                    if (!hasUnprocessedWarning) {
                        SysWarning warning = new SysWarning();
                        warning.setWarningType("EXPIRE");
                        warning.setWasteId(waste.getId());
                        warning.setWarningMsg("废物[" + waste.getWasteCode() + "]已超过24小时未转运");
                        warning.setWarningLevel(2);
                        warning.setStatus(0);
                        warning.setCreateTime(LocalDateTime.now());
                        sysWarningMapper.insert(warning);
                        warningCount++;
                        logger.info("生成超时预警: {}", waste.getWasteCode());
                    }
                }
            }
        }

        logger.info("超时预警检查完成，共生成{}条预警", warningCount);
    }

    /**
     * 检查仓库容量超限 - 每2小时执行一次
     * 超量阈值：超过80%容量
     */
    @Scheduled(fixedRate = 7200000) // 每2小时执行
    public void checkWarehouseOverflow() {
        logger.info("开始检查仓库容量...");

        List<Warehouse> warehouses = warehouseMapper.selectAll();
        int warningCount = 0;

        for (Warehouse warehouse : warehouses) {
            if (warehouse.getCapacity() != null) {
                // 计算当前贮存中的废物数量
                List<StorageRecord> storageRecords = storageRecordMapper.selectAll();
                long currentCount = storageRecords.stream()
                    .filter(s -> s.getWarehouseId() != null
                        && s.getWarehouseId().equals(warehouse.getId())
                        && s.getStatus() != null && s.getStatus() == 1)
                    .count();
                double usagePercent = (currentCount * 100.0);
                if (warehouse.getCapacity() > 0) {
                    usagePercent = (currentCount * 100.0) / warehouse.getCapacity();
                }
                if (usagePercent > 80) {
                    // 检查是否已存在未处理的超量预警
                    List<SysWarning> existingWarnings = sysWarningMapper.selectByWarehouseId(warehouse.getId());
                    boolean hasUnprocessedWarning = existingWarnings.stream()
                        .anyMatch(w -> "OVERFLOW".equals(w.getWarningType()) &&
                              (w.getStatus() == null || w.getStatus() == 0));

                    if (!hasUnprocessedWarning) {
                        SysWarning warning = new SysWarning();
                        warning.setWarningType("OVERFLOW");
                        warning.setWarehouseId(warehouse.getId());
                        warning.setWarningMsg("仓库[" + warehouse.getWarehouseName() + "]容量已达" +
                            String.format("%.1f", usagePercent) + "%，超过80%阈值");
                        warning.setWarningLevel(3);
                        warning.setStatus(0);
                        warning.setCreateTime(LocalDateTime.now());
                        sysWarningMapper.insert(warning);
                        warningCount++;
                        logger.info("生成超量预警: {}", warehouse.getWarehouseName());
                    }
                }
            }
        }

        logger.info("仓库容量检查完成，共生成{}条预警", warningCount);
    }

    /**
     * 检查异常废物记录 - 每天凌晨2点执行
     * 检查重量异常、状态异常等情况
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
    public void checkExceptionWaste() {
        logger.info("开始检查异常废物记录...");

        List<WasteRecord> wasteRecords = wasteRecordMapper.selectAll();
        LocalDateTime now = LocalDateTime.now();
        int warningCount = 0;

        for (WasteRecord waste : wasteRecords) {
            boolean hasException = false;
            String exceptionMsg = null;

            // 检查重量是否异常（<=0 或 >1000kg）
            if (waste.getWeight() != null && (waste.getWeight() <= 0 || waste.getWeight() > 1000)) {
                hasException = true;
                exceptionMsg = "废物[" + waste.getWasteCode() + "]重量异常: " + waste.getWeight() + "kg";
            }

            // 检查是否长时间处于已收集状态但未转运（超过48小时，状态2=已收集）
            if (waste.getStatus() != null && waste.getStatus() == 2) {
                LocalDateTime checkTime = waste.getGenerateTime() != null ? waste.getGenerateTime() : waste.getCreateTime();
                if (checkTime != null) {
                    long hoursInTransfer = ChronoUnit.HOURS.between(checkTime, now);
                    if (hoursInTransfer > 48) {
                        hasException = true;
                        exceptionMsg = "废物[" + waste.getWasteCode() + "]已收集但超过48小时未转运";
                    }
                }
            }

            if (hasException && exceptionMsg != null) {
                // 检查是否已存在未处理的异常预警
                List<SysWarning> existingWarnings = sysWarningMapper.selectByWasteIdAndType(
                    waste.getId(), "EXCEPTION");
                boolean hasUnprocessedWarning = existingWarnings.stream()
                    .anyMatch(w -> w.getStatus() == null || w.getStatus() == 0);

                if (!hasUnprocessedWarning) {
                    SysWarning warning = new SysWarning();
                    warning.setWarningType("EXCEPTION");
                    warning.setWasteId(waste.getId());
                    warning.setWarningMsg(exceptionMsg);
                    warning.setWarningLevel(1);
                    warning.setStatus(0);
                    warning.setCreateTime(LocalDateTime.now());
                    sysWarningMapper.insert(warning);
                    warningCount++;
                    logger.info("生成异常预警: {}", exceptionMsg);
                }
            }
        }

        logger.info("异常检查完成，共生成{}条预警", warningCount);
    }
}
