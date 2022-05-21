package com.wildcodeschool.patent.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class FileReaderService {

    /**
     * Reads a file and returns its content as a String.
     * @return
     * @throws Exception
     */
    public String readFile(String filePath) throws Exception
    {

        // The file to read.
        File file = new File(filePath);

        //The reader to read the file.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String message;

        // The string to read.
        while ((message = bufferedReader.readLine()) != null) {

            // The message to return.
            return message;
        }
        return null;
    }
}
