import java.nio.file.*;

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
        int[][] matrix = getMatrix(fileContents, size);
    }

    private static int[][] getMatrix(String contents, int size) {
        String[] lines = contents.split("\n");
        int[][] matrix = new int[size][size];

        int row = 0;
        for (int i = 1; i < lines.length; i++) {
            System.out.println(lines[i]);
        }
        return null;
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
}