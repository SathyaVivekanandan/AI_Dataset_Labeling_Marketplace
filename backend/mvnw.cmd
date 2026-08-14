@echo off
setlocal
set "MAVEN_VERSION=3.9.11"
set "MAVEN_HOME=%USERPROFILE%\.m2\wrapper\apache-maven-%MAVEN_VERSION%"
set "MAVEN_BIN=%MAVEN_HOME%\bin\mvn.cmd"
if exist "%MAVEN_BIN%" goto RUN
set "BASE=%USERPROFILE%\.m2\wrapper"
set "ZIP=%BASE%\apache-maven-%MAVEN_VERSION%-bin.zip"
if not exist "%BASE%" mkdir "%BASE%"
echo Maven %MAVEN_VERSION% not found. Downloading Maven...
powershell -NoProfile -ExecutionPolicy Bypass -Command "$ProgressPreference='SilentlyContinue'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%ZIP%'"
if errorlevel 1 (echo Failed to download Maven. Check your internet connection.& exit /b 1)
powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Path '%ZIP%' -DestinationPath '%BASE%' -Force"
if errorlevel 1 (echo Failed to extract Maven.& exit /b 1)
:RUN
call "%MAVEN_BIN%" %*
exit /b %ERRORLEVEL%
