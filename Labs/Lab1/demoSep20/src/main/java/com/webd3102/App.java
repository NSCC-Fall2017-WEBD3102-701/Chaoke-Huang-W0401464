package com.webd3102;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String filename = "/home/inet2005/IdeaProjects/Chaoke-Huang-W0401464/Labs/Lab1/demoSep20/pom.xml";
        try(Stream<String> stream = Files.lines(Paths.get(filename)))
        {
            stream.forEach(System.out::println);
        }
       catch (IOException e) {
            e.printStackTrace();
        }
    }

}
