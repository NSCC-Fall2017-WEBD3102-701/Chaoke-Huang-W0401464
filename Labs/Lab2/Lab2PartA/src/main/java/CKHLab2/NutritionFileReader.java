package CKHLab2;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class NutritionFileReader {
    public void processFood() {
        Scanner in = new Scanner(System.in);
        String selection = "";
        int value = 0;
        System.out.println("Please enter the file path and name:");
        // /home/inet2005/Documents/nutrition.xml
        String filePath = in.next();
        File file = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Food> foods = new ArrayList<>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = null;
            try {
                doc = builder.parse(file);

                System.out.println(doc.getFirstChild().getNodeName());
                NodeList nutriList = doc.getElementsByTagName("food");
                for (int i = 0; i < nutriList.getLength(); i++) {
                    Food food = new Food();
                    foods.add(food);
                    Node nutriNode = nutriList.item(i);
                    NodeList nutriChildList = nutriNode.getChildNodes();

                    for (int j = 0; j < nutriChildList.getLength(); j++) {
                        Node nutriChildNode = nutriChildList.item(j);
                        if (nutriChildNode.getNodeName().equals("name")) {
                            food.setName(nutriChildNode.getTextContent());
                        } else if (nutriChildNode.getNodeName().equals("cholesterol")) {
                            food.setCholesterol(Integer.parseInt(nutriChildNode.getTextContent()));
                        } else if (nutriChildNode.getNodeName().equals("calories")) {
                            NamedNodeMap calNodeMap = nutriChildNode.getAttributes();
                            Node cal = calNodeMap.getNamedItem("total");
                            food.setTotalCalories(Integer.parseInt(cal.getTextContent()));

                        } else if (nutriChildNode.getNodeName().equals("sodium")) {
                            food.setSodium(Integer.parseInt(nutriChildNode.getTextContent()));
                        }
                    }


                }

                do {
                    System.out.println("Select the category to sort by?");
                    System.out.println("1.  Cholesterol");
                    System.out.println("2.  Sodium");
                    System.out.println("3.  Total Calories");
                    selection = in.next();
                } while (!(selection.equals("1") || selection.equals("2") || selection.equals("3")));

            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (selection.equals("3")) {
            Collections.sort(foods, new FoodCaloriesComparator());
            Collections.reverse(foods);
        } else if (selection.equals("2")) {
            Collections.sort(foods, new FoodSodiumComparator());
            Collections.reverse(foods);
        } else if (selection.equals("1")) {
            Collections.sort(foods, new FoodCholesterolComparator());
            Collections.reverse(foods);
        }

        for (Food f : foods
                ) {

            if (selection.equals("3")) {
                value = f.getTotalCalories();
            } else if (selection.equals("2")) {
                value = f.getSodium();
            } else if (selection.equals("1")) {
                value = f.getCholesterol();
            }
            String nameStr = f.getName();
            String[] nameStrSplit = nameStr.split(",");

            String valStr = String.format("%1$-20s", value);
            System.out.println("Name: " + String.format("%1$-50s",nameStrSplit[0]) + "Value: " + valStr);
        }


    }
}

