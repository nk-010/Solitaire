package solitaire;

public class Color {
    
    public static String setColor(String card){
        if(card.contains("H") || card.contains("D")){
            return("\u001B[31m"+card+"\u001B[0m");
        }else{
            return card;
        }
    }
}
