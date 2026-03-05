# 医院医疗废物管理系统

## 项目简介

医院医疗废物管理系统是一套基于 Java SpringBoot 的 B/S 架构医疗废物管理系统，实现医疗废物从产生、分类、转运、处置的全流程数字化闭环管理，解决传统人工台账方式存在的问题，提升管理效率与规范性。

系统需具备基础信息管理、废物产生录入、转运追踪、处置确认、数据统计、预警提醒等功能，并能与医院现有 HIS/LIS 系统兼容。

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.7.18
- **ORM**: MyBatis
- **数据库**: MySQL 8.0
- **模板引擎**: Thymeleaf
- **API文档**: SpringDoc OpenAPI (Swagger)
- **JDK版本**: OpenJDK 11

### 前端技术
- HTML5 + CSS3 + JavaScript
- Thymeleaf模板引擎
- 响应式布局

### 部署技术
- Docker容器化部署
- Docker Compose多容器编排

## 功能模块

### 1. 基础信息管理
- **科室管理**: 维护医院组织架构、科室信息
- **人员管理**: 医护人员、操作人员信息管理
- **废物类别管理**: 管理医疗废物分类（感染性、损伤性、化学性、药物性、病理类）
- **容器管理**: 专用容器信息、容量、状态管理

### 2. 废物产生管理
- **产生登记**: 医护人员在源头录入废物信息
- **标签打印**: 自动生成唯一追溯码
- **称重记录**: 记录废物重量

### 3. 转运管理
- **任务分配**: 转运任务创建与分配
- **扫码交接**: 后勤人员通过扫码完成交接
- **路线追踪**: 记录转运时间、人员、路线

### 4. 处置管理
- **处置确认**: 处置单位确认接收
- **处置记录**: 记录处置方式、处置时间
- **费用结算**: 生成费用报表

### 5. 贮存管理
- **入库管理**: 废物入库登记
- **出库管理**: 废物出库登记
- **库存查询**: 实时库存查询

### 6. 统计报表
- **日报表**: 每日废物统计数据
- **月报表**: 每月统计数据
- **年报表**: 年度统计数据
- **科室统计**: 按科室统计废物产生量
- **超时预警统计**: 预警统计

### 7. 预警提醒
- **超时未转运预警**: 检测废物超时未转运
- **库存超限预警**: 库存超限提醒
- **设备异常预警**: 设备异常提醒

### 8. 系统管理
- **用户管理**: 用户账号管理
- **角色权限**: RBAC 权限控制
- **操作日志**: 记录所有敏感操作

## 页面路由体系

| 模块 | 路由 | 说明 |
|------|------|------|
| 首页 | `/page/dashboard` | 仪表盘 |
| 科室管理 | `/page/department` | 科室CRUD |
| 废物类别 | `/page/waste-category` | 类别管理 |
| 仓库管理 | `/page/warehouse` | 仓库管理 |
| 处置机构 | `/page/disposal-org` | 机构管理 |
| 用户管理 | `/page/user` | 用户管理 |
| 废物记录 | `/page/waste-record` | 废物产生记录 |
| 转运管理 | `/page/transfer` | 转运流程 |
| 贮存管理 | `/page/storage` | 贮存管理 |
| 处置管理 | `/page/disposal` | 处置记录 |

## 业务流程

```
产生科室 → 废物称重/登记（产生记录） → 暂存点 → 转运人员扫码接收 → 院内转运 → 集中处置点 → 处置确认 → 费用结算
                                                              ↖ 预警超时/超量
```

### 废物状态流转
```
待收集 → 已收集 → 转运中 → 已入库 → 处置中 → 已完成
```

## 数据库设计

### 核心数据表

| 表名 | 说明 |
|------|------|
| `department` | 科室信息 |
| `waste_category` | 废物类别 |
| `container` | 容器信息 |
| `waste_record` | 废物产生记录 |
| `transfer_record` | 转运记录 |
| `storage_record` | 贮存记录 |
| `disposal_record` | 处置记录 |
| `disposal_org` | 处置机构 |
| `warehouse` | 仓库信息 |
| `sys_user` | 系统用户 |
| `sys_role` | 角色信息 |
| `sys_warning` | 预警记录 |
| `operation_log` | 操作日志 |

### 追溯码规则
采用 `YYYYMMDD+科室编码+流水号` 格式，全局唯一。

## Docker部署指南

### 前置要求

- Docker Desktop 已安装并运行
- 至少 4GB 可用内存

### 快速启动

```bash
cd H:\段佳橙\medical-waste-system

# 启动所有服务
docker-compose up -d

# 查看运行状态
docker-compose ps

# 查看应用日志
docker-compose logs -f app
```

### 访问地址

| 服务 | 地址 |
|------|------|
| 应用首页 | http://localhost:8080/page/dashboard |
| 科室管理 | http://localhost:8080/page/department |
| API文档 | http://localhost:8080/swagger-ui.html |
| MySQL | localhost:3306 |

### 默认账号

- 用户名: admin
- 密码: admin123

### 常用命令

```bash
# 停止服务
docker-compose down

# 停止并删除数据卷（重置数据库）
docker-compose down -v

# 重启应用
docker-compose restart app

# 查看实时日志
docker-compose logs -f

# 进入MySQL容器
docker exec -it medical-waste-mysql mysql -uroot -proot123 medical_waste
```

## 目录结构

