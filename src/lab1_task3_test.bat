@echo off

set ROOT_PATH = "C:\Users\yana-\All_Projects_Intellij_Idea\OOP_Labs\src\Lab1_Task3"

set TEST_PATH = "%__CD__%test"
set OUT_PATH = "%TEMP%\out.txt"


javac "%__CD__%Lab1_Task3\Main.java"
java Lab1_Task3.Main "%__CD__%Lab1_Task3/test/correct/correct_input.txt" > "out.txt"
::"%__CD__%Lab1_Task3/test/correct/correct_input.txt"
::java  Lab1_Task3.Main < "%TEST_PATH%\correct\correct_input.txt"
::call java %TASK3_PATH% "qwerty.txt" < correct/correct_input.txt > "%TEMP%\out.txt" || goto err
::fc "%TEMP%\out.txt" correct/correct_output.txt || goto err


echo Test passed
exit /B 0

:err
echo Test failed
exit /B 1
