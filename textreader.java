package peggame;

//import static SAMPLEPEG2.Project1Main.playgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class textreader extends Project1Main
{
    private int size;
    private boolean pegboard[][];
    
    public boolean[][] getPegboard()
    {   return pegboard;    }
    
    public SqaurePeggame Textread()
    {
        /*We will read the file input by the user and display it
         * on the CLI
         */
        try
        {
            Scanner in= new Scanner(System.in);  //Scanner class
            System.out.print("Enter the file name: ");
            String filen= in.nextLine();
            FileReader fr1= new FileReader(filen); //accessing file to be read in, if not exists, other file is created
            BufferedReader reader1= new BufferedReader(fr1);  //to write into that particular file
            String line1= "";
            int size = Integer.parseInt(reader1.readLine()); //reading line 1 and converting it's size to int and then reading other lines
            boolean[][] pegboard = new boolean[size][size];  //creating pegboard of that size
            System.out.println("Initial Game Board: ");
            for (int row=0;row<size;row++)
            {
                String line = reader1.readLine();  //reading rows
                for (int col=0;col<size;col++)
                {
                    if(line.charAt(col)=='o')
                    pegboard[row][col]= true;  //assigning true to pegboard array when the file's board has 'o'
                }
                if(line1 == null)  //until all the lines are printed
                    break;
                System.out.println(line);
            }
            reader1.close();
            fr1.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        SqaurePeggame game = new SqaurePeggame(size,pegboard);  //object creation to play the game
        return game;
    }
}