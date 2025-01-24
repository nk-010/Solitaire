package solitaire;

import java.util.ArrayList;
import java.util.List;

public class Tableu {

    public static List<List<Card>> lanes;

    public static void intializeTableu(){
        int i, j;

        lanes = new ArrayList<>(); 
        
        for(i=0; i<7; i++){
            lanes.add(new ArrayList<>());
        }

        for(i=0; i<7; i++){
            for(j=0; j<=i; j++){
                Card card= Deck.dealCard();

                if (card == null) {
                    break; 
                }

                if(i==j){
                    card.flip();
                }
                lanes.get(i).add(card);
            }
        }
    }

    
}

