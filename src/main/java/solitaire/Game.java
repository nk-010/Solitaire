package solitaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Game {

    private static int point=0;

    public static void intializeGame(){
        Deck.intializeDeck();
        Tableu.intializeTableu();
        Waste.initializeWastePile();
    }

    public static void intializeGameTest(){
        Deck.intializeDeckTest();
        Foundation.foundationTest();
    }
    
    public static void play_game(){
        System.out.println();

        Game.intializeGame();
        //Game.intializeGameTest();   //to test the game end

        Scanner scn= new Scanner(System.in);
        String opt;
        String result="OK";

        do{
            Game.showGame(result);
            //Game.showGameTest(result); //to test game end
            opt= scn.nextLine();
            opt.trim();
            result= gameMoves(opt);
            System.out.println();

            Boolean end= gameEnd();

            if(end){
                System.out.println("Congrats, you have completed the game!!!");
                System.out.println("");
                System.out.println("Going to game console...");
                return ;
            }

        }while(!opt.equalsIgnoreCase("q"));
        
        System.out.println("Going to game console...");
    }

    public static void showGame(String result){

        int i;
        
        System.out.println("Waste Pile "+ "               "+"Foundation(H)  "+ "Foundation(D)  "+"Foundation(S)  "+ "Foundation(C)  " );
        System.out.println(Color.setColor(Waste.wastePeek()) + "                           "+ Foundation.fPeek(Foundation.fh)+"            "+ Color.setColor(Foundation.fPeek(Foundation.fd))+"           "+ Color.setColor(Foundation.fPeek(Foundation.fs))+"           "+Color.setColor(Foundation.fPeek(Foundation.fc)) );
        System.out.println("("+ Waste.waste.size()+ ") cards" + "                "+ "("+ Foundation.fh.size()+ ") cards" +"    "+ "("+ Foundation.fd.size()+ ") cards"+"    "+ "("+ Foundation.fs.size()+ ") cards"+"    "+ "("+ Foundation.fc.size()+ ") cards" );
        System.out.println("");

        for (i = 0; i < 7; i++) {
            System.out.print("Column " + (i + 1) + ": "); 
            List<Card> lane = Tableu.lanes.get(i);
            
            for (Card c : lane) {
                if(c.isFaceUp())
                    System.out.print(Color.setColor(c.cardString())+" ");

                else
                System.out.print("[]"+" ");
            }
            
            System.out.println(); 
        }

        System.out.println();

        System.out.println("Score: "+ point);
        System.out.println("Move status: "+ result);
        System.out.print("Move: ");         
    }

    public static void showGameTest(String result){
        
        System.out.println("Waste Pile "+ "               "+"Foundation(H)  "+ "Foundation(D)  "+"Foundation(S)  "+ "Foundation(C)  " );
        System.out.println(Color.setColor(Waste.wastePeek()) + "                           "+ Color.setColor(Foundation.fPeek(Foundation.fh))+"            "+ Color.setColor(Foundation.fPeek(Foundation.fd))+"           "+ Color.setColor(Foundation.fPeek(Foundation.fs))+"           "+Color.setColor(Foundation.fPeek(Foundation.fc)) );
        System.out.println("("+ Waste.waste.size()+ ") cards" + "                "+ "("+ Foundation.fh.size()+ ") cards" +"    "+ "("+ Foundation.fd.size()+ ") cards"+"    "+ "("+ Foundation.fs.size()+ ") cards"+"    "+ "("+ Foundation.fc.size()+ ") cards" );
        System.out.println("");

        System.out.println();

        System.out.println("Score: "+ point);
        System.out.println("Move status: "+ result);
        System.out.print("Move: ");         
    }


    public static String gameMoves(String opt){
        if(opt.length()>3 || opt.length()<1)
            return "Invalid";

        ArrayList<String> ranks= new ArrayList<>(Arrays.asList("K", "Q", "J", "10","9","8","7","6","5","4","3","2","A"));
        String result="";
        
        if(opt.length()==3){
            result= check3(opt, ranks);
        }

        if(opt.length()==2){

            Boolean first= Character.isDigit(opt.charAt(0));
            Boolean second= Character.isDigit(opt.charAt(1));
            if(first && second){
                result= check2Int(opt, ranks);
            }

            else
                result= check2(opt, ranks);
            
        }
        
        if(opt.length()==1){
            result= check1(opt);
        }
        
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Boolean gameEnd(){

        ArrayList<String> ranks= new ArrayList<>(Arrays.asList("K", "Q", "J", "10","9","8","7","6","5","4","3","2","A"));

        Stack<Card> fhcopy = (Stack<Card>) Foundation.fh.clone();
        Stack<Card> fdcopy = (Stack<Card>) Foundation.fd.clone();
        Stack<Card> fscopy = (Stack<Card>) Foundation.fs.clone();
        Stack<Card> fccopy = (Stack<Card>) Foundation.fc.clone();

        int flag=1;

        int i;

        ArrayList <Stack<Card>> fsuits = new ArrayList<>(Arrays.asList(fhcopy,fdcopy,fscopy,fccopy));

        for(Stack<Card> f: fsuits){
            i=0;

            while(!f.isEmpty()){
                Card c= f.pop();

                if(c.getRank().equalsIgnoreCase(ranks.get(i))){
                    i++;
                }
            }
    
            if(ranks.size()!=i){
                flag=0;
            }

        }

        if(flag==1)
            return true;

        return false;
    }

    public static String check1(String opt){
        if(opt.equalsIgnoreCase("p")){
            if(Waste.checkWaste()){
                return "Invalid";
            }

            if (Waste.waste.size() > 1) {
                Card firstCard = Waste.waste.remove(0); 
                Waste.waste.add(firstCard); 
            }
            return "OK";
        }
        
        return "Invalid";
    }

    public static String check2(String opt, ArrayList<String> ranks){
        
        String m1= String.valueOf(opt.charAt(0));
        String m2= String.valueOf(opt.charAt(1));

        //pile to lane
        if(m1.equalsIgnoreCase("p")){
            if(Waste.checkWaste()){
                return "Invalid";
            }
            
            if(Character.isDigit(opt.charAt(1))){
                Integer m2int= Integer.parseInt(m2);
                m2int-=1;
                if(m2int>7 || m2int<0){
                    return "Invalid";
                }

                List<Card> lane= Tableu.lanes.get(m2int);

                if( lane.isEmpty()){
                    if(Waste.waste.get(0).getRank().equalsIgnoreCase("k")){
                        Card c= Waste.waste.remove(0);
                        lane.add(c);
                        c.flip();
                        return "OK";
                    } else return "Invalid";
                }

                else if(!(lane.isEmpty())){
                    Card Card1= Waste.waste.get(0);
                    Card Card2= lane.get(lane.size()-1);

                    //check red cards
                    if(Card1.getSuit().equalsIgnoreCase("H") || Card1.getSuit().equalsIgnoreCase("D")){
                        if(Card2.getSuit().equalsIgnoreCase("S") || Card2.getSuit().equalsIgnoreCase("C")){
                            
                            int index;
                            index = ranks.indexOf(Card1.getRank());
                            if(Card2.getRank().equals(ranks.get(index-1))){
                                Waste.waste.remove(Card1);    
                                lane.add(Card1);
                                Card1.flip();
                                return "OK";
                            }
                            else 
                                return "Invalid";
    
                        }
                        else 
                        return "Invalid";
                    }
                    
                    //check black cards
                    if(Card1.getSuit().equalsIgnoreCase("S") || Card1.getSuit().equalsIgnoreCase("C")){
                        if(Card2.getSuit().equalsIgnoreCase("H") || Card2.getSuit().equalsIgnoreCase("D")){
                            
                            int index;
                            index = ranks.indexOf(Card1.getRank());
                            if(Card2.getRank().equals(ranks.get(index-1))){
                                Waste.waste.remove(Card1);    
                                lane.add(Card1);
                                Card1.flip();
                                return "OK";
                            }
                            else 
                                return "Invalid";
    
                        }
                        else 
                        return "Invalid";
                    }
                }
            }
            // pile to foundation
            else if(m2.equalsIgnoreCase("h") || m2.equalsIgnoreCase("d") || m2.equalsIgnoreCase("s") || m2.equalsIgnoreCase("c")){
                Card c= Waste.waste.get(0);
                
                if(m2.equalsIgnoreCase("h") && c.getSuit().equalsIgnoreCase("h")){
                    //check hearts
                    if(Foundation.checkf(Foundation.fh)){
                        if(c.getRank().equalsIgnoreCase("a")){
                            Waste.waste.remove(0);
                            Foundation.fh.push(c);
                            point+=10;
                            return "OK";
                        } else return "Invalid";

                    }else{
                        Integer index= ranks.indexOf(c.getRank());
                        if(Foundation.fh.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                            Waste.waste.remove(0);
                            Foundation.fh.push(c);
                            point+=10;
                            return "OK";
                        }else return "Invalid";
                    }
                }

                else if(m2.equalsIgnoreCase("d") && c.getSuit().equalsIgnoreCase("d")){
                    if(Foundation.checkf(Foundation.fd)){
                        if(c.getRank().equalsIgnoreCase("a")){
                            Waste.waste.remove(0);
                            Foundation.fd.push(c);
                            point+=10;
                            return "OK";
                        } else return "Invalid";

                    }else{
                        Integer index= ranks.indexOf(c.getRank());
                        if(Foundation.fd.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                            Waste.waste.remove(0);
                            Foundation.fd.push(c);
                            point+=10;
                            return "OK";
                        }else return "Invalid";
                    }
                }

                else if(m2.equalsIgnoreCase("s") && c.getSuit().equalsIgnoreCase("s")){
                    if(Foundation.checkf(Foundation.fs)){
                        if(c.getRank().equalsIgnoreCase("a")){
                            Waste.waste.remove(0);
                            Foundation.fs.push(c);
                            point+=10;
                            return "OK";
                        } else return "Invalid";

                    }else{
                        Integer index= ranks.indexOf(c.getRank());
                        if(Foundation.fs.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                            Waste.waste.remove(0);
                            Foundation.fs.push(c);
                            point+=10;
                            return "OK";
                        }else return "Invalid";
                    }
                }

                else if(m2.equalsIgnoreCase("c") && c.getSuit().equalsIgnoreCase("c")){
                    if(Foundation.checkf(Foundation.fc)){
                        if(c.getRank().equalsIgnoreCase("a")){
                            Waste.waste.remove(0);
                            Foundation.fc.push(c);
                            point+=10;
                            return "OK";
                        } else return "Invalid";

                    }else{
                        Integer index= ranks.indexOf(c.getRank());
                        if(Foundation.fc.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                            Waste.waste.remove(0);
                            Foundation.fc.push(c);
                            point+=10;
                            return "OK";
                        }else return "Invalid";
                    }
                }
            }else return "Invalid";
        }

        //lane to foundation
        else if (Character.isDigit(opt.charAt(0))) {
            if(m2.equalsIgnoreCase("h") || m2.equalsIgnoreCase("d") || m2.equalsIgnoreCase("s")||m2.equalsIgnoreCase("c")){
                Integer m1int= Integer.parseInt(m1);

                m1int-=1;
                if(m1int>7 || m1int<0){
                    return "Invalid";
                }

                List<Card> lane= Tableu.lanes.get(m1int);
                if(lane.size()>0){
                    Card c = lane.get(lane.size()-1);
                    //check hearts
                    if(m2.equalsIgnoreCase("h") && c.getSuit().equalsIgnoreCase("h")){
                        if(Foundation.checkf(Foundation.fh)){
                            if(c.getRank().equalsIgnoreCase("a")){
                                lane.remove(lane.size()-1);
                                Foundation.fh.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            } else return "Invalid";

                        }else{
                            Integer index= ranks.indexOf(c.getRank());
                            if(Foundation.fh.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                                lane.remove(lane.size()-1);
                                Foundation.fh.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            }else return "Invalid";
                        }
                    }
                    

                    //check daimonds
                    else if(m2.equalsIgnoreCase("d") && c.getSuit().equalsIgnoreCase("d")){
                        if(Foundation.checkf(Foundation.fd)){
                            if(c.getRank().equalsIgnoreCase("a")){
                                lane.remove(lane.size()-1);
                                Foundation.fd.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            } else return "Invalid";

                        }else{
                            Integer index= ranks.indexOf(c.getRank());
                            if(Foundation.fd.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                                lane.remove(lane.size()-1);
                                Foundation.fd.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            }else return "Invalid";
                        }
                    }

                    //check spade
                    else if(m2.equalsIgnoreCase("s") && c.getSuit().equalsIgnoreCase("s")){
                        if(Foundation.checkf(Foundation.fs)){
                            if(c.getRank().equalsIgnoreCase("a")){
                                lane.remove(lane.size()-1);
                                Foundation.fs.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            } else return "Invalid";

                        }else{
                            Integer index= ranks.indexOf(c.getRank());
                            if(Foundation.fs.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                                lane.remove(lane.size()-1);
                                Foundation.fs.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            }else return "Invalid";
                        }
                    }

                    //check club
                    else if(m2.equalsIgnoreCase("c") && c.getSuit().equalsIgnoreCase("c")){
                        if(Foundation.checkf(Foundation.fc)){
                            if(c.getRank().equalsIgnoreCase("a")){
                                lane.remove(lane.size()-1);
                                Foundation.fc.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            } else return "Invalid";

                        }else{
                            Integer index= ranks.indexOf(c.getRank());
                            if(Foundation.fc.peek().getRank().equalsIgnoreCase(ranks.get(index+1))){
                                lane.remove(lane.size()-1);
                                Foundation.fc.push(c);
                                point+=20;
                                if(lane.size()>=1){
                                    lane.get(lane.size()-1).flip();
                                }
                                return "OK";
                            }else return "Invalid";
                        }
                    }
                    else return "Invalid";

                }
                else return "Invalid";
            }
            else return "Invalid";

        } else {
            
        }

        

        return "";
    }
    

    public static String check2Int(String opt, ArrayList<String> ranks){

            Integer m1 = Integer.parseInt(String.valueOf(opt.charAt(0)));
            Integer m2 = Integer.parseInt(String.valueOf(opt.charAt(1)));

            m1-=1;
            m2-=1;

            if(m1>7 || m1<0)
                return "Invalid";

            if(m2>7 || m2<0)
                return "Invalid";

            List<Card> lane1= Tableu.lanes.get(m1);

            if(lane1.size()>=1){

                Card Card1= lane1.get(lane1.size()-1);
                List<Card> lane2= Tableu.lanes.get(m2);

                if(Card1.getRank().equalsIgnoreCase("k")){
                    if(lane2.size()!=0){
                        return "Invalid";
                    }
                    else{
                        lane1.remove(Card1);
                        lane2.add(Card1);
                        return "OK";
                    }
                }

                Card Card2= lane2.get(lane2.size()-1);

                //check red cards
                if(Card1.getSuit().equalsIgnoreCase("H") || Card1.getSuit().equalsIgnoreCase("D")){
                    if(Card2.getSuit().equalsIgnoreCase("S") || Card2.getSuit().equalsIgnoreCase("C")){
                        
                        int index;
                        index = ranks.indexOf(Card1.getRank());

                        if(Card2.getRank().equals(ranks.get(index-1))){
                            lane1.remove(Card1);    
                            lane2.add(Card1);
                                

                            if(lane1.size()>0){
                                lane1.get(lane1.size()-1).flip();
                            }
                            point+= 5;
                            return "OK";
                        }
                        else 
                            return "Invalid";

                    }
                    else 
                    return "Invalid";
                }
                
                //check black cards
                if(Card1.getSuit().equalsIgnoreCase("S") || Card1.getSuit().equalsIgnoreCase("C")){
                    if(Card2.getSuit().equalsIgnoreCase("H") || Card2.getSuit().equalsIgnoreCase("D")){
                        
                        int index;
                        index = ranks.indexOf(Card1.getRank());
                        if(Card2.getRank().equals(ranks.get(index-1))){
                            lane1.remove(Card1);    
                            lane2.add(Card1);
                                

                            if(lane1.size()>0){
                                lane1.get(lane1.size()-1).flip();
                            }
                            point+= 5;
                            return "OK";
                        }
                        else 
                            return "Invalid";

                    }
                    else 
                    return "Invalid";
                }

            }
            else
                return "Invalid";
           
        return "";
    }
    

    public static String check3(String opt, ArrayList<String> ranks){

        int i,j;

        try{
            Integer m1 = Integer.parseInt(String.valueOf(opt.charAt(0)));
            Integer m2 = Integer.parseInt(String.valueOf(opt.charAt(1)));
            Integer m3 = Integer.parseInt(String.valueOf(opt.charAt(2)));

            m1-=1;
            m2-=1;

            if(m1>7 || m1<0)
                return "Invalid";

            if(m2>7 || m2<0)
                return "Invalid";

            List<Card> lane1= Tableu.lanes.get(m1);
            List<Card> selected= new ArrayList<>();

            if(lane1.size()>=m3){
                for(i= lane1.size()-1, j=0; j<m3; j++, i--){
                    if(!(lane1.get(i).isFaceUp())){
                        return "Invalid";
                    }
                    selected.add(lane1.get(i));
                }

                Collections.reverse(selected);
                Card firstCard= selected.get((0));

                List<Card> lane2= Tableu.lanes.get(m2);
                Card lastCard= lane2.get(lane2.size()-1);

                //check red cards
                if(firstCard.getSuit().equalsIgnoreCase("H") || firstCard.getSuit().equalsIgnoreCase("D")){
                    if(lastCard.getSuit().equalsIgnoreCase("S") || lastCard.getSuit().equalsIgnoreCase("C")){
                        if(firstCard.getRank().equalsIgnoreCase("K")){
                            if(lane2.size()==0){
                                for(Card c: selected){
                                    lane1.remove(c);
                                    lane2.add(c);
                                }
                            }
                            else
                            return "Invalid";
                        }

                        int index;
                        index = ranks.indexOf(firstCard.getRank());
                        if(lastCard.getRank().equals(ranks.get(index-1))){
                            for(Card c: selected){
                                lane1.remove(c);
                                lane2.add(c);
                            }

                            if(lane1.size()>0){
                                lane1.get(lane1.size()-1).flip();
                            }
                            point+= m3*5;
                            return "OK";
                        }
                        else 
                            return "Invalid";

                    }
                    else 
                    return "Invalid";
                } 
                
                //check black cards
                if(firstCard.getSuit().equalsIgnoreCase("S") || firstCard.getSuit().equalsIgnoreCase("C")){
                    if(lastCard.getSuit().equalsIgnoreCase("D") || lastCard.getSuit().equalsIgnoreCase("H")){
                        if(firstCard.getRank().equalsIgnoreCase("K")){
                            if(lane2.size()==0){
                                for(Card c: selected){
                                    lane1.remove(c);
                                    lane2.add(c);
                                }
                            }
                            else
                            return "Invalid";
                        }

                        int index;
                        index = ranks.indexOf(firstCard.getRank());
                        if(lastCard.getRank().equals(ranks.get(index-1))){
                            for(Card c: selected){
                                lane1.remove(c);
                                lane2.add(c);
                            }

                            if(lane1.size()>0){
                                lane1.get(lane1.size()-1).flip();
                            }
                        }
                        else 
                            return "Invalid";
                        
                        point+= m3*5;
                        return "OK";
                    }
                    else 
                    return "Invalid";
                }
            }
            else
                return "Invalid";

            }
            catch(Error er){
                return "Invalid";
            }

        return "";
    }
}
