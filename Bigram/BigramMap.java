/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author eda arikan
 * @param <T>
 */
public class BigramMap<T> implements Bigram<T> {

    private Map<Object, T> bigramMap = new HashMap(); 
    private int type;


	//constructor
    public BigramMap() {
        type = 0;
    }



	//datatype constructor
    public BigramMap(int datatype) {
        type = datatype;
    }



	//read file
    @Override
    public void readFile(String filename) {

        String content = "";    //all file content holding in content variable
        String[] parsedContent; //file content seperated by whitespace character

        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(filename));

            String line; //reading a line from file and adding to content

            while ((line = br.readLine()) != null) {
                content += line;
            }

            //seperating by whitespace character and filling array
            parsedContent = content.split("\\s+");

            //casting by given value
            for (int i = 0; i < parsedContent.length; ++i) {
                try {
                    if (type == 1) {
                        bigramMap.put(i, (T) Integer.valueOf(parsedContent[i]));
                    } else if (type == 2) {
                        bigramMap.put(i, (T) String.valueOf(parsedContent[i]));
                    } else if (type == 3) {
                        bigramMap.put(i, (T) Double.valueOf(parsedContent[i]));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Exception: Different types in file or empty file.");
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            System.out.println("File error.");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                System.out.println("File error.");
            }
        }

    }




	//return total bigrams number
    @Override
    public int numGrams() {
        return bigramMap.size() - 1;
    }



	//return given bigram number in sequence
    @Override
    public int numOfGrams(T firstValue, T secondValue) {

        int counter = 0;
        for (int i = 0; i < bigramMap.size() - 1; ++i) {
            if (bigramMap.get(i).equals(firstValue) && bigramMap.get(i + 1).equals(secondValue)) {
                ++counter;
            }
        }

        return counter;
    }




	//toString all bigrams occurence
    @Override
    public String toString() {

        List<T> firstList = new ArrayList<T>();
        List<T> secondList = new ArrayList<T>();
        List<Integer> numOfGramsList = new ArrayList<Integer>();
        String returnString = "";

		//if next bigram pair doesnt exist in list, add them to lists
        for (int i = 0; i < bigramMap.size() - 1; ++i) {
            findInList(firstList, secondList, bigramMap.get(i), bigramMap.get(i + 1));
        }

        for (int i = 0; i < firstList.size(); ++i) {
            numOfGramsList.add(numOfGrams(firstList.get(i), secondList.get(i)));
        }

		//all lists sorting by numOfGramsList order
        bubbleSort(numOfGramsList, firstList, secondList);

        //returng string
        returnString += "Descending order with BigramMap\n";

        for (int i = 0; i < firstList.size(); ++i) {
            returnString += (firstList.get(i) + "," + secondList.get(i) + " : ");
            returnString += (numOfGrams(firstList.get(i), secondList.get(i)) + "\n");
            numOfGramsList.add(numOfGrams(firstList.get(i), secondList.get(i)));
        }
        
        returnString += "\n";
        
        return returnString;
    }




	//all bigrams in descending order, each pair must be unique
    private void findInList(List<T> firstList, List<T> secondList, T firstValue, T secondValue) {

        int flag = 0;

        for (int i = 0; i < firstList.size(); ++i) {
            if ((firstList.get(i).equals(firstValue) && secondList.get(i).equals(secondValue))) {
                flag = 1;
            }
        }

        if (flag == 0) {
            firstList.add(firstValue);
            secondList.add(secondValue);
        }
    }




	//sorting 
    private void bubbleSort(List<Integer> numOfGramsList, List<T> firstList, List<T> secondList) {

        int n = numOfGramsList.size();
        int temp = 0;
        T tempT;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (numOfGramsList.get(j - 1) < numOfGramsList.get(j)) {

                    temp = numOfGramsList.get(j - 1);
                    numOfGramsList.set(j - 1, numOfGramsList.get(j));
                    numOfGramsList.set(j, temp);

                    tempT = firstList.get(j - 1);
                    firstList.set(j - 1, firstList.get(j));
                    firstList.set(j, tempT);

                    tempT = secondList.get(j - 1);
                    secondList.set(j - 1, secondList.get(j));
                    secondList.set(j, tempT);
                }
            }
        }
    }

}
