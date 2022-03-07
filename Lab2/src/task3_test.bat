@echo off

set OUT = "out.txt"

javac "%__CD__%Task3\code\Main.java"

::case 0 -> All correct
java Task3.code.Main "%__CD__%Task3/test/correct/input.txt" > OUT || goto err
fc OUT "%__CD__%Task3/test/correct/output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1