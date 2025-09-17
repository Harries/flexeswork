@echo off
REM Flexes Backend 启动脚本 (Windows)
REM 作者: Flexes Team
REM 版本: 1.0.0

setlocal enabledelayedexpansion

REM 设置默认值
set SKIP_TESTS=false
set BUILD_ONLY=false
set CHECK_ONLY=false
set SPRING_PROFILES_ACTIVE=dev
set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC

echo [%date% %time%] === Flexes Backend 启动脚本 ===

REM 解析命令行参数
:parse_args
if "%~1"=="" goto check_env
if "%~1"=="-h" goto show_help
if "%~1"=="--help" goto show_help
if "%~1"=="-s" set SKIP_TESTS=true
if "%~1"=="--skip-tests" set SKIP_TESTS=true
if "%~1"=="-b" set BUILD_ONLY=true
if "%~1"=="--build-only" set BUILD_ONLY=true
if "%~1"=="-c" set CHECK_ONLY=true
if "%~1"=="--check-only" set CHECK_ONLY=true
shift
goto parse_args

:show_help
echo Flexes Backend 启动脚本
echo.
echo 用法: %0 [选项]
echo.
echo 选项:
echo   -h, --help          显示帮助信息
echo   -s, --skip-tests    跳过测试
echo   -b, --build-only    仅构建，不启动
echo   -c, --check-only    仅检查环境，不构建和启动
echo.
echo 环境变量:
echo   SPRING_PROFILES_ACTIVE  Spring激活的配置文件 (默认: dev)
echo   JAVA_OPTS              JVM参数
echo   DB_HOST                数据库主机 (默认: localhost)
echo   DB_PORT                数据库端口 (默认: 3306)
echo   DB_USERNAME            数据库用户名 (默认: flexes_user)
echo   DB_PASSWORD            数据库密码
echo   REDIS_HOST             Redis主机 (默认: localhost)
echo   REDIS_PORT             Redis端口 (默认: 6379)
echo.
goto end

:check_env
echo [%date% %time%] 检查Java环境...
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java未安装或未配置到PATH中
    goto error_exit
)
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    set JAVA_VERSION=!JAVA_VERSION:"=!
)
echo [%date% %time%] Java版本: !JAVA_VERSION!

echo [%date% %time%] 检查Maven环境...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven未安装或未配置到PATH中
    goto error_exit
)
for /f "tokens=3" %%g in ('mvn -version 2^>^&1 ^| findstr /i "Apache Maven"') do (
    set MVN_VERSION=%%g
)
echo [%date% %time%] Maven版本: !MVN_VERSION!

echo [%date% %time%] 检查数据库连接...
REM 这里可以添加数据库连接检查逻辑
echo [%date% %time%] 数据库连接检查跳过 (Windows环境)

echo [%date% %time%] 检查Redis连接...
REM 这里可以添加Redis连接检查逻辑
echo [%date% %time%] Redis连接检查跳过 (Windows环境)

if "%CHECK_ONLY%"=="true" (
    echo [%date% %time%] 环境检查完成
    goto end
)

:build_project
echo [%date% %time%] 构建项目...
if "%SKIP_TESTS%"=="true" (
    mvn clean package -DskipTests
) else (
    mvn clean package
)

if errorlevel 1 (
    echo [ERROR] 项目构建失败
    goto error_exit
)
echo [%date% %time%] 项目构建成功

if "%BUILD_ONLY%"=="true" (
    echo [%date% %time%] 项目构建完成
    goto end
)

:start_application
echo [%date% %time%] 启动Flexes Backend应用...

REM 查找jar文件
for /f %%i in ('dir /b target\*.jar ^| findstr /v sources ^| findstr /v javadoc') do (
    set JAR_FILE=target\%%i
    goto found_jar
)

echo [ERROR] 未找到jar文件，请先构建项目
goto error_exit

:found_jar
echo [%date% %time%] 使用jar文件: !JAR_FILE!
echo [%date% %time%] JVM参数: %JAVA_OPTS%
echo [%date% %time%] Spring Profile: %SPRING_PROFILES_ACTIVE%

REM 启动应用
java %JAVA_OPTS% -Dspring.profiles.active=%SPRING_PROFILES_ACTIVE% -jar "!JAR_FILE!"

if errorlevel 1 (
    echo [ERROR] 应用启动失败
    goto error_exit
)

goto end

:error_exit
echo [ERROR] 脚本执行失败
exit /b 1

:end
echo [%date% %time%] 脚本执行完成
endlocal
