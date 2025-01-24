package solitaire;

import java.util.*;

public class Deck {
    public static ArrayList<Card> cards= new ArrayList<>();

    enum suit{
        H("Heart"),
        D("Diamond"),
        S("Spade"),
        C("Club");

        public final String label;

        private suit(String label) {
            this.label = label;
            }

        public String get_label() {
            return label;
        }
    }

    enum rank{
        ace("A"),
        two("2"),
        three("3"),
        four("4"),
        five("5"),
        six("6"),
        seven("7"),
        eight("8"),
        nine("9"),
        ten("10"),
        jack("J"),
        queen("Q"),
        king("K");

        public final String label;

        private rank(String label) {
            this.label= label;
        }

        public String get_label() {
            return label;
        }
    } 

    public static void create_deck() {
        for (suit s: suit.values()){
            for(rank r: rank.values()){
                cards.add(new Card(s.name(),r.get_label()));
            }
        }
    }

    public static void show_deck() {
        for(Card c: cards){
           System.out.println(c.cardString());
        }
    }

    public static void randomize(){
        Collections.shuffle(cards);
    }


    public static Card dealCard(){
        return cardsEmpty()? null : cards.remove(cards.size() -1);
    }

    public static boolean cardsEmpty(){
        return cards.isEmpty();
    }

    public static void intializeDeck(){
        Deck.create_deck();
        Deck.randomize();
    }

    public static void intializeDeckTest(){
        Deck.create_deck();
    }
}