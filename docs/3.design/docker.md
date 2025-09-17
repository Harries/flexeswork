# Flexes 平台 Docker 环境配置

## 📋 概述

本文档提供了 Flexes 远程工程招聘平台开发环境中 MySQL 和 Redis 的 Docker 启动配置和命令。

---

## ⚡ 快速启动 (Windows PowerShell)

如果您使用 Windows PowerShell，可以直接复制以下命令：

### MySQL 快速启动
```powershell
docker run -d --name flexes-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root_password -e MYSQL_DATABASE=flexes_platform -e MYSQL_USER=flexes_user -e MYSQL_PASSWORD=flexes_password -v flexes_mysql_data:/var/lib/mysql mysql:8.0.33 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-authentication-plugin=mysql_native_password
```

### Redis 快速启动
```powershell
docker run -d --name flexes-redis -p 6379:6379 -v flexes_redis_data:/data redis:6-alpine redis-server --requirepass 123456
```

### 验证服务
```powershell
# 检查容器状态
docker ps

# 测试 MySQL 连接
docker exec -it flexes-mysql mysql -u flexes_user -pflexes_password -e "SELECT VERSION();"

# 测试 Redis 连接
docker exec -it flexes-redis redis-cli -a 123456 ping
```

---

## 🗄️ MySQL Docker 配置

### 单独启动 MySQL 容器

#### 基础启动命令

**Linux/macOS (Bash):**
```bash
docker run -d \
  --name flexes-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root_password \
  -e MYSQL_DATABASE=flexes_platform \
  -e MYSQL_USER=flexes_user \
  -e MYSQL_PASSWORD=flexes_password \
  -v flexes_mysql_data:/var/lib/mysql \
  mysql:8.0.33
```

**Windows (PowerShell):**
```powershell
docker run -d `
  --name flexes-mysql `
  -p 3306:3306 `
  -e MYSQL_ROOT_PASSWORD=root_password `
  -e MYSQL_DATABASE=flexes_platform `
  -e MYSQL_USER=flexes_user `
  -e MYSQL_PASSWORD=flexes_password `
  -v flexes_mysql_data:/var/lib/mysql `
  mysql:8.0.33
```

**Windows (CMD):**
```cmd
docker run -d ^
  --name flexes-mysql ^
  -p 3306:3306 ^
  -e MYSQL_ROOT_PASSWORD=root_password ^
  -e MYSQL_DATABASE=flexes_platform ^
  -e MYSQL_USER=flexes_user ^
  -e MYSQL_PASSWORD=flexes_password ^
  -v flexes_mysql_data:/var/lib/mysql ^
  mysql:8.0.33
```

#### 带字符集配置的启动命令

**Linux/macOS (Bash):**
```bash
docker run -d \
  --name flexes-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root_password \
  -e MYSQL_DATABASE=flexes_platform \
  -e MYSQL_USER=flexes_user \
  -e MYSQL_PASSWORD=flexes_password \
  -v flexes_mysql_data:/var/lib/mysql \
  mysql:8.0.33 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_unicode_ci \
  --default-authentication-plugin=mysql_native_password
```

**Windows (PowerShell):**
```powershell
docker run -d `
  --name flexes-mysql `
  -p 3306:3306 `
  -e MYSQL_ROOT_PASSWORD=root_password `
  -e MYSQL_DATABASE=flexes_platform `
  -e MYSQL_USER=flexes_user `
  -e MYSQL_PASSWORD=flexes_password `
  -v flexes_mysql_data:/var/lib/mysql `
  mysql:8.0.33 `
  --character-set-server=utf8mb4 `
  --collation-server=utf8mb4_unicode_ci `
  --default-authentication-plugin=mysql_native_password
```

#### 带配置文件的启动命令

**Linux/macOS (Bash):**
```bash
# 创建 MySQL 配置文件目录
mkdir -p ./docker/mysql/conf

