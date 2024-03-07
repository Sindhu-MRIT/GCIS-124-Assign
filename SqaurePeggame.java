package peggame;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class SqaurePeggame implements PegGame
{
    private int size1;
    private boolean[][] pboard;
    private GameState gamestate;

        public SqaurePeggame(int size1, boolean[][] pegboard)
        {
        this.size1= size1;
    
        this.pboard= new boolean[size1][size1];  //add pegs to the board
        for(int i=0;i<size1;i++)
            for(int j=0;j<size1;j++)
                this.pboard[i][j]= pegboard[i][j];
        this.gamestate= GameState.NOT_STARTED;
        }

        /*Let's include the Collection<Move> getPossibleMoves(),
         * List<Move> validity(), GameSate getGameState(), makeMove() and toString() and describe the its
         * attributes
         */
    
        @Override
        public Collection<Move> getPossibleMoves()  //collection of the attributes to specify
        {
        List<Move> possiblemovement= new ArrayList<>();
        for(int i=0;i<size1;i++)  //considering the valiid moves
            for(int j=0;j<size1;j++)
                if(pboard[i][j])
                    possiblemovement.addAll(validity(new Location(i, j)));
        return possiblemovement;
        }
    
        private List<Move> validity(Location loc)
        {
        List<Move> mov = new ArrayList<>();
        int rows= loc.getRow();
        int cols= loc.getCol();
        int jump_col= 0;
        int jump_row= 0;
    
            /*checking for valid move towards al directions
            * towards right, left, front and down*/
    
        jump_col= cols+2;  //Checking for possibilty on right side
        if(ifvalid(rows, cols, rows, jump_col))
            mov.add(new Move(loc, new Location(rows, jump_col)));
        
        jump_col= cols-2;  //checking for possibilty on left side
        if (ifvalid(rows, cols, rows, jump_col))
            mov.add(new Move(loc, new Location(rows, jump_col)));

        jump_row= rows-2;  //checking for possibilty on the top side
        if (ifvalid(rows, cols, jump_row, cols))
            mov.add(new Move(loc, new Location(jump_row, cols)));

        jump_row= rows+2;  //checking for posisibilty on the bottom side
        if (ifvalid(rows, cols, jump_row, cols))
            mov.add(new Move(loc, new Location(jump_row, cols)));

        return mov;  //returning it now
        }
    
        private boolean ifvalid(int jump_row, int jump_col, int to_row,int to_col)
        {
        /* For invalidity checking:
        returnin g invalid if jump row and column are negative
        or when jum row and column size is exceeded
        */
        if(jump_row<0||jump_col<0||jump_row>=size1||jump_col>=size1)
            return false;  //return invalid
        int midr= (to_row+jump_row)/2;  //consider middle row and column that will be jumped over
        int midc= (to_col+jump_col)/2;
        /*checking if a peg exists on the middle row jump over,
        * middle column jumped over or not so as to remove that peg,
        * or if no peg exists at all to jump over then invalid move is returned*/
        if(!pboard[to_row][to_col] || !pboard[midr][midc] || pboard[jump_row][jump_col])
            return false;  // move not possible
        return true;
        }
    
        @Override
        public GameState getGameState()
        {
        return gamestate;  //returning game state, it is a getter
        }
    
        @Override
        public void makeMove(Move move) throws PegGameException
        {
        Location from= move.getFrom();  //getting from and to values
        Location to= move.getTo();
        int from_row= from.getRow();
        int to_row= to.getRow();
        int from_col= from.getCol();
        int to_col= to.getCol();

        int jump_row= (from_row+to_row)/2;
        int jump_col= (from_col+to_col)/2;

        if(!ifvalid(jump_row, jump_col, to_row, to_col))
        {
            throw new PegGameException("Invalid Jump");
        }
        pboard[from_row][from_col]= false;  //assigning pegs: 'o' and empty:'-' spaces to the board
        pboard[to_row][to_col]= true;
        pboard[jump_row][jump_col]= false;

        if(gamestate==GameState.NOT_STARTED)
            gamestate= GameState.IN_PROGRESS;  //assigning to th IN_PROGRESS

        if(getPossibleMoves().isEmpty())
        {
            int pegsleft= remainingpegs();
            if(pegsleft==1)
                gamestate= GameState.WON;  //if one peg left print won  else stalemate
            else
                gamestate= GameState.STALEMATE;
        }
        }

        private int remainingpegs()  //method to keep count of remaining pegs and returns remaing pegs left
        {
        int peg_count= 0;
        for(int i=0;i<pboard.length;i++)
            for (int j=0;j<pboard[i].length;j++)
                if (pboard[i][j]==true)  //checking if its true  in the pegboard at i j position which means that peg is present
                    peg_count++;
        return peg_count;
        }

        public static void playgame(PegGame game, GameState gstate, int size)
        {
        int r1;int r2;int c1;int c2;  //initializing
        if(gstate==GameState.NOT_STARTED)
        {
        BufferedReader read = new BufferedReader(System.console().reader());
        while(gstate==GameState.IN_PROGRESS||gstate== GameState.NOT_STARTED)
        {
        System.out.println("Enter Yes to proceed with the game or No to Exit");
        try
        {
        String proceed= read.readLine();
        if(proceed.equalsIgnoreCase("No"))
        {
                System.out.println("Bye");
                break;
            }
        else if(proceed.equalsIgnoreCase("Yes"))
        {
        Scanner in= new Scanner(System.in);  //Scanner class
        System.out.println("Enter the move you want to make as: row1 col1 row2 col2");
        String movementrecord= in.nextLine();
        String[] token = movementrecord.split(" ");
        //textreader tr= new textreader();
        r1 = Integer.parseInt(token[0]);  //taking the rows and columns
        c1 = Integer.parseInt(token[1]);
        r2 = Integer.parseInt(token[2]);
        c2 = Integer.parseInt(token[3]);
        if(r1>=size||r2>= size||c1>= size||c2>= size||r1<0||r2<0||c1<0||c2<0)
            System.out.println("Invalid input");  //if rows and columns are negative or out of bound prints invalid entry
        else
        try
        {
        game.makeMove(new Move(new Location(r1, c1), new Location(r2, c2)));
        gstate = game.getGameState();  //to display updated gamestate
        System.out.println("Present Board: ");
                System.out.println(game);
        }
        catch (PegGameException e)
        {
            System.out.println("Invalid Move: "+e.getMessage());  //eror display
        }
        }
        }
        catch(Exception e)
        {
            System.out.println("Not Accesible " + e.getMessage());
        }
    }

    if(gstate==GameState.WON)  //printing if won or stalemate
        System.out.println("Yeah!! Victory!");
    else if(gstate == GameState.STALEMATE)
        System.out.println("Game Over!");
        }
    else
        System.out.println("Different Game State: "+gstate);
    }
    @Override
    public String toString()
    {
    StringBuilder sb= new StringBuilder();
    for(int i=0;i<pboard.length;i++)
    {
        for (int j=0;j<pboard[i].length;j++)
            if(pboard[i][j]==true) 
                sb.append("o");
            else
                sb.append("-");
        sb.append("\n");
    }
    return sb.toString();
    }
}


