/*
 * Taylor Nastally
 * Project 1
 * */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class Project1 {
    public static void main(String[] args) {
        calculateRuntime();
        /*String fileContents = getFile();
        if (fileContents == null) {
            System.out.println("File not found or other error occurred. Exiting.");
            return;
        }

        char sizeChar = fileContents.charAt(0);
        int size = Character.getNumericValue(sizeChar);

        int rangeOne = getRange(size, 1);
        int[][] matrixOne = getPreferencesMatrix(fileContents, size, rangeOne);

        int rangeTwo = getRange(size, 2);
        int[][] matrixTwo = getPreferencesMatrix(fileContents, size, rangeTwo);

        int[][] matchingMatrix = getMatchingMatrix(fileContents, size);

        int instabilities = evaluateInstabilities(matrixOne, matrixTwo, matchingMatrix);
        System.out.println(instabilities);*/
    }

    /*
    * debug and optimization function
    * remove before submission
    **/
   private static void calculateRuntime() {
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        // Record start time
        long startTime = System.currentTimeMillis();
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

        /*CompletableFuture<int[][]> matrixOne = CompletableFuture.supplyAsync(() -> {
            int rangeOne = getRange(size, 1);
            return getPreferencesMatrix(fileContents, size, rangeOne);
        });
        CompletableFuture<int[][]> matrixTwo = CompletableFuture.supplyAsync(() -> {
            int rangeTwo = getRange(size, 2);
            return getPreferencesMatrix(fileContents, size, rangeTwo);
        });

        CompletableFuture<int[][]> matchingMatrix = CompletableFuture.supplyAsync(() -> getMatchingMatrix(fileContents, size));

        try {
            int[][] mensMatrix = matrixOne.get();
            int[][] womensMatrix = matrixTwo.get();
            int[][] match = matchingMatrix.get();

            System.out.println("Extracted values from Matrix 1: ");
            printMatrix(mensMatrix);
            System.out.println();

            System.out.println("Extracted values from Matrix 2: ");
            printMatrix(womensMatrix);
            System.out.println();

            printMatrix(match);
            System.out.println();

            int instabilities = evaluateInstabilities(mensMatrix, womensMatrix, match);
            System.out.println("Number of instabilities: " + instabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        int rangeOne = getRange(size, 1);
        int[][] matrixOne = getPreferencesMatrix(fileContents, size, rangeOne);

        int rangeTwo = getRange(size, 2);
        int[][] matrixTwo = getPreferencesMatrix(fileContents, size, rangeTwo);

        int[][] matchingMatrix = getMatchingMatrix(fileContents, size);

        int instabilities = evaluateInstabilities(matrixOne, matrixTwo, matchingMatrix);
        System.out.println(instabilities);

        // Record end time
        long endTime = System.currentTimeMillis();
        // Run the garbage collector again
        runtime.gc();
        // Calculate the used memory after running the program
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // Calculate the time taken
        long timeTaken = endTime - startTime;
        // Calculate the memory used by your program
        long memoryUsed = memoryAfter - memoryBefore;
        double memoryUsedInMB = memoryUsed / (1000.0 * 1000.0);
        System.out.println("Time taken: " + timeTaken + " milliseconds");
        System.out.println("Memory used: " + memoryUsed + " bytes");
        System.out.println("Memory used: " + memoryUsedInMB + " MB");
    }

    private static int evaluateInstabilities(int[][] menPreferences, int[][] womenPreferences, int[][] matching) {
        int size = menPreferences.length;
        int instabilities = 0;
        int[] manCurrentWifeIndexAndValue;
        int[] womanCurrentHusbandIndexAndValue;

        // Iterate over all pairs of individuals
        for (int i = 0; i < size; i++) {
            manCurrentWifeIndexAndValue = getCurrentWifeIndex(menPreferences, matching, i);
            womanCurrentHusbandIndexAndValue = getCurrentHusbandIndex(womenPreferences, matching, i);
            for (int j = 0; j < size; j++) {
                // If both man and woman prefer each other over their current partners, it's an instability
                if (menPreferences[i][j] != manCurrentWifeIndexAndValue[0] && j < manCurrentWifeIndexAndValue[1] &&
                        womenPreferences[i][j] != womanCurrentHusbandIndexAndValue[0] && j < womanCurrentHusbandIndexAndValue[1])
                    instabilities++;
            }
        }
        return instabilities;
    }

    private static int[] getCurrentHusbandIndex(int[][] preferences, int[][] matching, int wife) {
        int[] result = new int[2];
        for (int[] pairing : matching) {
            if (pairing[1] == wife + 1) {
                result[0] = pairing[0];
                break;
            }
        }
        for (int i = 0; i < preferences[wife].length; i++) {
            if (preferences[wife][i] == result[0]) {
                result[1] = i;
                return result;
            }
        }
        result[1] = Integer.MAX_VALUE;
        return result;
    }

    private static int[] getCurrentWifeIndex(int[][] preferences, int[][] matching, int husband) {
        int[] result = new int[2];
        for (int[] pairing : matching) {
            if (pairing[0] == husband + 1) {
                result[0] = pairing[1];
                break;
            }
        }
        for (int i = 0; i < preferences[husband].length; i++) {
            if (preferences[husband][i] == result[0]) {
                result[1] = i;
                return result;
            }
        }
        result[1] = Integer.MAX_VALUE;
        return result;
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
        Path path = Paths.get(System.getProperty("user.dir"), "input.txt");
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