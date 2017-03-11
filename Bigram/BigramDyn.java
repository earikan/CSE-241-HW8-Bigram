/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eda arikan
 * @param <T>
 */
public class BigramDyn<T> implements Bigram<T> {

    private T[] bigramDynArray; //all values in file holding here, seperated by whitespace
    private int bigramDynArraySize;
    private int type;
    
    
    
	//constructor
    public BigramDyn() {
        type = 0;
    }



	//datatype constructor
    public BigramDyn(int datatype) {
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

            bigramDynArraySize = parsedContent.length;
            bigramDynArray = (T[]) new Object[bigramDynArraySize];

            //casting by given value
            for (int i = 0; i < parsedContent.length; ++i) {
                try {
                    if (type == 1) {
                        bigramDynArray[i] = (T) Integer.valueOf(parsedContent[i]);
                    } else if (type == 2) {
                        bigramDynArray[i] = (T) String.valueOf(parsedContent[i]);
                    } else if (type == 3) {
                        bigramDynArray[i] = (T) Double.valueOf(parsedContent[i]);
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



	//total bigrams
    @Override
    public int numGrams() {
        return bigramDynArraySize - 1;
    }



	//count of given bigram value
    @Override
    public int numOfGrams(T firstValue, T secondValue) {

        int counter = 0;
        for (int i = 0; i < bigramDynArraySize - 1; ++i) {
            if ((bigramDynArray[i].equals(firstValue)) && (bigramDynArray[i + 1].equals(secondValue))) {
                ++counter;
            }
        }

        return counter;
    }



	//toString function returning bigrams counts descending order
	//Used 2 lists, first list holds [i]th value, second list holds [i+1]th value
	//So firstlist[0] and secondlist[0] is a pair, and go on. (same index)
    @Override 
    public String toString() {

        T[] firstList = (T[]) new Object[0];
        T[] secondList = (T[]) new Object[0];
        String returnString = "";

        for (int i = 0; i < bigramDynArraySize - 1; ++i) {

            int flag = 0;

            if ((firstList.length == 0)) { //if lenght is zero, allocate and put values
            
                firstList = createNewTTypeArray(firstList);
                secondList = createNewTTypeArray(secondList);
                firstList[firstList.length - 1] = bigramDynArray[i];
                secondList[secondList.length - 1] = bigramDynArray[i + 1];
                
            } else {
            
                for (int j = 0; j < firstList.length; ++j) {
                    if ((firstList[j].equals(bigramDynArray[i]) && secondList[j].equals(bigramDynArray[i + 1]))) {
                        flag = 1;
                    }
                }
                
                if ((flag == 0)) {
                    firstList = createNewTTypeArray(firstList);
                    secondList = createNewTTypeArray(secondList);
                    firstList[firstList.length - 1] = bigramDynArray[i];
                    secondList[secondList.length - 1] = bigramDynArray[i + 1];
                }
                
            }
        }

		//counting all bigrams occurence
        int numOfGramsList[] = new int[firstList.length];

        for (int i = 0; i < firstList.length; ++i) {
            numOfGramsList[i] = numOfGrams(firstList[i], secondList[i]);
        }

		//sorting by numOfGramsList
        bubbleSort(numOfGramsList, firstList, secondList);
        
        //filling return string
        returnString += "Descending order with BigramDyn\n";
        for (int i = 0; i < firstList.length; ++i) {

            returnString += firstList[i] + "," + secondList[i] + " : ";
            returnString += numOfGrams(firstList[i], secondList[i]) + "\n";
            numOfGramsList[i] = numOfGrams(firstList[i], secondList[i]);
        }
        
        returnString += "\n";
        
        return returnString;
    }



	//all lists sorting by numOfGramsList using bubble sort
    private void bubbleSort(int[] numOfGramsList, T[] firstList, T[] secondList) {

        int n;
        n = numOfGramsList.length;
        int temp;
        T tempT;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (numOfGramsList[j - 1] < numOfGramsList[j]) {

                    temp = numOfGramsList[j - 1];
                    numOfGramsList[j - 1] = numOfGramsList[j];
                    numOfGramsList[j] = temp;

                    tempT = firstList[j - 1];
                    firstList[j - 1] = firstList[j];
                    firstList[j] = tempT;

                    tempT = secondList[j - 1];
                    secondList[j - 1] = secondList[j];
                    secondList[j] = tempT;
                }
            }
        }
    }



	//for create new int array
    private int[] createNewIntArray(int[] oldArray) {

        int[] newArray = new int[(oldArray.length) + 1];

        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }


	//for create new T type array
    private T[] createNewTTypeArray(T[] oldArray) {
    
        T[] newArray = (T[]) new Object[(oldArray.length) + 1];
        
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }

        return newArray;
    }

}
