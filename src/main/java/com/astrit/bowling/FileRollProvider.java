package com.astrit.bowling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRollProvider implements RollProvider {

    private final String fileName;

    FileRollProvider(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<InputRollValue> getRolls() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<InputRollValue> rolls = stream.map(FileRollProvider::readLine).collect(Collectors.toList());
            if (rolls.isEmpty()) {
                throw new RuntimeException("Invalid File. No rolls found. File: " + fileName);
            }
            return rolls;
        } catch (IOException e) {
            throw new RuntimeException("Invalid file: ", e);
        }
    }

    private static InputRollValue readLine(String input) {
        String[] tokens = input.split("\t", 2);
        if (tokens.length != 2) {
            throw new RuntimeException("Invalid input: " + input);
        }
        return new InputRollValue(tokens[0], tokens[1]);
    }
}
