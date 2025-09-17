#!/bin/bash

echo "========================================"
echo "Flexes Frontend 启动脚本"
echo "========================================"

# 检查 Node.js 版本
echo "检查 Node.js 版本..."
if ! command -v node &> /dev/null; then
    echo "错误: 请先安装 Node.js 16+ 版本"
    exit 1
fi

NODE_VERSION=$(node --version)
echo "Node.js 版本: $NODE_VERSION"

# 检查 npm 版本
echo "检查 npm 版本..."
if ! command -v npm &> /dev/null; then
    echo "错误: npm 未正确安装"
    exit 1
fi

NPM_VERSION=$(npm --version)
echo "npm 版本: $NPM_VERSION"

# 检查依赖是否已安装
if [ ! -d "node_modules" ]; then
    echo "正在安装依赖..."
    npm install
    if [ $? -ne 0 ]; then
        echo "错误: 依赖安装失败"
        exit 1
    fi
else
    echo "依赖已存在，跳过安装"
fi

echo "启动开发服务器..."
echo "应用将在 http://localhost:3000 启动"
echo "按 Ctrl+C 停止服务器"

npm start