```
medical-waste-system/
├── Dockerfile                    # 应用镜像构建文件
├── Dockerfile.dev                # 开发模式Dockerfile
├── docker-compose.yml           # 生产环境Docker编排
├── docker-compose-dev.yml       # 开发环境Docker编排
├── init.sql                     # 数据库初始化脚本
├── pom.xml                     # Maven配置
├── README.md                    # 项目说明
└── src/
    └── main/
        ├── java/
        │   └── com/hospital/waste/
        │       ├── MedicalWasteApplication.java  # 启动类
        │       ├── common/                       # 公共组件
        │       ├── controller/                   # 控制器
        │       │   ├── DepartmentController.java
        │       │   ├── WasteCategoryController.java
        │       │   ├── WasteRecordController.java
        │       │   ├── TransferRecordController.java
        │       │   ├── StorageRecordController.java
        │       │   ├── DisposalRecordController.java
        │       │   ├── WarehouseController.java
        │       │   ├── DisposalOrgController.java
        │       │   ├── UserController.java
        │       │   ├── PageController.java       # 页面路由
        │       │   └── IndexController.java
        │       ├── entity/                       # 实体类
        │       ├── mapper/                       # 数据访问层
        │       └── service/                      # 业务逻辑层
        └── resources/
            ├── mapper/                          # MyBatis映射文件
            ├── templates/                        # Thymeleaf模板
            │   ├── layout.html                   # 公共布局
            │   ├── dashboard.html                # 首页仪表盘
            │   ├── department/list.html          # 科室管理
            │   ├── waste-category/list.html      # 废物类别
            │   ├── warehouse/list.html            # 仓库管理
            │   ├── disposal-org/list.html        # 处置机构
            │   ├── user/list.html                # 用户管理
            │   ├── waste-record/list.html        # 废物记录
            │   ├── transfer/list.html            # 转运管理
            │   ├── storage/list.html              # 贮存管理
            │   └── disposal/list.html             # 处置管理
            ├── application.yml                    # 主配置
            └── application-docker.yml             # Docker配置
```

## 环境变量说明

| 变量名 | 默认值 | 说明 |
|--------|--------|------|
| DB_HOST | mysql | 数据库主机名 |
| DB_PORT | 3306 | 数据库端口 |
| DB_NAME | medical_waste | 数据库名称 |
| DB_USERNAME | root | 数据库用户名 |
| DB_PASSWORD | root123 | 数据库密码 |
| SPRING_PROFILES_ACTIVE | docker | Spring配置文件 |

## API接口说明

系统提供RESTful API接口，完整文档请访问Swagger UI: http://localhost:8080/swagger-ui.html

### 基础信息接口

| 端点 | 方法 | 功能 |
|------|------|------|
| `/api/v1/department/list` | GET | 获取科室列表 |
| `/api/v1/department` | POST | 新增科室 |
| `/api/v1/department/{id}` | PUT | 更新科室 |
| `/api/v1/department/{id}` | DELETE | 删除科室 |
| `/api/v1/waste-category/list` | GET | 获取废物类别列表 |
| `/api/v1/waste-category` | POST | 新增废物类别 |
| `/api/v1/warehouse/list` | GET | 获取仓库列表 |
| `/api/v1/disposal-org/list` | GET | 获取处置机构列表 |
| `/api/v1/user/list` | GET | 获取用户列表 |
| `/api/v1/role/list` | GET | 获取角色列表 |

### 业务接口

| 端点 | 方法 | 功能 |
|------|------|------|
| `/api/v1/waste/list` | GET | 获取废物记录列表 |
| `/api/v1/waste` | POST | 创建废物产生记录 |
| `/api/v1/waste/{id}` | GET | 获取废物详情 |
| `/api/v1/transfer/list` | GET | 获取转运记录列表 |
| `/api/v1/transfer` | POST | 创建转运任务 |
| `/api/v1/transfer/{id}/scan` | POST | 扫码交接 |
| `/api/v1/storage/list` | GET | 获取贮存记录列表 |
| `/api/v1/storage/in` | POST | 废物入库 |
| `/api/v1/storage/out` | POST | 废物出库 |
| `/api/v1/disposal-record/list` | GET | 获取处置记录列表 |
| `/api/v1/disposal-record` | POST | 创建处置记录 |

### 统计与预警接口

| 端点 | 方法 | 功能 |
|------|------|------|
| `/api/v1/statistics/dashboard` | GET | 获取仪表盘数据 |
| `/api/v1/warning/list` | GET | 获取预警列表 |
| `/api/v1/warning/{id}/handle` | POST | 处理预警 |
| `/api/v1/operation-log/list` | GET | 获取操作日志 |

## 快速开始指南

### 本地开发环境

1. **环境要求**
   - JDK 11+
   - Maven 3.8+
   - MySQL 8.0
   - IntelliJ IDEA / VS Code

2. **配置数据库**
   - 创建数据库 `medical_waste`
   - 执行 `init.sql` 初始化数据

3. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

4. **访问系统**
   - 打开浏览器访问 http://localhost:8080
   - 使用默认账号登录

### Docker部署

参考上文 "Docker部署指南" 部分。

## 注意事项

1. 首次启动会较慢（需要构建Java应用镜像）
2. MySQL首次启动会自动创建数据库和表
3. 如遇端口冲突，请修改docker-compose.yml中的端口映射
4. 生产环境建议修改默认数据库密码
