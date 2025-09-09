@echo off
echo ===================================================
echo 正在构建并安装Android应用到手机
echo ===================================================
echo.

echo 步骤1: 构建Debug APK...
call gradlew assembleDebug
if %ERRORLEVEL% NEQ 0 (
    echo 构建失败！请检查错误信息。
    goto :end
)
echo 构建成功！

echo.
echo 步骤2: 检查连接的设备...
adb devices
echo.

echo 步骤3: 安装APK到设备...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %ERRORLEVEL% NEQ 0 (
    echo 安装失败！请确保手机已连接并启用USB调试。
    goto :end
)
echo 安装成功！

echo.
echo 步骤4: 启动应用...
adb shell am start -n com.example.myapp/.MainActivity
echo.

echo 完成！应用已安装并启动在您的设备上。

:end
echo.
echo 按任意键退出...
pause > nul