# 创建配置文件 ./docker/mysql/conf/my.cnf
cat > ./docker/mysql/conf/my.cnf << EOF
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
default-authentication-plugin=mysql_native_password
max_connections=200
innodb_buffer_pool_size=256M
innodb_log_file_size=64M
slow_query_log=1
slow_query_log_file=/var/log/mysql/slow.log
long_query_time=2
EOF

# 启动容器
docker run -d \
  --name flexes-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root_password \
  -e MYSQL_DATABASE=flexes_platform \
  -e MYSQL_USER=flexes_user \
  -e MYSQL_PASSWORD=flexes_password \
  -v flexes_mysql_data:/var/lib/mysql \
  -v ./docker/mysql/conf/my.cnf:/etc/mysql/conf.d/my.cnf \
  -v flexes_mysql_logs:/var/log/mysql \
  mysql:8.0.33
```

**Windows (PowerShell):**
```powershell
# 创建 MySQL 配置文件目录
New-Item -ItemType Directory -Force -Path "./docker/mysql/conf"

# 创建配置文件 ./docker/mysql/conf/my.cnf
@"
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
default-authentication-plugin=mysql_native_password
max_connections=200
innodb_buffer_pool_size=256M
innodb_log_file_size=64M
slow_query_log=1
slow_query_log_file=/var/log/mysql/slow.log
long_query_time=2
"@ | Out-File -FilePath "./docker/mysql/conf/my.cnf" -Encoding UTF8

# 启动容器
docker run -d `
  --name flexes-mysql `
  -p 3306:3306 `
  -e MYSQL_ROOT_PASSWORD=root_password `
  -e MYSQL_DATABASE=flexes_platform `
  -e MYSQL_USER=flexes_user `
  -e MYSQL_PASSWORD=flexes_password `
  -v flexes_mysql_data:/var/lib/mysql `
  -v ${PWD}/docker/mysql/conf/my.cnf:/etc/mysql/conf.d/my.cnf `
  -v flexes_mysql_logs:/var/log/mysql `
  mysql:8.0.33
```

### MySQL 环境变量说明
- `MYSQL_ROOT_PASSWORD`: root 用户密码
- `MYSQL_DATABASE`: 自动创建的数据库名
- `MYSQL_USER`: 创建的普通用户名
- `MYSQL_PASSWORD`: 普通用户密码

### MySQL 数据卷说明
- `flexes_mysql_data`: 数据持久化存储
- `flexes_mysql_logs`: 日志文件存储

---

## 🚀 Redis Docker 配置

### 单独启动 Redis 容器

#### 基础启动命令（带密码）

**Linux/macOS (Bash):**
```bash
docker run -d \
  --name flexes-redis \
  -p 6379:6379 \
  -v flexes_redis_data:/data \
  redis:6-alpine \
  redis-server --requirepass 123456
```

**Windows (PowerShell):**
```powershell
docker run -d `
  --name flexes-redis `
  -p 6379:6379 `
  -v flexes_redis_data:/data `
  redis:6-alpine `
  redis-server --requirepass 123456
```

**Windows (CMD):**
```cmd
docker run -d ^
  --name flexes-redis ^
  -p 6379:6379 ^
  -v flexes_redis_data:/data ^
  redis:6-alpine ^
  redis-server --requirepass 123456
```

#### 带配置文件的启动命令

**Linux/macOS (Bash):**
```bash
# 创建 Redis 配置文件目录
mkdir -p ./docker/redis/conf

# 创建配置文件 ./docker/redis/conf/redis.conf
cat > ./docker/redis/conf/redis.conf << EOF
# 网络配置
bind 0.0.0.0
port 6379
timeout 300

# 认证配置
requirepass 123456

# 持久化配置
save 900 1
save 300 10
save 60 10000
rdbcompression yes
dbfilename dump.rdb
dir /data

# 日志配置
loglevel notice
logfile /var/log/redis/redis.log

# 内存配置
maxmemory 256mb
maxmemory-policy allkeys-lru

# 其他配置
tcp-keepalive 300
tcp-backlog 511
databases 16
EOF

# 启动容器
docker run -d \
  --name flexes-redis \
  -p 6379:6379 \
  -v flexes_redis_data:/data \
  -v flexes_redis_logs:/var/log/redis \
  -v ./docker/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
  redis:6-alpine \
  redis-server /usr/local/etc/redis/redis.conf
```

