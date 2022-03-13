@echo off

set OUT="%TEMP%\out.txt"

set TEST_DIR=%~dp0Task3\test

javac "%__CD__%Task3\code\Main.java"

::case 0 -> All correct
java Task3.code.Main "%TEST_DIR%\correct\input.txt" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\correct\output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1