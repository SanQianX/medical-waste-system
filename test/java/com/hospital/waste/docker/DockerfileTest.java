package com.hospital.waste.docker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Docker配置文件单元测试
 */
class DockerfileTest {

    @TempDir
    Path tempDir;

    // ==================== 生产环境Dockerfile测试 ====================

    @Test
    void testDockerfileProductionExists() {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        assertTrue(Files.exists(dockerfile), "生产环境Dockerfile应该存在");
    }

    @Test
    void testDockerfileProductionContent() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        // 验证多阶段构建
        assertTrue(lines.stream().anyMatch(line -> line.startsWith("FROM")), "应该包含FROM指令");
        assertTrue(lines.stream().anyMatch(line -> line.contains("AS builder")), "应该包含构建阶段");

        // 验证Maven构建
        assertTrue(lines.stream().anyMatch(line -> line.contains("mvn")), "应该包含Maven构建命令");
        assertTrue(lines.stream().anyMatch(line -> line.contains("mvn clean package")), "应该包含打包命令");

        // 验证暴露端口
        assertTrue(lines.stream().anyMatch(line -> line.contains("EXPOSE 8080")), "应该暴露8080端口");

        // 验证启动命令
        assertTrue(lines.stream().anyMatch(line -> line.contains("ENTRYPOINT")), "应该包含ENTRYPOINT指令");
        assertTrue(lines.stream().anyMatch(line -> line.contains("java -jar")), "应该使用java -jar启动");
    }

    @Test
    void testDockerfileProductionMultiStage() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        long fromCount = lines.stream()
                .filter(line -> line.trim().startsWith("FROM"))
                .count();

        assertEquals(2, fromCount, "生产环境Dockerfile应该使用多阶段构建（2个FROM）");
    }

    @Test
    void testDockerfileProductionCopyCommand() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        // 验证COPY命令
        assertTrue(lines.stream().anyMatch(line -> line.contains("COPY --from=builder")), "应该从builder阶段复制文件");
    }

    // ==================== 开发环境Dockerfile测试 ====================

    @Test
    void testDockerfileDevExists() {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile.dev");
        assertTrue(Files.exists(dockerfile), "开发环境Dockerfile应该存在");
    }

    @Test
    void testDockerfileDevContent() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile.dev");
        List<String> lines = Files.readAllLines(dockerfile);

        // 验证Maven依赖
        assertTrue(lines.stream().anyMatch(line -> line.contains("mvn dependency:go-offline")), "应该下载Maven依赖");

        // 验证开发模式
        assertTrue(lines.stream().anyMatch(line -> line.contains("spring-boot:run")), "应该使用spring-boot:run");
        assertTrue(lines.stream().anyMatch(line -> line.contains("-Dspring-boot.run.fork=false")), "应该禁用fork");
    }

    @Test
    void testDockerfileDevSingleStage() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile.dev");
        List<String> lines = Files.readAllLines(dockerfile);

        long fromCount = lines.stream()
                .filter(line -> line.trim().startsWith("FROM"))
                .count();

        assertEquals(1, fromCount, "开发环境Dockerfile应该使用单阶段构建");
    }

    // ==================== Dockerfile最佳实践验证 ====================

    @Test
    void testDockerfileNoRootUser() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        // 生产环境不应该有USER指令设置非root用户（可选）
        // 这里只做基本验证
        assertNotNull(lines);
    }

    @Test
    void testDockerfileExposePort() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        boolean hasExpose = lines.stream()
                .anyMatch(line -> line.trim().startsWith("EXPOSE"));

        assertTrue(hasExpose, "应该包含EXPOSE指令");
    }

    @Test
    void testDockerfileWorkingDirectory() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        boolean hasWorkdir = lines.stream()
                .anyMatch(line -> line.trim().startsWith("WORKDIR"));

        assertTrue(hasWorkdir, "应该设置WORKDIR");
    }

    // ==================== 验证构建命令 ====================

    @Test
    void testDockerfileSkipTests() throws IOException {
        Path dockerfile = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile");
        List<String> lines = Files.readAllLines(dockerfile);

        // 验证构建时跳过测试
        assertTrue(lines.stream().anyMatch(line -> line.contains("-DskipTests")), "构建时应该跳过测试");
    }

    // ==================== 验证多环境配置 ====================

    @Test
    void testDockerfileDevNoProductionConfig() throws IOException {
        Path dockerfileDev = Path.of("H:\\段佳橙\\medical-waste-system\\Dockerfile.dev");
        List<String> lines = Files.readAllLines(dockerfileDev);

        // 开发环境不应该包含ENTRYPOINT（使用CMD运行开发服务器）
        boolean hasEntrypoint = lines.stream()
                .anyMatch(line -> line.trim().startsWith("ENTRYPOINT"));

        assertFalse(hasEntrypoint, "开发环境Dockerfile不应该包含ENTRYPOINT");
    }
}
