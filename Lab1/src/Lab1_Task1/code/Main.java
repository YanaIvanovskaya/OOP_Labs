package Lab1_Task1.code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final int argsCount = 4;
        final String errorArguments =
                "4 arguments expected: <input file> <output file> <search> <replace>";
        final String errorSearchAndReplaceEquals =
                "The replacement string is the same as the search string";

        String inputPath;
        String outputPath;
        String search;
        String replace;

        if (args.length != argsCount) {
            System.out.println(errorArguments);
            return;
        } else {
            inputPath = args[0];
            outputPath = args[1];
            search = args[2];
            replace = args[3];
        }
        if (search.equals(replace)) {
            System.out.println(errorSearchAndReplaceEquals);
            return;
        }

        Scanner scanner = getScannerOrNull(inputPath);
        FileWriter writer = getFileWriterOrNull(outputPath);

        if (scanner == null) {
            System.out.println(getFileErrorMessage(inputPath));
        }
        if (writer == null) {
            System.out.println(getFileErrorMessage(outputPath));
        }

        if (scanner != null && writer != null) {
            readAndReplace(scanner, writer, search, replace);
        }
    }

    private static void readAndReplace(Scanner scanner, FileWriter writer, String search, String replace) {
        String newLine;
        String preparedLine;

        try (scanner) {
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();
                preparedLine = newLine.replaceAll(search, replace);
                if (scanner.hasNextLine()) {
                    preparedLine += " ";
                }
                write(writer, preparedLine);
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void write(FileWriter writer, String line) throws IOException {
        if (line.isEmpty()) return;

        try {
            writer.write(line);
            System.out.print(line);
        } catch (IOException ex) {
            ex.printStackTrace();
            writer.close();
        }
    }

    private static String getFileErrorMessage(String path) {
        return "File " + path + " not found or not available";
    }

    private static FileWriter getFileWriterOrNull(String outputPath) {
        File output = new File(outputPath);
        boolean isValidAndWritable = isValidFile(output) && output.canWrite();

        try {
            return isValidAndWritable ? new FileWriter(output, false) : null;
        } catch (IOException ex) {
            return null;
        }
    }

    private static Scanner getScannerOrNull(String inputPath) {
        File input = new File(inputPath);
        boolean isValidAndReadable = isValidFile(input) && input.canRead();

        try {
            return isValidAndReadable ? new Scanner(input) : null;
        } catch (IOException ex) {
            return null;
        }
    }

    private static boolean isValidFile(File file) {
        return file.exists() && file.isFile();
    }

}


