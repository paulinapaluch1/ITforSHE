import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TableReader {


    protected int[][] readTableFromFile(String path, int rows, int maxCol) {
        int table[][] = new int[rows][maxCol];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] valuesInLine = line.split(";");
                for (int col = 0; col < maxCol; col++)
                    table[row][col] = Integer.parseInt(valuesInLine[col]);
                row++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return table;
    }
}

