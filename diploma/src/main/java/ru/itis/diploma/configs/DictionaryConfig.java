package ru.itis.diploma.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DictionaryConfig {

    @Bean
    public Map<String, String> getDictionary() throws IOException {
        Map<String, String> dict = new HashMap<>();

        BufferedReader br = null;
        try {

            // create file object
            File file = new File("src/main/resources/dictionary.txt");

            // create BufferedReader object from the File
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                // split the line by :
                String[] parts = line.split("=");

                // first part is name, second is number
                String name = parts[0].trim();
                String number = parts[1].trim();

                // put name, number in HashMap if they are
                // not empty
                if (!name.equals("") && !number.equals(""))
                    dict.put(name, number);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }

        return dict;
    }
}
