import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
* Made by Taylor Nastally, 04/23/2024
* */

public class Project1 {
    public static void main(String[] args) {
        // calculateRuntime();
        String fileContents = getFile();
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
        System.out.println("Number of instabilities: " + instabilities);
    }

    /*
    * debug and optimization function
    * remove before submission
    *
   private static void calculateRuntime() {
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        // Record start time
        long startTime = System.currentTimeMillis();
        // ... your existing code ...
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

        int instabilities = evaluateInstabilities(matrixOne, matrixTwo, matchingMatrix);
        System.out.println("Number of instabilities: " + instabilities);
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
    }*/

    private static int evaluateInstabilities(int[][] menPreferences, int[][] womenPreferences, int[][] matching) {
        int size = menPreferences.length;
        int instabilities = 0;
        boolean manPrefers;
        boolean womanPrefers;
        int currentPartnerOfMan;
        int currentPartnerOfWoman;

        // Iterate over all pairs of individuals
        for (int man = 0; man < size; man++) {
            currentPartnerOfMan = matching[man][1];
            for (int woman = 0; woman < size; woman++) {
                currentPartnerOfWoman = matching[woman][0];
                manPrefers = false;
                // man is left col and woman is right col, this means the current partners are the opposite cols
                // Check if man prefers woman over his current partner
                int currentPartnerIndex = 0;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (menPreferences[i][j] == currentPartnerOfMan) {
                            currentPartnerIndex = j;
                            break;
                        }
                    }
                    if (menPreferences[man][i] != currentPartnerOfMan && i < currentPartnerIndex) {
                        manPrefers = true;
                        break;
                    } else if (menPreferences[man][i] == currentPartnerOfMan) {
                        break;
                        // he is matched with his best partner
                    }

                }

                currentPartnerIndex = 0;
                womanPrefers = false;
                // Check if woman prefers man over her current partner
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (womenPreferences[i][j] == currentPartnerOfWoman) {
                            currentPartnerIndex = j;
                            break;
                        }
                    }
                    if (womenPreferences[woman][i] != currentPartnerOfWoman && i < currentPartnerIndex) {
                        womanPrefers = true;
                        break;
                    } else if (womenPreferences[woman][i] == currentPartnerOfWoman) {
                        break;
                        // she is matched with her best partner
                    }
                }

                // If both man and woman prefer each other over their current partners, it's an instability
                if (manPrefers && womanPrefers)
                    instabilities++;
            }
        }

        return instabilities;
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