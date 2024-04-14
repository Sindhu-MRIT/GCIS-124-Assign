package peggame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class SqaurePeggame implements PegGame
{
    private int sizes;
    private boolean[][] pboard;
    private GameState gamestate;
    
        public SqaurePeggame(int sizes, boolean[][] pegboard)
        {
        //this.size1= size1;
        textreader tr= new textreader();
        //size1= tr.ReadLineOne();
        //this.pboard= new boolean[size1][size1];  //add pegs to the board
        //System.out.println(pegboard);
        try
        {   
            System.out.print("To play the game ");
            String filen= tr.Textread();//in.nextLine();
            FileReader fr1= new FileReader(filen); //accessing file to be read in, if not exists, other file is created
            BufferedReader reader1= new BufferedReader(fr1);  //to write into that particular file
            String line1= "";
            this.sizes = Integer.parseInt(reader1.readLine());
            this.pboard = new boolean[this.sizes][this.sizes];  //creating pegboard of that size
            System.out.println("----------------------------------");
            for (int row=0;row<this.sizes;row++)
            {
                String line = reader1.readLine();  //reading rows
                for (int col=0;col<this.sizes;col++)
                {
                    if(line.charAt(col)=='o')
                    {
                        this.pboard[row][col]= true;  //assigning true to pegboard array when the file's board has 'o'
                    }
                }
                if(line1 == null)  //until all the lines are printed
                    break;
            }
            reader1.close();
            fr1.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        this.gamestate= GameState.NOT_STARTED;
        }
        public SqaurePeggame(String absolutePath) 
        {
            try
            {
                FileReader fr = new FileReader(absolutePath); //extracting path of the selected file
                BufferedReader reader = new BufferedReader(fr);
                this.sizes = Integer.parseInt(reader.readLine()); //extracting the size of the board from the file
                this.pboard = new boolean[this.sizes][this.sizes]; //creating peggboard of the above mentioned size
        
                for (int row = 0; row < this.sizes; row++) 
                {
                    String line = reader.readLine(); // reading file line by line
                    for (int col = 0; col < this.sizes; col++) 
                    { 
                        this.pboard[row][col] = (line.charAt(col) == 'o'); //checking for peg
                    }
                }
        
                reader.close();
                fr.close();
                this.gamestate = GameState.NOT_STARTED; //displaying the gamestate
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        /*Let's include the Collection<Move> getPossibleMoves(),
         * List<Move> validity(), GameSate getGameState(), makeMove() and toString() and describe the its
         * attributes
         */
        @Override
        public Collection<Move> getPossibleMoves()  //collection of the attributes to specify
        {
        List<Move> possiblemovement= new ArrayList<>();
        for(int i=0;i<this.sizes;i++)  //considering the valiid moves
            for(int j=0;j<this.sizes;j++)
                if(this.pboard[i][j])
                    possiblemovement.addAll(validity(new Location(i, j)));
        return possiblemovement;
        }
        public int getSize() {
            return this.sizes;
        }
    
        public boolean getPegboardValue(Location location) 
        {
            int row = location.getRow(); //getting the row
            int col = location.getCol(); // getting the column
            if (row >= 0 && row < this.sizes && col >= 0 && col < this.sizes) 
            {
                return this.pboard[row][col]; // returning the peggboard of the paticular location only if the location entered is valid
            }
            return false;
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
    
        private boolean ifvalid(int from_row, int from_col, int to_row,int to_col)
        {
        /* For invalidity checking:
        returnin g invalid if jump row and column are negative
        or when jum row and column size is exceeded
        */
        
        if(from_row<0||from_col<0||from_row>=this.sizes||from_col>=this.sizes||to_row<0||to_col<0||to_row>=this.sizes||to_col>=this.sizes)
        {
            return false;  //return invalid
        }
        else
        {
        int midr= (to_row+from_row)/2;  //consider middle row and column that will be jumped over
        int midc= (to_col+from_col)/2;
        /*checking if a peg exists on the middle row jump over,
        * middle column jumped over or not so as to remove that peg,
        * or if no peg exists at all to jump over then invalid move is returned*/
        if(this.pboard[to_row][to_col]==true|| this.pboard[midr][midc]==false||this.pboard[from_row][from_col]==false)
        {
            return false;  // move not possible
        }
        return true;
        }
        }
        public void saveGame(String fileName) 
        {
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(String.valueOf(this.sizes)); //converting the int size to string
                writer.write("");
        
                for (int row = 0; row < this.sizes; row++) 
                {
                    for (int col = 0; col < this.sizes; col++) //writing to the file to save the changes made after each move made by the user
                    {
                        writer.write(this.pboard[row][col] ? 'o' : '-');
                    }
                    writer.write("\n");
                }
            } catch (IOException e) 
            {
                System.out.println("Error saving the game due to wrong file"+e.getMessage());
            }
        }
    
        @Override
        public GameState getGameState()
        {
            if (this.gamestate == null) {
                return GameState.NOT_STARTED; // returns a default state if gamestate is null
            }
            return this.gamestate;
        }
    
        @Override
        public void makeMove(Move move) throws PegGameException
        {
        Location from= move.getFrom();  //getting from and to values
        System.out.println("From "+from);
        Location to= move.getTo();
        System.out.println("To"+to);
        int from_row= from.getRow();
        int to_row= to.getRow();
        int from_col= from.getCol();
        int to_col= to.getCol();

        int jump_row= (from_row+to_row)/2;
        //jumping over peg at this row
        int jump_col= (from_col+to_col)/2;
        //jumping over peg at this column
        if(!ifvalid(from_row, from_col, to_row, to_col))
        {
            throw new PegGameException("Invalid Jump");
        }
        System.out.println("----------------------------------");
        this.pboard[from_row][from_col]= false;  //assigning pegs: 'o' and empty:'-' spaces to the board
        this.pboard[to_row][to_col]= true;
        this.pboard[jump_row][jump_col]= false;

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
        public boolean isValidMove(Move move)
         {
            /* checking for the validity of the 
            move made by the user on the GUI */
            Location from = move.getFrom();
            Location to = move.getTo();
    
            // checking if "from.getRow()","to.getRow()","to.getCol()","from.getCol()" are valid
            if (from.getRow() < 0 || from.getRow() >= this.sizes || from.getCol() < 0 || from.getCol() >= this.sizes ||
                to.getRow() < 0 || to.getRow() >= this.sizes || to.getCol() < 0 || to.getCol() >= this.sizes)
                 {
                return false;
            }
    
            // checking for the peg at the respective row and column
            if (!this.pboard[from.getRow()][from.getCol()] || this.pboard[to.getRow()][to.getCol()]) {
                return false;  //if not wrong peg chosen
            }
    
            // checking for the validity of the jump
            int midr = Math.abs(from.getRow() - to.getRow());
            int midc = Math.abs(from.getCol() - to.getCol());
            if (midr == 2 && midc == 0)
            {
                int jumpRow = (from.getRow() + to.getRow()) / 2;  //extracting the middle rrow position being jumped over
                if (this.pboard[jumpRow][from.getCol()])
                 {
                    return true;
                }
            } else if (midr == 0 && midc == 2) {
                int jumpCol = (from.getCol() + to.getCol()) / 2; //extracting the middle col position being jumped over
                if (this.pboard[from.getRow()][jumpCol]) 
                {
                    return true;
                }
            }
    
            return false;
        }

        private int remainingpegs()  //method to keep count of remaining pegs and returns remaing pegs left
        {
        int peg_count= 0;
        for(int i=0;i<this.pboard.length;i++)
            for (int j=0;j<this.pboard[i].length;j++)
                if (this.pboard[i][j]==true)  //checking if its true  in the pegboard at i j position which means that peg is present
                    peg_count++;
        return peg_count;
        }

        public static void playgame(PegGame game, GameState gstate, int size)
        {
        int r1;int r2;int c1;int c2;  //initializing
        if(gstate==GameState.NOT_STARTED)  //if game hasn't started yet, proceed with the game
        {
            BufferedReader read = new BufferedReader(System.console().reader());
            while(gstate==GameState.IN_PROGRESS||gstate== GameState.NOT_STARTED)
            {
                System.out.println("Enter Yes to proceed with the game or No to Exit");
                try
                {
                    String proceed= read.readLine();  //proceed: game to be played or not
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
                                System.out.println("Present game state: "+gstate);
                            }
                            catch (PegGameException e)
                            {
                                System.out.println("Invalid Move: "+e.getMessage());  //eror display
                            }
                    }
                    else
                        System.out.println("Incorrect input!");
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
    {// tostring method to display board
    StringBuilder sb= new StringBuilder();
    for(int i=0;i<this.pboard.length;i++)
    {
        for (int j=0;j<this.pboard[i].length;j++)
            if(this.pboard[i][j]==true) 
                sb.append("o");
            else
                sb.append("-");
        sb.append("\n");
    }
    return sb.toString();
    }
}


