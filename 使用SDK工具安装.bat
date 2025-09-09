@echo off
echo ===================================================
echo 使用Android SDK工具构建并安装应用
echo ===================================================
echo.

echo 检查Android SDK环境...
where aapt
if %ERRORLEVEL% NEQ 0 (
    echo 未找到Android SDK构建工具(aapt)！
    echo 请确保Android SDK已安装并添加到PATH环境变量。
    goto :end
)

echo 检查ADB...
adb devices
echo.

echo 请确保您的手机已连接并启用USB调试。
echo.
echo 以下是手动安装APK的步骤：
echo 1. 使用Android Studio打开此项目
echo 2. 点击"Build" -^> "Build Bundle(s) / APK(s)" -^> "Build APK(s)"
echo 3. 等待构建完成
echo 4. 点击"locate"链接找到APK文件
echo 5. 使用以下命令安装APK：
echo    adb install -r [APK文件路径]
echo.
echo 或者，您可以使用以下命令直接安装预构建的APK（如果有）：
echo.

set APK_PATH=app\build\outputs\apk\debug\app-debug.apk
if exist %APK_PATH% (
    echo 找到预构建的APK，正在安装...
    adb install -r %APK_PATH%
    if %ERRORLEVEL% NEQ 0 (
        echo 安装失败！请检查错误信息。
    ) else (
        echo 安装成功！
        echo 启动应用...
        adb shell am start -n com.example.myapp/.MainActivity
    )
) else (
    echo 未找到预构建的APK文件。
    echo 请先使用Android Studio构建项目。
)

:end
echo.
echo 按任意键退出...
pause > nul