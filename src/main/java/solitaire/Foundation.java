package solitaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Foundation {
    public static Stack<Card> fh= new Stack<>();
    public static Stack<Card> fd= new Stack<>();
    public static Stack<Card> fs= new Stack<>();
    public static Stack<Card> fc= new Stack<>();


    public static String fPeek(Stack<Card> f){
        return checkf(f)? "[]": f.peek().cardString();
    }

    public static Boolean checkf(Stack<Card> f){
        return f.isEmpty();
    }

    public static void foundationTest(){
        Integer i,j;

        ArrayList <Stack<Card>> fsuits = new ArrayList<>(Arrays.asList(fh,fd,fs,fc));

            i=0;

            for(Stack<Card> f: fsuits){
                for(j=0; j<13; j++){
                    f.add(Deck.cards.get(i));
                    i++; 
                }
            }
    }

}
