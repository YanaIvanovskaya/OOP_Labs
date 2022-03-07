@echo off

set OUT = "out.txt"

javac "%__CD__%Task4\code\Main.java"

::case 5 -> No found
java Task4.code.Main "%__CD__%Task4/test/no_found/input.txt" > OUT || goto err
fc OUT "%__CD__%Task4/test/no_found/output.txt"  || goto err

::case 4 -> Different case
java Task4.code.Main "%__CD__%Task4/test/correct/input.txt" > OUT || goto err
fc OUT "%__CD__%Task4/test/different_case/output.txt"  || goto err

::case 3 -> Empty file
java Task4.code.Main "%__CD__%Task4/test/empty_file/input.txt" > OUT || goto err
fc OUT "%__CD__%Task4/test/empty_file/output.txt"  || goto err

::case 2 -> Incorrect file
java Task4.code.Main "incorrect_file/some_file.txt" > OUT || goto err
fc OUT "%__CD__%Task4/test/incorrect_file/output.txt"  || goto err

::case 1 -> Incorrect arguments
java Task4.code.Main "arg1" "arg1" > OUT || goto err
fc OUT "%__CD__%Task4/test/incorrect_arguments/output.txt"  || goto err
java Task4.code.Main > OUT || goto err
fc OUT "%__CD__%Task4/test/incorrect_arguments/output.txt"  || goto err

::case 0 -> All correct
java Task4.code.Main "%__CD__%Task4/test/correct/input.txt" > OUT || goto err
fc OUT "%__CD__%Task4/test/correct/output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1