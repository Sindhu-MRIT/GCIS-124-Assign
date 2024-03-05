package PEGGAME;

public class Move extends Location implements PegGame
{
    private Location from;
    private Location to;

    public Move(Location from, Location to)
    {
        super(getRow(), getCol());
        this.from= from;
        this.to= to;
    }
    public void setFrom(Location from) {
        this.from = from;
    }
    public void setTo(Location to) {
        this.to = to;
    }
    public Location getFrom()
    {
        return from;
    }
    public Location getTo()
    {
        return to;
    }
    public boolean equals(Object o)
    {
        /*Moving peg from one location to other on the board */
        if(this.o== o)
            return true;
        else
            return false;
            Move move= (Move)o;  //upcasting o to move
        if(from.equals(move.from)== true && to.equals(move.to)== true)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return "Peg Moved From: "+from+" to: "+to;
    }
}
