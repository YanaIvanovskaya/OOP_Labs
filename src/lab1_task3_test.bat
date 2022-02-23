@echo off

set OUT = "out.txt"

javac "%__CD__%Lab1_Task3\code\Main.java"

:: case 1 -> All correct
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/correct/correct_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/correct/correct_output.txt" || goto err


:: case 2 -> All correct but matrix can not be inverted
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/can_not_invert/can_not_invert_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/can_not_invert/can_not_invert_output.txt" || goto err


:: case 3 -> Incorrect count of arguments
java Lab1_Task3.code.Main > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/incorrect_arguments/incorrect_arguments_output.txt" || goto err

java Lab1_Task3.code.Main arg1 arg2 arg3 > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/incorrect_arguments/incorrect_arguments_output.txt" || goto err


:: case 4 -> Input file is empty
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/empty_file/empty_file_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/empty_file/empty_file_output.txt" || goto err


:: case 5 -> Matrix in file have incorrect row count
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/incorrect_row_count/incorrect_row_count_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/incorrect_row_count/incorrect_row_count_output.txt" || goto err


:: case 6 -> Matrix in file have incorrect column count
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/incorrect_column_count/incorrect_column_count_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/incorrect_column_count/incorrect_column_count_output.txt" || goto err


:: case 7 -> Matrix in file contains invalid symbols
java Lab1_Task3.code.Main "%__CD__%Lab1_Task3/test/incorrect_symbols/incorrect_symbols_input.txt" > OUT || goto err
fc OUT "%__CD__%Lab1_Task3/test/incorrect_symbols/incorrect_symbols_output.txt" || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1
