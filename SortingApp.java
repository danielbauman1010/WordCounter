/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortingapp;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.lang.Character.isLetter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.xml.stream.events.Characters;

/**
 *
 * @author innabauman
 */
public class SortingApp {
    private String filename;
    public SortingApp(String filename) {
        this.filename = filename;
    }
    private Scanner getTextFileScanner(String filename){    //scanner for reading the text file
        Scanner scan = null;
        try{
            scan = new Scanner(new BufferedReader(new FileReader(filename)));
        } catch (FileNotFoundException ex) {
            System.out.println("Cannot process commands from "+ filename + " because: "+ ex );
        }
        return scan; 
    }
    
    private boolean goodWord(String word) {     //check if a word is a valid "good" word
        for(Character c: word.toCharArray()) {
            if(!isLetter(c)) {  //if the character is in a-z or A-Z
                return false;
            }
        }
        return true;
    }
    private List<String> getWords() { //scan for all of the words
        List<String> words = new ArrayList();
        Scanner scan = this.getTextFileScanner(this.filename);       
        while(scan.hasNext()) {
            words.add(scan.next());
        }
        return words;
    }
    private List<String> getGoodWords() { //scan for all of the "good" words
        List<String> words = this.getWords();
        List<String> goodWords = words.stream().filter(w -> goodWord(w)).collect(toList()); 
        return goodWords;
    }
    private Map<String, Integer> getUniqueWords() { //scan for all of the unique "good" words
        List<String> goodWords = this.getGoodWords();
        Map<String, Integer> uniqueGoodWords = new HashMap();                
        goodWords.forEach(w -> {
            if(uniqueGoodWords.containsKey(w)) {
                uniqueGoodWords.replace(w, (uniqueGoodWords.get(w)+1));
            } else {
                uniqueGoodWords.put(w, 1);
            }
        });
        return uniqueGoodWords;
    }
    private void processFile() {
        System.out.println("Proccesing words from file Words2.txt");
        int numberOfWords = this.getWords().size();
        int numberOfGoodWords = this.getGoodWords().size();
        Map<String, Integer> UniqueWords = this.getUniqueWords();
        System.out.println("Done Processing " + this.filename);
        System.out.println("");
        System.out.println("File " + this.filename + " contains " + numberOfWords + " scanner words " +
                numberOfGoodWords + " total words and " + UniqueWords.size() + " unique words: ");
        System.out.println("");
        UniqueWords.forEach((W,N) -> {
            System.out.println(W + "\t" + N);
        });
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SortingApp s = new SortingApp("Words2.txt");
        s.processFile();
        
    }
    
}
