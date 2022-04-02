package Lab1_Task3.code;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File input = args.length != 1 ? null : new File(args[0]);

        if (input == null) {
            System.out.println("1 argument expected: <input file path>");
            return;
        }

        invertMatrixFrom(input);
    }

    static void invertMatrixFrom(File input) {
        try {
            double[][] originalMatrix = readMatrixFromFile(input);
            double[][] result = Matrix3x3Inverter.invertOrNull(originalMatrix);

            if (result == null) {
                System.out.println("Matrix can not be inverted because its determinant is 0");
            } else printMatrix(result);
        } catch (FileNotFoundException ex) {
            System.out.println("File is not correct");
        } catch (IllegalArgumentException ex) {
            System.out.println("Matrix in file must be of size 3x3 and contain only numeric values");
        }
    }

    static void printMatrix(double[][] matrix) {
        DecimalFormat formatter = new DecimalFormat("#.###");
        for (double[] doubles : matrix) {
            for (int column = 0; column < matrix.length; column++) {
                System.out.print(formatter.format(doubles[column]));
                if (column < matrix.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    static double[][] readMatrixFromFile(File input) throws FileNotFoundException {
        double[][] originalMatrix = new double[3][];
        int rowCounter = 0;

        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine() && rowCounter < 3) {
                String newRow = scanner.nextLine().trim();
                originalMatrix[rowCounter] = convertToDoubleArrayOrNull(newRow.split(" "));
                rowCounter++;
            }
        }

        return originalMatrix;
    }

    static double[] convertToDoubleArrayOrNull(String[] stringArray) {
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

final class Matrix3x3Inverter {

    private Matrix3x3Inverter() {
    }

    private static final int MATRIX_SIZE = 3;

    static double[][] invertOrNull(double[][] matrix) throws IllegalArgumentException {
        if (matrix == null || !isMatrixValid(matrix)) {
            throw new IllegalArgumentException("Matrix is incorrect");
        }

        double determinant = find3xDeterminant(matrix);
        if (determinant == 0) {
            return null;
        }

        double[][] minorMatrix = findMinorMatrix(matrix);
        double[][] transposedMinorMatrix = transpose(minorMatrix);

        return scale(transposedMinorMatrix, 1 / determinant);
    }

    private static boolean isMatrixValid(double[][] matrix) {
        boolean isRowSizeCorrect = matrix.length == MATRIX_SIZE;
        boolean isFirstRowCorrect = matrix[0] != null && matrix[0].length == MATRIX_SIZE;
        boolean isSecondRowCorrect = matrix[1] != null && matrix[1].length == MATRIX_SIZE;
        boolean isThirdRowCorrect = matrix[2] != null && matrix[2].length == MATRIX_SIZE;
        return isRowSizeCorrect && isFirstRowCorrect && isSecondRowCorrect && isThirdRowCorrect;
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

    private static double[][] scale(double[][] matrix, double determinant) {
        double[][] invertedMatrix = new double[MATRIX_SIZE][MATRIX_SIZE];
        for (int row = 0; row < MATRIX_SIZE; row++) {
            for (int column = 0; column < MATRIX_SIZE; column++) {
                invertedMatrix[row][column] =
                        matrix[row][column] * determinant;
            }
        }
        return invertedMatrix;
    }

    private static double find2xDeterminant(double[][] matrix) {
        return (matrix[0][0] * matrix[1][1])
                - (matrix[0][1] * matrix[1][0]);
    }

}


