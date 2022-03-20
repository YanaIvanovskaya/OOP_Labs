package Lab1_Task1.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("4 arguments expected: <input file> <output file> <search> <replace>");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String search = args[2];
        String replace = args[3];

        try {
            readAndReplace(inputPath, outputPath, search, replace);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void readAndReplace(
            String inputPath,
            String outputPath,
            String search,
            String replace
    ) throws FileNotFoundException {
        String newLine;
        String replacedLine;

        try (Scanner scanner = getScannerOrNull(inputPath)) {
            if (scanner == null) {
                throw new FileNotFoundException(getFileErrorMessage(inputPath));
            }
            FileWriter writer = getFileWriterOrNull(outputPath);
            if (writer == null) {
                throw new FileNotFoundException(getFileErrorMessage(outputPath));
            }
            try (writer) {
                while (scanner.hasNextLine()) {
                    newLine = scanner.nextLine();
                    replacedLine = newLine.replaceAll(search, replace);
                    if (scanner.hasNextLine()) {
                        replacedLine += " ";
                    }
                    writer.write(replacedLine);
                }
            } catch (IOException ignored) {
            }
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
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    private static boolean isValidFile(File file) {
        return file.exists() && file.isFile();
    }

}


