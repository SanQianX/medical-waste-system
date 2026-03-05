package com.hospital.waste.service;

import com.hospital.waste.entity.Container;
import com.hospital.waste.mapper.ContainerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 容器管理业务层
 */
@Service
public class ContainerService {

    @Autowired
    private ContainerMapper containerMapper;

    public List<Container> findAll() {
        return containerMapper.selectAll();
    }

    public Container findById(Long id) {
        return containerMapper.selectById(id);
    }

    public Container findByCode(String containerCode) {
        return containerMapper.selectByCode(containerCode);
    }

    public List<Container> findByDepartmentId(Long departmentId) {
        return containerMapper.selectByDepartmentId(departmentId);
    }

    public List<Container> findByStatus(Integer status) {
        return containerMapper.selectByStatus(status);
    }

    public Container create(Container container) {
        // 生成容器编号
        if (container.getContainerCode() == null || container.getContainerCode().isEmpty()) {
            container.setContainerCode(generateContainerCode());
        }
        if (container.getStatus() == null) {
            container.setStatus(1);
        }
        if (container.getCurrentCount() == null) {
            container.setCurrentCount(0);
        }
        containerMapper.insert(container);
        return container;
    }

    public boolean update(Container container) {
        return containerMapper.update(container) > 0;
    }

    public boolean delete(Long id) {
        return containerMapper.deleteById(id) > 0;
    }

    /**
     * 生成容器编号
     * 格式: CON + 年月日 + 4位流水号
     */
    private String generateContainerCode() {
        String dateStr = java.time.LocalDate.now().toString().replace("-", "");
        String serial = String.format("%04d", System.currentTimeMillis() % 10000);
        return "CON" + dateStr + serial;
    }
}
