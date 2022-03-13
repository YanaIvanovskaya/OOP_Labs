@echo off

set OUT="%TEMP%\out.txt"

set TEST_DIR=%~dp0Task2\test

javac "%__CD__%Task2\code\Main.java"

::case 5 -> Part of symbol
java Task2.code.Main "&quot" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\part_of_symbol\output.txt"  || goto err

::case 4 -> Symbols in any positions
java Task2.code.Main "&Cat &lt;says&gt; ;&&quot;Meow&quot;. M&amp;M&;apos;s&" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\symbols_in_any_positions\output.txt"  || goto err

::case 3 -> Only symbols in string
java Task2.code.Main "&lt;&gt;&quot;&quot;&amp;&apos;" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\text_contains_symbols_only\output.txt"  || goto err

::case 2 -> No symbols found
java Task2.code.Main "Cat says meow-meow" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\no_symbols_found\output.txt"  || goto err

::case 1 -> All correct
java Task2.code.Main "Cat &lt;says&gt; &quot;Meow&quot;. M&amp;M&apos;s" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\correct\output.txt"  || goto err

::case 0 -> Incorrect arguments
java Task2.code.Main >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_argument\output.txt"  || goto err
java Task2.code.Main "string1" "string2" >%OUT% || goto err
fc %OUT% "%TEST_DIR%\incorrect_argument\output.txt"  || goto err


echo ALL TESTS PASSED
exit /B 0

:err
echo TEST FAILED
exit /B 1