@echo off
setlocal

set IMAGE=leechanmi/short-kki-server:latest-dev

echo === Building JAR ===
call gradlew.bat clean bootJar -x test
if %errorlevel% neq 0 (
    echo JAR build failed
    exit /b 1
)

echo === Building and pushing Docker image (arm64) ===
docker buildx build --platform linux/arm64 --push -t %IMAGE% .
if %errorlevel% neq 0 (
    echo Docker build/push failed
    exit /b 1
)

echo === Done: %IMAGE% ===
