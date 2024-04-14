package peggame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Project1Main
{
    public static void main(String[] args)
    {
        Scanner in= new Scanner(System.in);  //Scanner class
        System.out.println("Enter Board Size: ");
        int size= in.nextInt();
        textreader tr= new textreader();  //object creation for textreader class\
        SqaurePeggame game= tr.Textreaddisplay();
        //SqaurePeggame ob= new SqaurePeggame(size, null);
        GameState gstate = game.getGameState();
        System.out.println("Present Game State: "+gstate);
        SqaurePeggame.playgame(game, gstate,size);
    }
}
