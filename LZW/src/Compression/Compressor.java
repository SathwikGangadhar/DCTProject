package Compression;

import java.io.*;
import java.util.*;

public class Compressor {

    public static void compressLZW(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             DataOutputStream output = new DataOutputStream(new FileOutputStream(outputFile))) {

            Map<String, Integer> dictionary = new HashMap<>();
            int nextCode = 256;

            for (int i = 0; i < 256; i++) {
                dictionary.put(Character.toString((char) i), i);
            }

            StringBuilder current = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                current.append((char) character);
                if (!dictionary.containsKey(current.toString())) {
                    output.writeInt(dictionary.get(current.substring(0, current.length() - 1)));
                    dictionary.put(current.toString(), nextCode++);
                    current = new StringBuilder(Character.toString((char) character));
                }
            }

            if (current.length() > 0) {
                output.writeInt(dictionary.get(current.toString()));
            }

            System.out.println("Compression completed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Which file do you want to compress");
        String filename = sc.nextLine();
        String compressedFile = "compressed.bin";
        String decompressedFile = "decompressed.txt";

        // Compress the text file
        Compressor.compressLZW(filename, compressedFile);
    }
}
