package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public List<String> readFromFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            if (Files.exists(path)) {
                return Files.readAllLines(path);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + filePath + ": " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public void writeAllToFile(String filePath, List<String> lines) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao escrever todas as linhas no arquivo " + filePath + ": " + e.getMessage());
        }
    }

    public void writeToFile(String filePath, String data, boolean append) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            if (append) {
                Files.write(path, (data + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.write(path, (data + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever dados no arquivo " + filePath + ": " + e.getMessage());
        }
    }

    public void writeLine(String filePath, String line, boolean append) {
        writeToFile(filePath, line, append);
    }
}
