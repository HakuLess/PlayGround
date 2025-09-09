@echo off
echo ===================================================
echo Building and Installing Android App
echo ===================================================
echo.

echo Step 1: Building Debug APK with Gradle 8.13...
call gradlew assembleDebug
if %ERRORLEVEL% NEQ 0 (
    echo Build failed! Please check the error message.
    goto :end
)
echo Build successful!

echo.
echo Step 2: Checking connected devices...
adb devices
echo.

echo Step 3: Installing APK to device...
adb install -r app\build\outputs\apk\debug\app-debug.apk
if %ERRORLEVEL% NEQ 0 (
    echo Installation failed! Please make sure your phone is connected and USB debugging is enabled.
    goto :end
)
echo Installation successful!

echo.
echo Step 4: Launching the app...
adb shell am start -n com.example.myapp/.MainActivity
echo.

echo Done! The app has been installed and launched on your device.

:end
echo.
echo Press any key to exit...
pause > nul