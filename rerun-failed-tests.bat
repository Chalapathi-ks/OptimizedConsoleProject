@echo off
REM Script to rerun failed TestNG tests on Windows
REM Usage: rerun-failed-tests.bat [profile] [hubUrl]
REM Example: rerun-failed-tests.bat ProdUK http://selenium-hub.netcorein.com:4444/wd/hub

set PROFILE=%~1
if "%PROFILE%"=="" set PROFILE=ProdUK

set HUB_URL=%~2
if "%HUB_URL%"=="" set HUB_URL=http://selenium-hub.netcorein.com:4444/wd/hub

set FAILED_XML=target\surefire-reports\testng-failed.xml

echo ==========================================
echo Rerunning Failed Tests
echo ==========================================
echo Profile: %PROFILE%
echo Selenium Grid URL: %HUB_URL%
echo Failed Tests XML: %FAILED_XML%
echo ==========================================
echo.

REM Check if failed tests XML exists
if not exist "%FAILED_XML%" (
    echo [ERROR] Failed tests XML not found at %FAILED_XML%
    echo         Please run the test suite first to generate failed tests.
    exit /b 1
)

echo [INFO] Found failed tests. Rerunning...
echo.

REM Run Maven with the failed tests XML
call mvn test -P"%PROFILE%" -Denv.profile="%PROFILE%" -DhubUrl="%HUB_URL%" -DsuiteXmlFile="%FAILED_XML%"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] All failed tests passed on rerun!
) else (
    echo.
    echo [WARNING] Some tests still failed. Check the reports for details.
)

exit /b %ERRORLEVEL%

