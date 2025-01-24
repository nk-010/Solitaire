package solitaire;


public class Card {
    private String rank;
    private String suit;
    private Boolean faceUp;

    public Card(String suit, String rank){
        this.suit= suit;
        this.rank= rank;
        this.faceUp = false;
    }

    public String getRank(){
        return rank;
    }

    public String getSuit(){
        return suit;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        this.faceUp = !this.faceUp;
    }

    public  String cardString(){
        return this.rank+""+this.suit ;
    }
}
