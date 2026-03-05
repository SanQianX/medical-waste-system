-- 医院医疗废物管理系统数据库初始化脚本

-- 创建数据库（如需要）
-- CREATE DATABASE IF NOT EXISTS medical_waste DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE medical_waste;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    department_id BIGINT COMMENT '所属科室',
    role_id BIGINT COMMENT '角色ID',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态(0禁用/1正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(20) NOT NULL COMMENT '角色编码',
    description VARCHAR(200) COMMENT '描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 科室表
CREATE TABLE IF NOT EXISTS base_department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dept_code VARCHAR(20) NOT NULL COMMENT '科室编码',
    dept_name VARCHAR(100) NOT NULL COMMENT '科室名称',
    dept_type VARCHAR(20) COMMENT '科室类型',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科室表';

-- 废物类别表
CREATE TABLE IF NOT EXISTS base_waste_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_code VARCHAR(20) NOT NULL COMMENT '类别编码',
    category_name VARCHAR(100) NOT NULL COMMENT '类别名称',
    category_type VARCHAR(20) COMMENT '类别类型(感染性/损伤性/化学性/药物性/病理类)',
    description VARCHAR(500) COMMENT '说明',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='废物类别表';

-- 仓库表
CREATE TABLE IF NOT EXISTS base_warehouse (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouse_code VARCHAR(20) NOT NULL COMMENT '仓库编码',
    warehouse_name VARCHAR(100) NOT NULL COMMENT '仓库名称',
    warehouse_type VARCHAR(20) COMMENT '仓库类型',
    address VARCHAR(200) COMMENT '地址',
    capacity DECIMAL(10,2) COMMENT '容量(kg)',
    manager VARCHAR(50) COMMENT '管理员',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库表';

-- 处置机构表
CREATE TABLE IF NOT EXISTS base_disposal_org (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    org_code VARCHAR(20) NOT NULL COMMENT '机构编码',
    org_name VARCHAR(100) NOT NULL COMMENT '机构名称',
    license_no VARCHAR(50) COMMENT '许可证号',
    contact_person VARCHAR(50) COMMENT '联系人',
    phone VARCHAR(20) COMMENT '联系电话',
    address VARCHAR(200) COMMENT '地址',
    status TINYINT DEFAULT 1 COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='处置机构表';

-- 废物记录表
CREATE TABLE IF NOT EXISTS biz_waste_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    waste_code VARCHAR(50) NOT NULL UNIQUE COMMENT '废物唯一编码(条形码)',
    category_id BIGINT COMMENT '废物类别',
    department_id BIGINT COMMENT '产生科室',
    weight DECIMAL(10,2) NOT NULL COMMENT '重量(kg)',
    container_count INT DEFAULT 1 COMMENT '容器数量',
    generator_id BIGINT COMMENT '产生人(护士)ID',
    generate_time DATETIME NOT NULL COMMENT '产生时间',
    status TINYINT DEFAULT 1 COMMENT '状态(1待收集/2已收集/3已转运/4已处置)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_waste_code (waste_code),
    INDEX idx_department_id (department_id),
    INDEX idx_generate_time (generate_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='废物记录表';

-- 转运记录表
CREATE TABLE IF NOT EXISTS biz_transfer_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transfer_no VARCHAR(50) NOT NULL UNIQUE COMMENT '转运单号',
    waste_ids VARCHAR(500) COMMENT '废物ID列表',
    from_department_id BIGINT COMMENT '来源科室',
    to_warehouse_id BIGINT COMMENT '目标仓库',
    transporter_id BIGINT COMMENT '运送人员',
    transfer_time DATETIME COMMENT '转运时间',
    receive_time DATETIME COMMENT '接收时间',
    status TINYINT DEFAULT 1 COMMENT '状态(1待转运/2运输中/3已到达/4已完成)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_transfer_no (transfer_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转运记录表';

-- 贮存记录表
CREATE TABLE IF NOT EXISTS biz_storage_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    waste_id BIGINT NOT NULL COMMENT '废物记录ID',
    warehouse_id BIGINT COMMENT '仓库ID',
    in_time DATETIME NOT NULL COMMENT '入库时间',
    out_time DATETIME COMMENT '出库时间',
    storage_days INT COMMENT '存储天数',
    status TINYINT DEFAULT 1 COMMENT '状态(1贮存中/2已出库)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_waste_id (waste_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='贮存记录表';

-- 处置记录表
CREATE TABLE IF NOT EXISTS biz_disposal_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    waste_id BIGINT NOT NULL COMMENT '废物记录ID',
    disposal_org_id BIGINT COMMENT '处置机构',
    disposal_time DATETIME NOT NULL COMMENT '处置时间',
    disposal_weight DECIMAL(10,2) COMMENT '处置重量',
    disposal_method VARCHAR(50) COMMENT '处置方式',
    operator_id BIGINT COMMENT '操作人',
    status TINYINT DEFAULT 1 COMMENT '状态(1待处置/2已处置)',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_waste_id (waste_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='处置记录表';

-- 预警记录表
CREATE TABLE IF NOT EXISTS sys_warning (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    warning_type VARCHAR(20) NOT NULL COMMENT '预警类型(EXPIRE/OVERFLOW/EXCEPTION)',
    waste_id BIGINT COMMENT '关联废物ID',
    warehouse_id BIGINT COMMENT '仓库ID',
    warning_msg VARCHAR(500) NOT NULL COMMENT '预警信息',
    warning_level TINYINT COMMENT '预警级别(1低/2中/3高)',
    status TINYINT DEFAULT 0 COMMENT '状态(0未处理/1已处理)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    handle_time DATETIME COMMENT '处理时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预警记录表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS sys_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    module VARCHAR(50) COMMENT '操作模块',
    operation VARCHAR(100) COMMENT '操作类型',
    method VARCHAR(200) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    result TEXT COMMENT '返回结果',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    status INT COMMENT '状态(1成功/0失败)',
    error_msg VARCHAR(500) COMMENT '错误信息',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_module (module),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 插入默认管理员用户 (密码: admin123，BCrypt加密)
INSERT INTO sys_user (username, password, real_name, role_id, status) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '系统管理员', 1, 1);

-- 插入默认角色
INSERT INTO sys_role (role_name, role_code, description) VALUES
('管理员', 'ADMIN', '系统管理员'),
('护士', 'NURSE', '护士角色'),
('运送人员', 'TRANSPORTER', '运送人员角色'),
('仓库管理员', 'WAREHOUSE', '仓库管理员角色');

-- 插入默认科室
INSERT INTO base_department (dept_code, dept_name, dept_type, leader) VALUES
('DEPT001', '内科', '门诊', '张三'),
('DEPT002', '外科', '门诊', '李四'),
('DEPT003', '急诊科', '急诊', '王五'),
('DEPT004', '儿科', '门诊', '赵六'),
('DEPT005', '手术室', '住院', '钱七');

-- 插入默认废物类别
INSERT INTO base_waste_category (category_code, category_name, category_type, description) VALUES
('WC001', '感染性废物', '感染性', '携带病原微生物具有引发感染性疾病传播危险的医疗废物'),
('WC002', '损伤性废物', '损伤性', '能够刺伤或者割伤人体的废弃的医用锐器'),
('WC003', '化学性废物', '化学性', '具有毒性、腐蚀性、易燃易爆性的废弃的化学物品'),
('WC004', '药物性废物', '药物性', '过期、淘汰、变质或者被污染的废弃的药品'),
('WC005', '病理类废物', '病理类', '人体废弃物和病理切片后的废弃物');

-- 插入默认仓库
INSERT INTO base_warehouse (warehouse_code, warehouse_name, warehouse_type, address, capacity) VALUES
('WH001', '医疗废物暂存点', '暂存点', '医院地下室一层', 1000.00),
('WH002', '医疗废物贮存库', '贮存库', '医院后院', 5000.00);

-- 插入默认处置机构
INSERT INTO base_disposal_org (org_code, org_name, license_no, contact_person, phone, address) VALUES
('ORG001', 'XX医疗废物处置中心', 'DY-2024-001', '陈主任', '13800138001', 'XX市XX区XX路XX号');
