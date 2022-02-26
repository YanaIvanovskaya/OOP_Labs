@echo off

set OUT = "out.txt"

javac "%__CD__%Lab1_Task1\code\Main.java"


::case 13 -> Search contains whitespaces
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/search_contains_whitespaces/input.txt" "%__CD__%Lab1_Task1/test/search_contains_whitespaces/output.txt" "and also" "REPLACED" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/search_contains_whitespaces/ideal_output.txt"  || goto err

::case 12 -> Many lines search
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/many_lines_search/output.txt" "If we wish to define the technology, in simple terms one may consider it as the knowledge and usage of tools, crafts, systems, methods and techniques of the organization to resolve the problem." "REPLACED" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/many_lines_search/ideal_output.txt" || goto err

::case 11 -> Search has breaks
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/search_has_breaks/output.txt" "tools, crafts" "REPLACED" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/search_has_breaks/ideal_output.txt" || goto err

::case 10 -> Search equals replace
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/correct/output.txt" "and" "and" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/search_equals_replace/ideal_output.txt" || goto err

::case 9 -> Search longer than replace
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/search_longer_than_replace/output.txt" "and" "." > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/search_longer_than_replace/ideal_output.txt"  || goto err

::case 8 -> Search hot found
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/search_not_found/output.txt" "apple" "orange" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/search_not_found/ideal_output.txt"  || goto err

::case 7 -> Replace longer than search
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/replace_longer_than_search/output.txt" "and" "this is long replace string here" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/replace_longer_than_search/ideal_output.txt"  || goto err

::case 6 -> Replace contains part of search
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/replace_contains_part_search/output.txt" "and" "and REPLACED and" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/replace_contains_part_search/ideal_output.txt"  || goto err

::case 5 -> Empty input file
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/empty_input/input.txt" "%__CD__%Lab1_Task1/test/empty_input/output.txt" "and" "REPLACED" > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/empty_input/input.txt"  || goto err

::case 4 -> Both files are incorrect
java Lab1_Task1.code.Main "C:\Users\yana-\some_input_file.txt" "C:\Users\yana-\some_output_file.txt" "search" "replace"> OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_input_and_output/output.txt" || goto err

::case 3 -> Output file is incorrect, input file is correct
java Lab1_Task1.code.Main "C:\Users\yana-\valid_file.txt" "C:\Users\yana-\some_file.txt" "search" "replace"> OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_input_or_output/output.txt" || goto err

::case 2 -> Input file is incorrect, output file is correct
java Lab1_Task1.code.Main "C:\Users\yana-\some_file.txt" "C:\Users\yana-\valid_file.txt" "search" "replace"> OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_input_or_output/output.txt" || goto err

::case 1 -> Incorrect arguments
java Lab1_Task1.code.Main > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_arguments/output.txt" || goto err
java Lab1_Task1.code.Main arg1 arg2 arg3 > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_arguments/output.txt" || goto err
java Lab1_Task1.code.Main arg1 arg2 arg3 arg4 arg5 > OUT || goto err
fc OUT "%__CD__%Lab1_Task1/test/incorrect_arguments/output.txt" || goto err

:: case 0 -> All correct
java Lab1_Task1.code.Main "%__CD__%Lab1_Task1/test/correct/input.txt" "%__CD__%Lab1_Task1/test/correct/output.txt" "and" "REPLACED" > OUT || goto err
fc OUT" "%__CD__%Lab1_Task1/test/correct/ideal_output.txt" || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1