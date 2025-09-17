# 多阶段构建 Dockerfile for Flexes Backend

# 第一阶段：构建阶段
FROM maven:3.8.6-openjdk-8-slim AS build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 和源代码
COPY pom.xml .
COPY src ./src

# 构建应用
RUN mvn clean package -DskipTests

# 第二阶段：运行阶段
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 创建非root用户
RUN groupadd -r flexes && useradd -r -g flexes flexes

# 安装必要的工具
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 从构建阶段复制jar文件
COPY --from=build /app/target/*.jar app.jar

# 创建日志目录
RUN mkdir -p /app/logs && chown -R flexes:flexes /app

# 切换到非root用户
USER flexes

# 暴露端口
EXPOSE 8081

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8081/api/actuator/health || exit 1

# 设置JVM参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:+UseStringDeduplication"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
