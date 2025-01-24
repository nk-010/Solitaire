package solitaire;

import java.util.ArrayList;

public class Waste {

    public static ArrayList<Card> waste= new ArrayList<>();
    
    public static void initializeWastePile(){
        while(! Deck.cardsEmpty()){
            waste.add(Deck.dealCard());
        }
    }

    public static String wastePeek(){
        return checkWaste()? "[]": waste.get(0).cardString();
    }

    public static Boolean checkWaste(){
        return waste.isEmpty();
    }
}
