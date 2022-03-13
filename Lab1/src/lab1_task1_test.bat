@echo off

set OUT="%TEMP%\out.txt"

SET TEST_DIR=%~dp0Lab1_Task1\test

javac "%__CD__%Lab1_Task1\code\Main.java"

::case 10 -> Search contains whitespaces
java Lab1_Task1.code.Main "%TEST_DIR%\search_contains_whitespaces\input.txt" "%TEST_DIR%\search_contains_whitespaces\output.txt" "and also" "REPLACED" || goto err
fc "%TEST_DIR%\search_contains_whitespaces\output.txt" "%TEST_DIR%\search_contains_whitespaces\ideal_output.txt"  || goto err

::case 9 -> Search longer than replace
java Lab1_Task1.code.Main "%TEST_DIR%\correct\input.txt" "%TEST_DIR%\search_longer_than_replace\output.txt" "and" "." || goto err
fc "%TEST_DIR%\search_longer_than_replace\output.txt" "%TEST_DIR%\search_longer_than_replace\ideal_output.txt"  || goto err

::case 8 -> Search hot found
java Lab1_Task1.code.Main "%TEST_DIR%\correct\input.txt" "%TEST_DIR%\search_not_found\output.txt" "apple" "orange" || goto err
fc "%TEST_DIR%\search_not_found\output.txt" "%TEST_DIR%\search_not_found\ideal_output.txt"  || goto err

::case 7 -> Replace longer than search
java Lab1_Task1.code.Main "%TEST_DIR%\correct\input.txt" "%TEST_DIR%\replace_longer_than_search\output.txt" "and" "this is long replace string here" || goto err
fc "%TEST_DIR%\replace_longer_than_search\output.txt" "%TEST_DIR%\replace_longer_than_search\ideal_output.txt"  || goto err

::case 6 -> Replace contains part of search
java Lab1_Task1.code.Main "%TEST_DIR%\correct\input.txt" "%TEST_DIR%\replace_contains_part_search\output.txt" "and" "and REPLACED and" || goto err
fc "%TEST_DIR%\replace_contains_part_search\output.txt" "%TEST_DIR%\replace_contains_part_search\ideal_output.txt"  || goto err

::case 5 -> Empty input file
java Lab1_Task1.code.Main "%TEST_DIR%\empty_input\input.txt" "%TEST_DIR%\empty_input\output.txt" "and" "REPLACED" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\empty_input\input.txt"  || goto err

::case 3 -> Output file is incorrect, input file is correct
java Lab1_Task1.code.Main "C:\Users\yana-\valid_file.txt" "C:\Users\yana-\some_file.txt" "search" "replace" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_input_or_output\output.txt" || goto err

::case 2 -> Input file is incorrect, output file is correct
java Lab1_Task1.code.Main "C:\Users\yana-\some_file.txt" "C:\Users\yana-\valid_file.txt" "search" "replace" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_input_or_output\output.txt" || goto err

::case 1 -> Incorrect arguments
java Lab1_Task1.code.Main >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_arguments\output.txt" || goto err
java Lab1_Task1.code.Main arg1 arg2 arg3 >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_arguments\output.txt" || goto err
java Lab1_Task1.code.Main arg1 arg2 arg3 arg4 arg5 >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_arguments\output.txt" || goto err

:: case 0 -> All correct
java Lab1_Task1.code.Main "%TEST_DIR%\correct\input.txt" "%TEST_DIR%\correct\output.txt" "and" "REPLACED" || goto err
fc "%TEST_DIR%\correct\output.txt" "%TEST_DIR%\correct\ideal_output.txt" || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1