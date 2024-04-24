import java.nio.file.*;
import java.util.Arrays;

public class Project1 {
    public static void main(String[] args) {
        String fileContents = getFile();
        System.out.println(fileContents);
        if (fileContents == null) {
            System.out.println("File not found or other error occurred. Exiting.");
            return;
        }
        char sizeChar = fileContents.charAt(0);
        int size = Character.getNumericValue(sizeChar);
        System.out.println("Size of matrices: " + sizeChar);
        int rangeOne = getRange(size, 1);
        int[][] matrixOne = getMatrix(fileContents, size, rangeOne);
        System.out.println("Extracted values from Matrix 1: ");
        printMatrix(matrixOne);
        int rangeTwo = getRange(size, 2);
        int[][] matrixTwo = getMatrix(fileContents, size, rangeTwo);
    }

    private static int getRange(int size, int matrixNum) {
        return matrixNum == 1 ? 1 : size;
    }

    private static int[][] getMatrix(String contents, int size, int range) {
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