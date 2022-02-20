@echo off

set TASK3_PATH="C:\Users\yana-\All_Projects_Intellij_Idea\OOP_Labs\src\Lab1_Task3\Main.java"
set OUT = "%TEMP%\out.txt"

% java C:\Users\yana-\All_Projects_Intellij_Idea\OOP_Labs\src\Lab1_Task3\Main.java < "correct/correct_input.txt"
::call java %TASK3_PATH% "qwerty.txt" < correct/correct_input.txt > "%TEMP%\out.txt" || goto err
::fc "%TEMP%\out.txt" correct/correct_output.txt || goto err

call java -classpath . %TASK3_PATH%
echo Test passed
exit /B 0

:err
echo Test failed
exit /B 1
