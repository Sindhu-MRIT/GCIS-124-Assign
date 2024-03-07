package peggame;

public class Location
{
    private int row;
    private int col;
    private Object ob;    
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
    public boolean equals(Object ob)
    {
        /*To check if the peg row and column of a location
        are equal to the other Location */
        if (this.ob==ob)
            return true;
        else
        if(ob==null||getClass()!= ob.getClass())
        return false;
        Location loc= (Location) (ob);  //upcasting o to Location Parent Class
        return row==loc.row && col==loc.col;  //setting location
    }
    @Override
    public String toString()
    {
        return "Present Position Of Peg, Row: "+row+", Column: "+col;  //displaying
    }
}