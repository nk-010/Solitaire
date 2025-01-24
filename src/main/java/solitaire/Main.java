package solitaire;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        String opt;

        System.out.println("Press Enter to start the game console");
        opt= scn.nextLine();

        if (opt.isEmpty()){

            do{
                System.out.println("...Game Console...");
                System.out.println("s To START/RESUME the game");
                System.out.println("e To END the game");
                opt= scn.nextLine();

                switch(opt){

                    case "s":
                    case "S": 
                            System.out.println("The game has started");
                            Game.play_game();
                            
                            //Deck.show_deck();
                            break;

                    case "e":
                    case "E":
                            System.out.println("Ending game...");
                            break;

                    default: 
                            System.out.println("Invalid Command");
                            System.out.println("Please press enter or q to quit");
                            break;
                }
            }while(!opt.equalsIgnoreCase("e"));
        }
    }
}