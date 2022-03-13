@echo off

set OUT="%TEMP%\out.txt"

set TEST_DIR=%~dp0Task1\test

javac "%__CD__%Task1\code\Main.java"

::case 1 -> All correct
java Task1.code.Main 9 -0.05 3.5 0.2 0 >%OUT% || goto err
fc %OUT% "%TEST_DIR%\correct\output.txt"  || goto err

::case 0 -> Incorrect arguments
java Task1.code.Main >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_argument\output.txt"  || goto err
java Task1.code.Main "" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_argument\output.txt"  || goto err
java Task1.code.Main "1 4f 44 34.7 5.8" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_argument\output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1