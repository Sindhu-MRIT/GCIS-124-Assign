package PEGGAME;

public class Location
{
    private int row;
    private int col;
    
    public Location(int row, int col)  //constructor
    {
        this.row= row;
        this.col= col;
    }
    public int getRow()  //getters for row and column
    {   return row;    }
    public int getCol()
    {   return col;    }
    @Override
    public boolean equals(Object o)
    {
        /*To check if the peg row and column of a location
        are equal to the other Location */
        if (this.o ==o)
            return true;
        else
            return false;
        Location location= (Location) o;  //upcasting o to Location Parent Class
        return row==location.row && col==location.col;  //setting location
    }
    @Override
    public String toString()
    {
        return "Present Position Of Peg, Row: "+row+", Column: "+col;
    }
}