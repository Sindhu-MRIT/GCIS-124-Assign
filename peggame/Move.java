package peggame;

public class Move
{
    private Location from;
    private Location to;
    private Object ob;

    public Move(Location from, Location to)
    {
        this.from= from;
        this.to= to;
    }
    public Location getFrom()  //getters
    {
        return from;
    }
    public Location getTo()
    {
        return to;
    }
    public boolean equals(Object ob)
    {
        /*Moving peg from one location to other on the board */
        if(this.ob== ob)
            return true;
        if(ob==null||getClass()!=ob.getClass())
        return false;
            Move move= (Move) (ob);  //upcasting o to move
        if(from.equals(move.from)== true && to.equals(move.to)== true)
            return true;
        else
            return false;
    }
    @Override
    public String toString()
    {
        return "Peg Moved From: "+from+" to: "+to;  //displaying
    }
}
