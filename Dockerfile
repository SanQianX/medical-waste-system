# 阶段1：构建镜像
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/maven:3.9.9-eclipse-temurin-8-noble-linuxarm64 AS builder

WORKDIR /app

# 复制pom文件和源代码并构建（跳过依赖下载）
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -B

# 阶段2：运行镜像
FROM swr.cn-north-4.myhuaweicloud.com/ddn-k8s/docker.io/openjdk:11-jdk-slim

WORKDIR /app

# 安装wget用于健康检查
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# 复制构建产物
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
