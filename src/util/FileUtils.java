package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readAllLines(String filePath) throws IOException {
        List<String> fileLines = new ArrayList<>();
        Path pathToFile = Paths.get(filePath);

        if (!Files.exists(pathToFile)) {
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            System.out.println("Arquivo criado: " + filePath);
            return fileLines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                fileLines.add(currentLine);
            }
        }
        return fileLines;
    }
}
