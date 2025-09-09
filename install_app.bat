@echo off
echo ===================================================
echo Install Android App to Phone
echo ===================================================
echo.

echo Step 1: Checking connected devices...
adb devices
echo.

echo Step 2: Please make sure your phone is connected and USB debugging is enabled.
echo.

echo Step 3: Manual installation steps:
echo 1. Open this project in Android Studio
echo 2. Click "Build" -^> "Build Bundle(s) / APK(s)" -^> "Build APK(s)"
echo 3. Wait for the build to complete
echo 4. Click the "locate" link to find the APK file
echo 5. Use the following command to install the APK:
echo    adb install -r [APK file path]
echo.

echo Step 4: Checking for pre-built APK...
set APK_PATH=app\build\outputs\apk\debug\app-debug.apk
if exist %APK_PATH% (
    echo Found pre-built APK, installing...
    adb install -r %APK_PATH%
    if %ERRORLEVEL% NEQ 0 (
        echo Installation failed! Please check the error message.
    ) else (
        echo Installation successful!
        echo Launching the app...
        adb shell am start -n com.example.myapp/.MainActivity
    )
) else (
    echo Pre-built APK file not found.
    echo Please build the project using Android Studio first.
)

echo.
echo Press any key to exit...
pause > nul