@echo off

set OUT = "out.txt"

javac "%__CD__%Lab1_Task2\code\Bin2Dec.java"

::case 2 -> All correct
java Lab1_Task2.code.Bin2Dec "11111101"> OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/correct/output.txt"  || goto err

::case 1 -> Incorrect number
java Lab1_Task2.code.Bin2Dec "010100001101011100101000101010010"> OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/incorrect_number/output.txt"  || goto err
java Lab1_Task2.code.Bin2Dec "0341"> OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/incorrect_number/output.txt"  || goto err
java Lab1_Task2.code.Bin2Dec "0b101"> OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/incorrect_number/output.txt"  || goto err

::case 0 -> Incorrect argument
java Lab1_Task2.code.Bin2Dec > OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/incorrect_argument/output.txt"  || goto err
java Lab1_Task2.code.Bin2Dec "0110" "110" > OUT || goto err
fc OUT "%__CD__%Lab1_Task2/test/incorrect_argument/output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1


