@echo off
echo ========================================
echo Flexes Frontend 启动脚本
echo ========================================

echo 检查 Node.js 版本...
node --version
if %errorlevel% neq 0 (
    echo 错误: 请先安装 Node.js 16+ 版本
    pause
    exit /b 1
)

echo 检查 npm 版本...
npm --version
if %errorlevel% neq 0 (
    echo 错误: npm 未正确安装
    pause
    exit /b 1
)

echo 检查依赖是否已安装...
if not exist "node_modules" (
    echo 正在安装依赖...
    npm install
    if %errorlevel% neq 0 (
        echo 错误: 依赖安装失败
        pause
        exit /b 1
    )
) else (
    echo 依赖已存在，跳过安装
)

echo 启动开发服务器...
echo 应用将在 http://localhost:3000 启动
echo 按 Ctrl+C 停止服务器
npm start

pause
