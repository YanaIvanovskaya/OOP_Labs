package Lab1_Task1.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Replacer replacer = new Replacer(args);
        replacer.replace();
    }

}

class Replacer {
    private Arguments args;
    private FileWriter writer = null;
    private Scanner scanner = null;
    private final StringBuffer lineBuffer = new StringBuffer();

    Replacer(String[] args) {
        this.args = getArgumentsOrNull(args);
    }

    private static final String ERROR_ARGUMENTS =
            "4 arguments expected: <input file> <output file> <search> <replace>";
    private static final String ERROR_SEARCH_AND_REPLACE_EQUALS =
            "The replacement string is the same as the search string";

    void replace() {
        if (args == null) {
            System.out.println(ERROR_ARGUMENTS);
            return;
        }
        if (args.searchStr.equals(args.replaceStr)) {
            System.out.println(ERROR_SEARCH_AND_REPLACE_EQUALS);
            return;
        }

        if (isValidFile(new File(args.inputPath)))
            scanner = createScanner();
        else
            System.out.println(getFilePathError(args.inputPath));

        if (isValidFile(new File(args.outputPath)))
            writer = createFileWriter();
        else
            System.out.println(getFilePathError(args.outputPath));

        if (writer == null || scanner == null) {
            return;
        }

        readAndReplace();
    }

    private void readAndReplace() {
        String newLine;
        String preparedLine;

        try {
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();
                preparedLine = prepareLine(newLine);
                write(preparedLine);
            }
            if (!lineBuffer.isEmpty()) {
                write(lineBuffer
                        .toString()
                        .replaceAll(args.searchStr, args.replaceStr)
                );
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    private String prepareLine(String newLine) {
        String searchStr = args.searchStr;
        String replaceStr = args.replaceStr;

        String result = "";
        String replacedLine = newLine.replaceAll(searchStr, replaceStr);

        boolean isReplacedNotContain = !hasSearchPartInEndOfLine(replacedLine, searchStr);

        lineBuffer.append(" ").append(newLine);

        if (isReplacedNotContain) {
            result = lineBuffer
                    .toString()
                    .replaceAll(searchStr, replaceStr);
            lineBuffer.setLength(0);
        }

        return result;
    }

    private boolean hasSearchPartInEndOfLine(String text, String search) {
        boolean isContain = false;
        String[] searchWords = search.split(" ");

        if (searchWords.length == 1) return false;

        for (String word : searchWords) {
            boolean foundInEndOfLine =
                    (text.indexOf(word) + word.length()) == text.length();
            if (foundInEndOfLine) {
                isContain = true;
                break;
            }
        }

        return isContain;
    }

    private void write(String line) throws IOException {
        if (line.isEmpty()) return;

        try {
            writer.write(line.trim() + "\n");
            System.out.print(line.trim() + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
            writer.close();
        }
    }

    private String getFilePathError(String path) {
        return "File " + path + " not found or not available";
    }

    private Arguments getArgumentsOrNull(String[] args) {
        if (args.length != 4) {
            return null;
        } else {
            return new Arguments(
                    args[0],
                    args[1],
                    args[2],
                    args[3]
            );
        }
    }

    private FileWriter createFileWriter() {
        File output = new File(args.outputPath);
        try {
            return new FileWriter(output, false);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Scanner createScanner() {
        File input = new File(args.inputPath);
        try {
            return new Scanner(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private boolean isValidFile(File file) {
        return file.exists() && file.isFile() && file.canRead() && file.canWrite();
    }

    private static class Arguments {

        public String inputPath;
        public String outputPath;
        public String searchStr;
        public String replaceStr;

        Arguments(
                String inputPath,
                String outputPath,
                String searchStr,
                String replaceStr
        ) {
            this.inputPath = inputPath;
            this.outputPath = outputPath;
            this.searchStr = searchStr;
            this.replaceStr = replaceStr;
        }

    }

}


