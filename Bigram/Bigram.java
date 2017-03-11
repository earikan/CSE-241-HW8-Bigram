/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author eda arikan
 */
public interface Bigram <T> {
    
    void readFile(String filename);
    int numGrams();
    int numOfGrams(T firstValue, T secondValue);
    @Override
    public String toString();
    
}