**Windows (PowerShell):**
```powershell
# 创建 Redis 配置文件目录
New-Item -ItemType Directory -Force -Path "./docker/redis/conf"

# 创建配置文件 ./docker/redis/conf/redis.conf
@"
# 网络配置
bind 0.0.0.0
port 6379
timeout 300

# 认证配置
requirepass 123456

# 持久化配置
save 900 1
save 300 10
save 60 10000
rdbcompression yes
dbfilename dump.rdb
dir /data

# 日志配置
loglevel notice
logfile /var/log/redis/redis.log

# 内存配置
maxmemory 256mb
maxmemory-policy allkeys-lru

# 其他配置
tcp-keepalive 300
tcp-backlog 511
databases 16
"@ | Out-File -FilePath "./docker/redis/conf/redis.conf" -Encoding UTF8

# 启动容器
docker run -d `
  --name flexes-redis `
  -p 6379:6379 `
  -v flexes_redis_data:/data `
  -v flexes_redis_logs:/var/log/redis `
  -v ${PWD}/docker/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf `
  redis:6-alpine `
  redis-server /usr/local/etc/redis/redis.conf
```

### Redis 配置说明
- `requirepass 123456`: 设置访问密码
- `save`: 数据持久化策略
- `maxmemory`: 最大内存限制
- `maxmemory-policy`: 内存淘汰策略

---

## 🐳 Docker Compose 配置

### 完整的 docker-compose.yml
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0.33
    container_name: flexes-mysql
    restart: unless-stopped
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: flexes_platform
      MYSQL_USER: flexes_user
      MYSQL_PASSWORD: flexes_password
    volumes:
      - flexes_mysql_data:/var/lib/mysql
      - flexes_mysql_logs:/var/log/mysql
      - ./docker/mysql/conf/my.cnf:/etc/mysql/conf.d/my.cnf
      - ./docs/sql:/docker-entrypoint-initdb.d
    command: >
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_unicode_ci
      --default-authentication-plugin=mysql_native_password
    networks:
      - flexes-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "flexes_user", "-pflexes_password"]
      timeout: 20s
      retries: 10

  redis:
    image: redis:6-alpine
    container_name: flexes-redis
    restart: unless-stopped
    ports:
      - "6379:6379"
    volumes:
      - flexes_redis_data:/data
      - flexes_redis_logs:/var/log/redis
      - ./docker/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf
    command: redis-server /usr/local/etc/redis/redis.conf
    networks:
      - flexes-network
    healthcheck:
      test: ["CMD", "redis-cli", "-a", "123456", "ping"]
      timeout: 20s
      retries: 5

volumes:
  flexes_mysql_data:
    driver: local
  flexes_mysql_logs:
    driver: local
  flexes_redis_data:
    driver: local
  flexes_redis_logs:
    driver: local

networks:
  flexes-network:
    driver: bridge
```

### Docker Compose 启动命令
```bash
# 启动所有服务
docker-compose up -d

# 启动指定服务
docker-compose up -d mysql redis

# 查看服务状态
docker-compose ps

# 查看服务日志
docker-compose logs -f mysql
docker-compose logs -f redis

# 停止服务
docker-compose down

# 停止服务并删除数据卷（谨慎使用）
docker-compose down -v
```

---

## 🔧 容器管理命令

