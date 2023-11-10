package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    // NOT USED
    public List<String> getWords(){
        // Read CSV file
        List<String> words = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("src/test/resources/words.csv"))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String searchTerm = nextLine[0]; // assuming the search term is in the first column

                words.add(searchTerm);
                System.out.println(searchTerm);
            }
        }catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        return words;
    }
}
