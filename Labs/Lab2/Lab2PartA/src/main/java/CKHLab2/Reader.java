package CKHLab2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Reader {
    public void readFile() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the file path and name:");
        // /home/inet2005/Documents/data.txt
        String filePath = in.next();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            System.out.println("The file contains the following: ");
            stream.forEach(System.out::println);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read the file, please input a valid file path and name.");
        }
    }
}
