package Lab1_Task1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Arguments {

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

class Replacer {
    private Arguments args;
    private FileWriter writer = null;
    private Scanner scanner = null;
    private final StringBuffer lineBuffer = new StringBuffer();

    Replacer(String[] args) {
        this.args = getArgumentsOrNull(args);
    }

    void replace() {
        if (args == null) {
            System.out.println("Must be 4 arguments <input file> <output file> <search> <replace>");
            return;
        }
        if (!(isValidFilePath(args.inputPath) && isValidFilePath(args.outputPath))) {
            System.out.println("Incorrect file paths");
            return;
        }

        writer = createFileWriter();
        scanner = createScanner();

        if (writer == null) {
            return;
        }

        readAndReplace();
    }

    private Arguments getArgumentsOrNull(String[] args) {
        if (args.length < 4) {
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
        String output = args.outputPath;
        try {
            return new FileWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Cannot create FileWriter for file " + output);
            return null;
        }
    }

    private Scanner createScanner() {
        return new Scanner(args.inputPath);
    }

    private boolean isValidFilePath(String filepath) {
        File file = new File(filepath);
        return file.exists() && file.canRead() && file.canWrite();
    }

    private boolean containsNoSearchPart(String text, String search) {
        boolean isContain = false;
        for (String part : search.split(" ")) {
            if (text.contains(part)) {
                isContain = true;
                break;
            }
        }
        return !isContain;
    }

    private String prepareLine(String newLine) {
        String searchStr = args.searchStr;
        String replaceStr = args.replaceStr;

        String replacedLine = "";
        String result = "";

        replacedLine = newLine.replaceAll(searchStr, replaceStr);
        boolean isNewNotContain = containsNoSearchPart(newLine, searchStr);
        boolean isReplacedNotContain = containsNoSearchPart(replacedLine, searchStr);

        if (isNewNotContain || isReplacedNotContain) {
            result = lineBuffer.toString();
            lineBuffer.setLength(0);

            if (isNewNotContain) {
                result += newLine + "\n";
            } else {
                result += replacedLine + "\n";
            }
            return result;
        }

        replacedLine = lineBuffer
                .append(" ")
                .append(newLine)
                .toString()
                .replaceAll(searchStr, replaceStr)
                .trim();
        lineBuffer.setLength(0);
        lineBuffer.append(replacedLine);

        return result;
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
                write(lineBuffer.toString());
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void write(String line) throws IOException {
        if (line.isEmpty()) return;

        try {
            writer.write(line);
        } catch (IOException ex) {
            ex.printStackTrace();
            writer.close();
        }
    }

}

class Main {

    public static void main(String[] args) {
        Replacer replacer = new Replacer(args);
        replacer.replace();
    }

}
