package com.astrit.bowling;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileRollProviderTest {

    @Test
    public void testGetRolls() {
        final String fileName = "src/test/resources/positive/perfect.txt";
        FileRollProvider fileRollProvider = new FileRollProvider(fileName);
        List<InputRollValue> rolls = fileRollProvider.getRolls();
        assertThat(rolls.size(), IsEqual.equalTo(12));
        for (InputRollValue roll : rolls) {
            assertThat(roll.getPlayerName(), IsEqual.equalTo("Carl"));
            assertThat(roll.getRollValue(), IsEqual.equalTo("10"));
        }
    }

    @Test
    public void testGetRollsEmptyFile() {
        final String fileName = "src/test/resources/negative/empty.txt";
        FileRollProvider fileRollProvider = new FileRollProvider(fileName);
        RuntimeException exception = assertThrows(RuntimeException.class, fileRollProvider::getRolls);
        assertThat(exception.getMessage(), StringContains.containsString("Invalid File. No rolls found. File: "));
    }

    @Test
    public void testGetRollsNonExistingFile() {
        final String fileName = "src/test/resources/negative/no_existing_file.txt";
        FileRollProvider fileRollProvider = new FileRollProvider(fileName);
        RuntimeException exception = assertThrows(RuntimeException.class, fileRollProvider::getRolls);
        assertThat(exception.getMessage(), StringContains.containsString("Invalid file: "));
    }


    @Test
    public void testGetRollsInvalidInput() {
        final String fileName = "src/test/resources/negative/free-text.txt";
        FileRollProvider fileRollProvider = new FileRollProvider(fileName);
        RuntimeException exception = assertThrows(RuntimeException.class, fileRollProvider::getRolls);
        assertThat(exception.getMessage(), StringContains.containsString("Invalid input: "));
    }
}
