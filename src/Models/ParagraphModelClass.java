/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author DELL
 */
public class ParagraphModelClass {
    
      private List<String> availableLang = new ArrayList<>();
    private String selectedLang = " ";
    private String fileName = " ";
    private String absolutePath = " ";
    private StringBuilder paragraph = new StringBuilder();
    private File directory = new File("D:\\Java\\JavaFXTest\\dist\\dictionary\\dictionary");
    
    
    
    
    public String createPara(String selectedLang) {
        this.selectedLang = selectedLang;
       
        paragraph.setLength(0); // Clear the existing paragraph
        
        try {
            // Check if files exist
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();

                if (files != null) {
                    for (File file : files) {
                        fileName = file.getName();
                        String langName = fileName.substring(0, fileName.lastIndexOf('.'));
                        absolutePath = file.getAbsolutePath();
                        availableLang.add(langName);

                        if (langName.equals(selectedLang)) {
                            List<String> words = new ArrayList<>();
                            try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    // Split the line into words using a suitable delimiter
                                    String[] lineWords = line.split(" ");
                                    for (String word : lineWords) {
                                        // Add the word to the list of words
                                        words.add(word);
                                    }
                                }
                                Random random = new Random();

                                for (int i = 0; i < 30; i++) {
                                    // Generate a random index to select a word from the list
                                    int randomIndex = random.nextInt(words.size());
                                    String word = words.get(randomIndex);
                                    paragraph.append(word).append(" ");
                                }
                            } catch (IOException e) {
                                // Handle any errors that may occur during file reading
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        // Print the newly generated paragraph
        System.out.println(paragraph.toString());

    
    return paragraph.toString();
    }
    
}
