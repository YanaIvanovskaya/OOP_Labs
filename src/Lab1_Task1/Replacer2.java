package Lab1_Task1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Replacer2 {

    private final Arguments args;
    private final StringBuffer lineBuffer;

    Replacer2(Arguments args) {
        this.args = args;
        this.lineBuffer = new StringBuffer();
    }

    public static void main(String[] args) {
        Replacer2.Arguments arguments = getArgumentsOrNull(args);
        if (arguments == null) {
            System.out.println("Must be 4 arguments <input file> <output file> <search> <replace>");
            return;
        }
        Replacer2 replacer2 = new Replacer2(arguments);
        replacer2.replace();
    }

    private static Replacer2.Arguments getArgumentsOrNull(String[] args) {
        if (args.length < 4) {
            return null;
        } else {
            return new Replacer2.Arguments(
                    args[0],
                    args[1],
                    args[2],
                    args[3]
            );
        }
    }

    private FileWriter createFileWriter() {
        String output = args.outputPath;
        try {
            return new FileWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Cannot create FileWriter for file " + output);
            return null;
        }
    }

    private void replace() {
        if (!(isValidFilePath(args.inputPath) && isValidFilePath(args.outputPath))) {
            System.out.println("Incorrect file paths");
            return;
        }
        String inputPath = "C:\\Users\\yana-\\Desktop\\input.txt";

        String outputPath = "C:\\Users\\yana-\\Desktop\\output.txt";

        FileWriter writer = createFileWriter();

        if (writer == null) {
            return;
        }

        readAndReplace(new File(args.inputPath), writer);
    }

    private String prepareLine(String newLine) {
        String replacedLine;

        String searchStr = args.searchStr;
        String replaceStr = args.replaceStr;
        String preparedLine = "";

        replacedLine = newLine.replaceAll(searchStr, replaceStr);
        boolean isNewNotContain = containsNoSearchPart(newLine, searchStr);
        boolean isReplacedNotContain = containsNoSearchPart(replacedLine, searchStr);

        if (isNewNotContain || isReplacedNotContain) {
            preparedLine = lineBuffer.toString();
            lineBuffer.setLength(0);

            if (isNewNotContain) {
                preparedLine += newLine + "\n";
            } else {
                preparedLine += replacedLine + "\n";
            }
            return preparedLine;
        }

        replacedLine = lineBuffer
                .append(" ")
                .append(newLine)
                .toString()
                .replaceAll(searchStr, replaceStr)
                .trim();
        lineBuffer.setLength(0);
        lineBuffer.append(replacedLine);

        return preparedLine;
    }

    private void readAndReplace(File file, FileWriter writer) {
        Scanner scanner = null;
        String newLine;
        String preparedLine;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();
                preparedLine = prepareLine(newLine);
                writer.write(preparedLine);
            }
            if (!lineBuffer.isEmpty()) {
                writer.write(lineBuffer.toString());
            }
            scanner.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            assert scanner != null;
            scanner.close();
        }
    }

    private static boolean containsNoSearchPart(String text, String search) {
        boolean isContain = false;
        for (String part : search.split(" ")) {
            if (text.contains(part)) {
                isContain = true;
                break;
            }
        }
        return !isContain;
    }

    private static boolean isValidFilePath(String filepath) {
        File file = new File(filepath);
        return file.exists() && file.canRead() && file.canWrite();
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