### MySQL 容器管理
```bash
# 启动容器
docker start flexes-mysql

# 停止容器
docker stop flexes-mysql

# 重启容器
docker restart flexes-mysql

# 查看容器日志
docker logs -f flexes-mysql

# 进入容器
docker exec -it flexes-mysql bash

# 连接 MySQL
docker exec -it flexes-mysql mysql -u flexes_user -pflexes_password flexes_platform

# 备份数据库
docker exec flexes-mysql mysqldump -u flexes_user -pflexes_password flexes_platform > backup.sql

# 恢复数据库
docker exec -i flexes-mysql mysql -u flexes_user -pflexes_password flexes_platform < backup.sql
```

### Redis 容器管理
```bash
# 启动容器
docker start flexes-redis

# 停止容器
docker stop flexes-redis

# 重启容器
docker restart flexes-redis

# 查看容器日志
docker logs -f flexes-redis

# 进入容器
docker exec -it flexes-redis sh

# 连接 Redis
docker exec -it flexes-redis redis-cli -a 123456

# 查看 Redis 信息
docker exec flexes-redis redis-cli -a 123456 info

# 备份 Redis 数据
docker exec flexes-redis redis-cli -a 123456 bgsave
```

---

## 🔍 连接测试

### MySQL 连接测试
```bash
# 使用 Docker 容器连接
docker exec -it flexes-mysql mysql -u flexes_user -pflexes_password -e "SELECT VERSION();"

# 使用本地客户端连接
mysql -h localhost -P 3306 -u flexes_user -pflexes_password flexes_platform

# 测试 SQL
mysql -h localhost -P 3306 -u flexes_user -pflexes_password flexes_platform -e "SHOW TABLES;"
```

### Redis 连接测试
```bash
# 使用 Docker 容器连接
docker exec -it flexes-redis redis-cli -a 123456 ping

# 使用本地客户端连接
redis-cli -h localhost -p 6379 -a 123456

# 测试命令
redis-cli -h localhost -p 6379 -a 123456 set test "Hello Flexes"
redis-cli -h localhost -p 6379 -a 123456 get test
```

---

## 🛠️ 故障排除

### 常见问题

#### MySQL 问题
1. **端口占用**: 检查 3306 端口是否被占用
   ```bash
   netstat -an | grep 3306
   lsof -i :3306
   ```

2. **权限问题**: 确保数据卷有正确的权限
   ```bash
   docker exec flexes-mysql ls -la /var/lib/mysql
   ```

3. **字符集问题**: 检查字符集配置
   ```bash
   docker exec -it flexes-mysql mysql -u flexes_user -pflexes_password -e "SHOW VARIABLES LIKE 'character_set%';"
   ```

#### Redis 问题
1. **端口占用**: 检查 6379 端口是否被占用
   ```bash
   netstat -an | grep 6379
   lsof -i :6379
   ```

2. **密码认证**: 确保使用正确的密码连接
   ```bash
   docker exec flexes-redis redis-cli -a 123456 auth 123456
   ```

3. **内存使用**: 检查 Redis 内存使用情况
   ```bash
   docker exec flexes-redis redis-cli -a 123456 info memory
   ```

### 清理命令
```bash
# 停止并删除容器
docker stop flexes-mysql flexes-redis
docker rm flexes-mysql flexes-redis

# 删除数据卷（谨慎使用）
docker volume rm flexes_mysql_data flexes_redis_data

# 删除网络
docker network rm flexes-network

# 清理未使用的资源
docker system prune -f
```

---

## 📝 注意事项

1. **数据持久化**: 生产环境务必配置数据卷持久化
2. **安全配置**: 生产环境需要修改默认密码和配置
3. **网络隔离**: 建议使用自定义网络隔离服务
4. **资源限制**: 根据实际需求配置内存和CPU限制
5. **备份策略**: 定期备份数据库和Redis数据
6. **监控告警**: 配置容器健康检查和监控

---

**文档版本**: v1.0  
**创建日期**: 2025-09-17  
**最后更新**: 2025-09-17  
**维护团队**: Flexes 开发团队
