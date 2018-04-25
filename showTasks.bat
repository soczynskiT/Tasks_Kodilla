call runcrud
if "%ERRORLEVEL%" == "0" goto openWeb
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:openWeb
call explorer "http://localhost:8080/crud/v1/tasks"
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Request finished.
