#!/bin/bash

# Flexes Backend 启动脚本
# 作者: Flexes Team
# 版本: 1.0.0

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 打印带颜色的消息
print_message() {
    echo -e "${2}[$(date +'%Y-%m-%d %H:%M:%S')] $1${NC}"
}

print_info() {
    print_message "$1" "$BLUE"
}

print_success() {
    print_message "$1" "$GREEN"
}

print_warning() {
    print_message "$1" "$YELLOW"
}

print_error() {
    print_message "$1" "$RED"
}

# 检查Java环境
check_java() {
    print_info "检查Java环境..."
    if ! command -v java &> /dev/null; then
        print_error "Java未安装或未配置到PATH中"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    print_success "Java版本: $JAVA_VERSION"
}

# 检查Maven环境
check_maven() {
    print_info "检查Maven环境..."
    if ! command -v mvn &> /dev/null; then
        print_error "Maven未安装或未配置到PATH中"
        exit 1
    fi
    
    MVN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
    print_success "Maven版本: $MVN_VERSION"
}

# 检查数据库连接
check_database() {
    print_info "检查数据库连接..."
    
    DB_HOST=${DB_HOST:-localhost}
    DB_PORT=${DB_PORT:-3306}
    DB_NAME=${DB_NAME:-flexes_platform}
    DB_USER=${DB_USERNAME:-flexes_user}
    
    if command -v mysql &> /dev/null; then
        if mysql -h"$DB_HOST" -P"$DB_PORT" -u"$DB_USER" -p"${DB_PASSWORD}" -e "USE $DB_NAME;" 2>/dev/null; then
            print_success "数据库连接正常"
        else
            print_warning "数据库连接失败，请检查配置"
        fi
    else
        print_warning "MySQL客户端未安装，跳过数据库连接检查"
    fi
}

# 检查Redis连接
check_redis() {
    print_info "检查Redis连接..."
    
    REDIS_HOST=${REDIS_HOST:-localhost}
    REDIS_PORT=${REDIS_PORT:-6379}
    
    if command -v redis-cli &> /dev/null; then
        if redis-cli -h "$REDIS_HOST" -p "$REDIS_PORT" ping | grep -q "PONG"; then
            print_success "Redis连接正常"
        else
            print_warning "Redis连接失败，请检查配置"
        fi
    else
        print_warning "Redis客户端未安装，跳过Redis连接检查"
    fi
}

# 构建项目
build_project() {
    print_info "构建项目..."
    
    if [ "$SKIP_TESTS" = "true" ]; then
        mvn clean package -DskipTests
    else
        mvn clean package
    fi
    
    if [ $? -eq 0 ]; then
        print_success "项目构建成功"
    else
        print_error "项目构建失败"
        exit 1
    fi
}

# 启动应用
start_application() {
    print_info "启动Flexes Backend应用..."
    
    # 设置JVM参数
    JAVA_OPTS=${JAVA_OPTS:-"-Xms512m -Xmx1024m -XX:+UseG1GC"}
    
    # 设置Spring Profile
    SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}
    
    # 查找jar文件
    JAR_FILE=$(find target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" | head -n 1)
    
    if [ -z "$JAR_FILE" ]; then
        print_error "未找到jar文件，请先构建项目"
        exit 1
    fi
    
    print_info "使用jar文件: $JAR_FILE"
    print_info "JVM参数: $JAVA_OPTS"
    print_info "Spring Profile: $SPRING_PROFILES_ACTIVE"
    
    # 启动应用
    java $JAVA_OPTS -Dspring.profiles.active="$SPRING_PROFILES_ACTIVE" -jar "$JAR_FILE"
}

# 显示帮助信息
show_help() {
    echo "Flexes Backend 启动脚本"
    echo ""
    echo "用法: $0 [选项]"
    echo ""
    echo "选项:"
    echo "  -h, --help          显示帮助信息"
    echo "  -s, --skip-tests    跳过测试"
    echo "  -b, --build-only    仅构建，不启动"
    echo "  -c, --check-only    仅检查环境，不构建和启动"
    echo ""
    echo "环境变量:"
    echo "  SPRING_PROFILES_ACTIVE  Spring激活的配置文件 (默认: dev)"
    echo "  JAVA_OPTS              JVM参数 (默认: -Xms512m -Xmx1024m -XX:+UseG1GC)"
    echo "  DB_HOST                数据库主机 (默认: localhost)"
    echo "  DB_PORT                数据库端口 (默认: 3306)"
    echo "  DB_USERNAME            数据库用户名 (默认: flexes_user)"
    echo "  DB_PASSWORD            数据库密码"
    echo "  REDIS_HOST             Redis主机 (默认: localhost)"
    echo "  REDIS_PORT             Redis端口 (默认: 6379)"
    echo ""
}

# 主函数
main() {
    print_info "=== Flexes Backend 启动脚本 ==="
    
    # 解析命令行参数
    SKIP_TESTS=false
    BUILD_ONLY=false
    CHECK_ONLY=false
    
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                show_help
                exit 0
                ;;
            -s|--skip-tests)
                SKIP_TESTS=true
                shift
                ;;
            -b|--build-only)
                BUILD_ONLY=true
                shift
                ;;
            -c|--check-only)
                CHECK_ONLY=true
                shift
                ;;
            *)
                print_error "未知参数: $1"
                show_help
                exit 1
                ;;
        esac
    done
    
    # 检查环境
    check_java
    check_maven
    check_database
    check_redis
    
    if [ "$CHECK_ONLY" = "true" ]; then
        print_success "环境检查完成"
        exit 0
    fi
    
    # 构建项目
    build_project
    
    if [ "$BUILD_ONLY" = "true" ]; then
        print_success "项目构建完成"
        exit 0
    fi
    
    # 启动应用
    start_application
}

# 执行主函数
main "$@"
