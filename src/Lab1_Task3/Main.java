package Lab1_Task3;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("HELLO");
        File input = getInputFileOrNull(args);

        if (input == null) {
            System.out.println("1 argument expected <input file>");
            return;
        }
        if (!input.exists() || !input.isFile() || !input.canRead()) {
            System.out.println("File is not correct");
        }

        double[][] originalMatrix = readMatrixFromFile(input);

        if (isMatrixValid(originalMatrix)) {
            double[][] result = MatrixInverter.invertOrNull(originalMatrix);
            if (result == null) {
                System.out.println("Matrix can not be inverted because its determinant is 0");
                return;
            }
            System.out.println(MatrixInverter.isCorrectInverted(originalMatrix, result));
            printMatrix(result);
        } else {
            System.out.println("Matrix in file is not correct");
        }
    }

    static void printMatrix(double[][] matrix) {
        DecimalFormat formatter = new DecimalFormat("#.### ");
        formatter.setRoundingMode(RoundingMode.CEILING);
        for (double[] doubles : matrix) {
            for (int column = 0; column < matrix.length; column++) {
                System.out.print(formatter.format(doubles[column]));
            }
            System.out.print("\n");
        }
    }

    static File getInputFileOrNull(String[] args) {
        System.out.println(Arrays.toString(args));
        if (args.length != 1) {
            return null;
        } else {
            return new File(args[0]);
        }
    }

    static double[][] readMatrixFromFile(File input) {
        double[][] originalMatrix = new double[3][];
        int rowCounter = 0;
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                String newRow = scanner.nextLine().trim();
                if (rowCounter == 3) break;
                originalMatrix[rowCounter] = convertToDoubleArrayIrNull(newRow.split(" "));
                System.out.println(Arrays.toString(convertToDoubleArrayIrNull(newRow.split(" "))));
                rowCounter++;
            }

        } catch (NoSuchElementException | FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return originalMatrix;
    }

    static boolean isMatrixValid(double[][] matrix) {
        boolean isRowSizeCorrect = matrix.length == 3;
        boolean isFirstRowCorrect = matrix[0] != null && matrix[0].length == 3;
        boolean isSecondRowCorrect = matrix[1] != null && matrix[1].length == 3;
        boolean isThirdRowCorrect = matrix[2] != null && matrix[2].length == 3;
        return isRowSizeCorrect && isFirstRowCorrect && isSecondRowCorrect && isThirdRowCorrect;
    }

    static double[] convertToDoubleArrayIrNull(String[] stringArray) {
        int size = stringArray.length;
        double[] result = new double[size];

        try {
            for (int i = 0; i < size; i++) {
                String element = stringArray[i];
                if (element != null) {
                    result[i] = Double.parseDouble(element);
                }
            }
            return result;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}

final class MatrixInverter {

    private MatrixInverter() {
    }

    private static final double[][] E = {
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {0.0, 0.0, 1.0}
    };

    private static final int MATRIX_SIZE = 3;

    static double[][] invertOrNull(double[][] matrix) {
        double determinant = find3xDeterminant(matrix);
        if (determinant == 0) {
            return null;
        }
        double[][] minorMatrix = findMinorMatrix(matrix);
        System.out.println(Arrays.deepToString(minorMatrix));
        double[][] transposedMinorMatrix = transpose(minorMatrix);
        double[][] invertedMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                invertedMatrix[row][column] =
                        transposedMinorMatrix[row][column] / determinant;
            }
        }
        return invertedMatrix;
    }

    private static double find3xDeterminant(double[][] matrix) {
        return (matrix[0][0] * findMinorDeterminantByPosition(0, 0, matrix))
                - (matrix[0][1] * findMinorDeterminantByPosition(0, 1, matrix))
                + (matrix[0][2] * findMinorDeterminantByPosition(0, 2, matrix));
    }

    private static double[][] findMinorMatrix(double[][] matrix) {
        double[][] minorMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];

        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                minorMatrix[row][column] = findMinorDeterminantByPosition(
                        row,
                        column,
                        matrix
                );
            }
        }

        minorMatrix[0][1] *= -1;
        minorMatrix[1][0] *= -1;
        minorMatrix[2][1] *= -1;
        minorMatrix[1][2] *= -1;

        return minorMatrix;
    }

    private static double findMinorDeterminantByPosition(
            int row,
            int column,
            double[][] matrix
    ) {
        int[][] indexes = {
                {1, 2},
                {0, 2},
                {0, 1}
        };

        int row1 = indexes[row][0];
        int row2 = indexes[row][1];

        int column1 = indexes[column][0];
        int column2 = indexes[column][1];

        return find2xDeterminant(new double[][]{
                {matrix[row1][column1], matrix[row1][column2]},
                {matrix[row2][column1], matrix[row2][column2]}
        });
    }

    private static double[][] transpose(double[][] matrix) {
        double[][] transposedMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];

        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                transposedMatrix[row][column] = matrix[column][row];
            }
        }
        return transposedMatrix;
    }

    private static double find2xDeterminant(double[][] matrix) {
        return (matrix[0][0] * matrix[1][1])
                - (matrix[0][1] * matrix[1][0]);
    }

    static boolean isCorrectInverted(
            double[][] originalMatrix,
            double[][] invertedMatrix
    ) {
        double[][] result = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                result[row][column] = (int) (
                        (originalMatrix[row][0] *
                                invertedMatrix[0][column])

                                + (originalMatrix[row][1] *
                                invertedMatrix[1][column])

                                + (originalMatrix[row][2] *
                                invertedMatrix[2][column])
                );
            }
        }
        System.out.println(Arrays.deepToString(result));
        return Arrays.deepEquals(result, E);
    }

}


