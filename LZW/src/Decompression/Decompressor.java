package Decompression;

import Compression.Compressor;

import java.io.*;
import java.util.*;

public class Decompressor {
    public static void decompressLZW(String compressedFile, String outputFile) {
        try (DataInputStream input = new DataInputStream(new FileInputStream(compressedFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            Map<Integer, String> dictionary = new HashMap<>();
            int nextCode = 256;

            for (int i = 0; i < 256; i++) {
                dictionary.put(i, Character.toString((char) i));
            }

            List<String> buffer = new ArrayList<>();
            int prevCode = -1;
            int currentCode;
            while (input.available() > 0) {
                currentCode = input.readInt();
                String entry;
                if (dictionary.containsKey(currentCode)) {
                    entry = dictionary.get(currentCode);
                    writer.write(entry);
                    if (prevCode != -1) {
                        dictionary.put(nextCode++, dictionary.get(prevCode) + entry.charAt(0));
                    }
                } else if (currentCode == nextCode) {
                    entry = dictionary.get(prevCode) + dictionary.get(prevCode).charAt(0);
                    writer.write(entry);
                    dictionary.put(nextCode++, entry);
                } else {
                    System.out.println("Error in compressed file: Invalid code encountered.");
                    return;
                }
                buffer.add(entry);
                prevCode = currentCode;
            }

            System.out.println("Decompression completed.");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {
        String compressedFile = "compressed.bin";
        String decompressedFile = "decompressed.txt";

        // Compress the text file
        Decompressor.decompressLZW(compressedFile,decompressedFile );
    }
}
