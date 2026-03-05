# 医院医疗废物管理系统

## 系统概述

医院医疗废物管理系统是一套基于Spring Boot + MyBatis + MySQL + Thymeleaf开发的Web应用系统，用于实现医疗废物的全生命周期管理。

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.7.18
- **ORM**: MyBatis
- **数据库**: MySQL 8.0
- **模板引擎**: Thymeleaf
- **API文档**: SpringDoc OpenAPI (Swagger)

### 前端技术
- HTML5 + CSS3 + JavaScript
- Thymeleaf模板引擎
- 响应式布局

### 部署技术
- Docker容器化部署
- Docker Compose多容器编排

## 系统功能模块

### 1. 基础数据管理
- **科室管理**: 维护医院科室信息
- **废物类别管理**: 管理医疗废物分类（感染性、损伤性、化学性、药物性、病理类）
- **仓库管理**: 管理废物贮存仓库
- **处置机构管理**: 管理废物处置合作机构
- **用户管理**: 系统用户及权限管理

### 2. 业务管理
- **废物产生记录**: 记录废物产生信息，支持条形码管理
- **转运管理**: 废物从科室到仓库的转运流程
- **贮存管理**: 废物入库、出库、库存管理
- **处置管理**: 废物处置记录及处置机构对接

### 3. 统计报表
- 首页仪表盘展示关键数据指标

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

## 业务流转

```
废物产生(待收集) -> 收集(已收集) -> 转运 -> 贮存 -> 处置 -> 完成
```

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

### 主要接口

- `GET /api/v1/department/list` - 获取科室列表
- `GET /api/v1/waste-category/list` - 获取废物类别列表
- `GET /api/v1/waste/list` - 获取废物记录列表
- `GET /api/v1/transfer/list` - 获取转运记录列表
- `GET /api/v1/storage/list` - 获取贮存记录列表
- `GET /api/v1/disposal-record/list` - 获取处置记录列表
- `GET /api/v1/warehouse/list` - 获取仓库列表
- `GET /api/v1/disposal-org/list` - 获取处置机构列表
- `GET /api/v1/user/list` - 获取用户列表

## 注意事项

1. 首次启动会较慢（需要构建Java应用镜像）
2. MySQL首次启动会自动创建数据库和表
3. 如遇端口冲突，请修改docker-compose.yml中的端口映射
4. 生产环境建议修改默认数据库密码
