# 本地运行指南

## 方式一：使用IDE（推荐）

### 1. 导入项目
- 使用 IntelliJ IDEA 或 Eclipse 打开 `H:\段佳橙\medical-waste-system`
- IDEA会自动识别为Maven项目并下载依赖

### 2. 配置数据库
确保MySQL已启动（Docker中的MySQL容器）：
```bash
docker start medical-waste-mysql
```

### 3. 修改配置
确保 `application.yml` 中的数据库配置正确：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/medical_waste
    username: root
    password: root123
```

### 4. 运行项目
- 在IDE中运行 `MedicalWasteApplication.java`
- 访问 http://localhost:8080

---

## 方式二：使用命令行

### 1. 安装Maven
下载Maven: https://maven.apache.org/download.cgi

### 2. 构建项目
```bash
cd H:\段佳橙\medical-waste-system
mvn clean package
```

### 3. 运行
```bash
java -jar target/medical-waste-system-1.0.0.jar
```

---

## 验证MySQL状态
```bash
docker ps  # 查看运行中的容器
docker logs medical-waste-mysql  # 查看MySQL日志
```

## 访问地址
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
