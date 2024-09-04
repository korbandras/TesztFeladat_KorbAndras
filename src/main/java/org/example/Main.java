package org.example;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("0-No sorting\r\n1 - Frequencies (Ascending)\r\n2 - Frequencies (Descending)\r\n3- Names (Ascending)\r\n4- Names (Descending)");
        System.out.println("Choose how do you want to sort the data (please respond with the number according to the sort): ");

        int choice = scanner.nextInt();
        boolean check = true;
        while(check) {
            try{
                if (choice < 0 || choice > 4) {
                    System.out.println("Invalid choice");
                    System.out.println("Please enter a number between 1 and 4: ");
                }
                else{
                    check = false;
                }
            }catch (NumberFormatException e){
                System.out.println("Please enter number, invalid input!");
            }
        }

        String filePath = "src/main/java/org/example/test-data.xml";
        List<NameFrequency> frequencies = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            NodeList nodeList = document.getElementsByTagName("datafield");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    if ("100".equals(elem.getAttribute("tag"))) {
                        NodeList subfields = elem.getElementsByTagName("subfield");
                        for (int j = 0; j < subfields.getLength(); j++) {
                            Node subNode = subfields.item(j);
                            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element subElem = (Element) subNode;
                                if ("a".equals(subElem.getAttribute("code"))) {
                                    String name = subElem.getTextContent();
                                    updateFrequency(frequencies, name);
                                }
                            }
                        }
                    }
                }
            }

            printSorted(frequencies, choice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateFrequency(List<NameFrequency> frequencies, String name) {
        for (NameFrequency nameFrequency : frequencies) {
            if (nameFrequency.getName().equals(name)) {
                nameFrequency.incrementFrequency();
                return;
            }
        }
        frequencies.add(new NameFrequency(name, 1));
    }

    private static void printSorted(List<NameFrequency> frequencies, int choice) {

        if(choice != 0) {
            switch (choice) {
                case 1: frequenciesAsc(frequencies);break;
                case 2: frequenciesDesc(frequencies);break;
                case 3: namesAsc(frequencies);break;
                case 4: namesDesc(frequencies);break;
            }
        }

        for (NameFrequency nameFrequency : frequencies) {
            System.out.println(nameFrequency);
        }
    }

    private static void namesDesc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getName().compareTo(frequencies.get(maxIndex).getName()) > 0) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(maxIndex));
                frequencies.set(maxIndex, temp);
            }
        }
    }

    private static void namesAsc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getName().compareTo(frequencies.get(minIndex).getName()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(minIndex));
                frequencies.set(minIndex, temp);
            }
        }
    }

    private static void frequenciesDesc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getFrequency() > frequencies.get(maxIndex).getFrequency()) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(maxIndex));
                frequencies.set(maxIndex, temp);
            }
        }
    }

    private static void frequenciesAsc(List<NameFrequency> frequencies) {
        for (int i = 0; i < frequencies.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < frequencies.size(); j++) {
                if (frequencies.get(j).getFrequency() < frequencies.get(minIndex).getFrequency()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                NameFrequency temp = frequencies.get(i);
                frequencies.set(i, frequencies.get(minIndex));
                frequencies.set(minIndex, temp);
            }
        }
    }
}
