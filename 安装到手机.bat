@echo off
echo ===================================================
echo 安装Android应用到手机的步骤
echo ===================================================
echo.
echo 1. 请确保您已安装Android Studio
echo 2. 使用Android Studio打开此项目文件夹
echo 3. 等待Gradle同步完成
echo 4. 将您的手机通过USB连接到电脑，并确保已启用USB调试
echo 5. 在Android Studio中点击"运行"按钮(绿色三角形)
echo 6. 在弹出的设备选择器中选择您的手机
echo 7. 等待应用安装并在手机上启动
echo.
echo 如果您已安装Android SDK并配置了环境变量，也可以尝试以下命令行方式：
echo.
echo 1. 在命令行中导航到项目根目录
echo 2. 执行: gradlew assembleDebug
echo 3. 执行: adb install -r app\build\outputs\apk\debug\app-debug.apk
echo.
echo 按任意键退出...
pause > nul