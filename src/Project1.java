import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
* Made by Taylor Nastally, 04/23/2024
* */

public class Project1 {
    public static void main(String[] args) {
        String fileContents = getFile();
        System.out.println("File contents: ");
        System.out.println(fileContents + "\n");
        if (fileContents == null) {
            System.out.println("File not found or other error occurred. Exiting.");
            return;
        }

        char sizeChar = fileContents.charAt(0);
        int size = Character.getNumericValue(sizeChar);
        System.out.println("Size of matrices: " + sizeChar + "\n");

        int rangeOne = getRange(size, 1);
        int[][] matrixOne = getPreferencesMatrix(fileContents, size, rangeOne);
        System.out.println("Extracted values from Matrix 1: ");
        printMatrix(matrixOne);
        System.out.println();

        int rangeTwo = getRange(size, 2);
        int[][] matrixTwo = getPreferencesMatrix(fileContents, size, rangeTwo);
        System.out.println("Extracted values from Matrix 2: ");
        printMatrix(matrixTwo);
        System.out.println();

        int[][] matchingMatrix = getMatchingMatrix(fileContents, size);
        System.out.println("Extracted values from Matching Matrix: ");
        printMatrix(matchingMatrix);
        System.out.println();
    }

    private static int getRange(int size, int matrixNum) {
        return matrixNum == 1 ? 1 : size + 1;
    }

    private static int[][] getMatchingMatrix(String contents, int size) {
        String[] lines = contents.split("\n");
        int[][] matrix = new int[size][2];

        int row = 0;
        for (int i = lines.length - size; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] numbers = line.split(" ");
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[row][j] = Integer.parseInt(numbers[j]);
            }
            row++;
            if (row > size - 1) {
                break;
            }
        }
        return matrix;
    }

    private static int[][] getPreferencesMatrix(String contents, int size, int range) {
        String[] lines = contents.split("\n");
        int[][] matrix = new int[size][size];

        int row = 0;
        for (int i = range; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] numbers = line.split(" ");
            for (int j = 0; j < size; j++) {
                matrix[row][j] = Integer.parseInt(numbers[j]);
            }
            row++;
            if (row > size - 1) {
                break;
            }
        }
        return matrix;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static String getFile() {
        Path path = Paths.get("src", "input.txt");
        try {
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.printf("%4d", element);
            }
            System.out.println();
        }
    }
}