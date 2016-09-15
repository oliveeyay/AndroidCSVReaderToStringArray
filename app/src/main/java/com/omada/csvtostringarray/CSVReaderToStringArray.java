package com.omada.csvtostringarray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by olivier.goutay on 9/15/16.
 * Allows to convert a CSV file to a string-array Android xml file.
 */
public class CSVReaderToStringArray {

    /**
     * All the final words to create a string-array resource file on Android
     */
    private static final String XML_ENCODING = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    private static final String RESOURCES_OPEN_TAG = "<resources>";
    private static final String RESOURCES_CLOSE_TAG = "</resources>";
    private static final String STRING_ARRAY_OPEN_TAG = "<string-array name=\"words\">";
    private static final String STRING_ARRAY_CLOSE_TAG = "</string-array>";
    private static final String ITEM_OPEN_TAG = "<item>";
    private static final String ITEM_CLOSE_TAG = "</item>";

    /**
     * Main methods, calls directly {@link #csvReaderToStringArray}
     *
     * @param args Args are configurable in app/build.gradle
     */
    public static void main(String args[]) throws Exception {
        csvReaderToStringArray(args);
    }

    /**
     * Transforms a csv file into a string-array android resource
     * (remove all comas also)
     *
     * @param args The args from {@link #main(String[])}
     */
    private static void csvReaderToStringArray(String args[]) throws IOException {
        ArrayList<String> words = readFromCsv(args[0]);

        writeCsvToAndroidStringArrayResource(words, args[1]);
    }

    /**
     * Reads a CSV file from the argument, are returns an {@link ArrayList} with all the words, line by line
     *
     * @param csvFile The file we want to read
     * @return The formatted {@link ArrayList}
     */
    private static ArrayList<String> readFromCsv(String csvFile) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(csvFile));

        String word;
        while ((word = br.readLine()) != null) {
            word = word.replaceAll(",", "");
            words.add(word);
        }

        return words;
    }

    /**
     * Writes to an Android string-array resource file the {@link ArrayList} argument
     *
     * @param words      The {@link ArrayList} we want to write
     * @param outputFile The {@link java.io.File} path we want to write to
     */
    private static void writeCsvToAndroidStringArrayResource(ArrayList<String> words, String outputFile) throws IOException {
        FileOutputStream fOut = new FileOutputStream(new File(outputFile));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));

        //Open tags
        bw.write(XML_ENCODING);
        bw.newLine();
        bw.write(RESOURCES_OPEN_TAG);
        bw.newLine();
        bw.write(STRING_ARRAY_OPEN_TAG);

        for (String word : words) {
            bw.write(ITEM_OPEN_TAG + word + ITEM_CLOSE_TAG);
            bw.newLine();
        }

        //Close tags
        bw.write(STRING_ARRAY_CLOSE_TAG);
        bw.newLine();
        bw.write(RESOURCES_CLOSE_TAG);
        bw.close();
        fOut.close();
    }

}
