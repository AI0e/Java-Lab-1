import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Laboratory Work #1
 * Topic: Arrays in Java
 * Author: Potapenko Eldar
 * * Variant (14):
 * C5 = 4 (Matrix Multiplication: C = A x B)
 * C7 = 0 (Element type: double)
 * C11 = 3 (Action on C: Sum of max elements in each row)
 */
public class Lab1 {

    private static final int MIN_MATRIX_SIZE = 1;
    private static final int MAX_MATRIX_SIZE = 10;

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            boolean exit = false;
            Random rand = new Random(); // Створюємо Random один раз

            while (!exit) {

                try {
                    int rowsA, colsA, rowsB, colsB;
                    double[][] A, B, C;
                    double finalSum;

                    System.out.println("--- Enter matrix dimensions ---");
                    System.out.println("Note: All dimensions must be between "
                            + MIN_MATRIX_SIZE + " and " + MAX_MATRIX_SIZE + ".");

                    rowsA = readIntWithValidation(scanner,
                            "Number of rows for A: ", MIN_MATRIX_SIZE, MAX_MATRIX_SIZE);

                    colsA = readIntWithValidation(scanner,
                            "Number of columns for A: ", MIN_MATRIX_SIZE, MAX_MATRIX_SIZE);

                    rowsB = readIntWithValidation(scanner,
                            "Number of rows for B: ", MIN_MATRIX_SIZE, MAX_MATRIX_SIZE);

                    colsB = readIntWithValidation(scanner,
                            "Number of columns for B: ", MIN_MATRIX_SIZE, MAX_MATRIX_SIZE);

                    if (colsA != rowsB) {
                        throw new IllegalArgumentException(
                                "Multiplication not possible: columns A (" + colsA + ") != rows B (" + rowsB + ")");
                    }

                    System.out.println("\nGenerating matrix A...");
                    A = generateRandomMatrix(rowsA, colsA, rand);

                    System.out.println("Generating matrix B...");
                    B = generateRandomMatrix(rowsB, colsB, rand);

                    System.out.println("\n--- Matrix A ---");
                    printMatrix(A);
                    System.out.println("\n--- Matrix B ---");
                    printMatrix(B);

                    C = multiplyMatrices(A, B);
                    System.out.println("\n--- Result of Action 1 (C = A x B) ---");
                    printMatrix(C);

                    finalSum = calculateSumOfMaxInRows(C);
                    System.out.println("\n--- Result of Action 2 ---");
                    System.out.printf("Total sum of the largest elements in each row: %.2f\n", finalSum);

                } catch (InputMismatchException e) {
                    System.err.println("INPUT ERROR: An integer was expected.");
                } catch (IllegalArgumentException e) {
                    System.err.println("LOGIC ERROR: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                }

                // Запит на повторний запуск
                System.out.print("\nDo you want to exit? (y/n): ");
                String choice = scanner.next().trim().toLowerCase();
                if (choice.equals("y")) {
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                }
                System.out.println(); // Порожній рядок для кращої читабельності
            }
        }
    }

    /**
     * Reads an integer from the user with validation.
     * Keeps asking in a loop until a valid integer within the specified
     * range is entered.
     *
     * @param scanner The Scanner object to read from
     * @param prompt  The message to display to the user
     * @param min     The minimum allowed value (inclusive)
     * @param max     The maximum allowed value (inclusive)
     * @return A validated integer from the user
     */
    public static int readIntWithValidation(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();

                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.err.println("Error: Value must be between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: You must enter a valid integer.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Creates a matrix of a given size and fills it
     * with random double values (from 0.0 to 10.0).
     *
     * @param rows Number of rows
     * @param cols Number of columns
     * @param rand Random object for number generation
     * @return A new double[][] matrix
     */
    public static double[][] generateRandomMatrix(int rows, int cols, Random rand) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextDouble() * 10.0;
            }
        }
        return matrix;
    }

    /**
     * Prints a double matrix to the console with formatting.
     *
     * @param matrix The matrix to print
     */
    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.print("[");
            for (int j = 0; j < row.length; j++) {
                System.out.printf("%6.2f", row[j]);
                if (j < row.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    /**
     * Performs matrix multiplication C = A x B.
     * Precondition: number of columns in A == number of rows in B.
     *
     * @param A The first matrix (left operand)
     * @param B The second matrix (right operand)
     * @return The resulting matrix C
     */
    public static double[][] multiplyMatrices(double[][] A, double[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;
        double[][] C = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    /**
     * Calculates the sum of the largest elements in each row of the matrix.
     *
     * @param C The matrix to process
     * @return The total sum of max elements
     */
    public static double calculateSumOfMaxInRows(double[][] C) {
        double totalSumOfMax = 0;
        for (int i = 0; i < C.length; i++) {
            double maxInRow = C[i][0];
            for (int j = 1; j < C[i].length; j++) {
                if (C[i][j] > maxInRow) {
                    maxInRow = C[i][j];
                }
            }
            totalSumOfMax += maxInRow;
        }
        return totalSumOfMax;
    }
}