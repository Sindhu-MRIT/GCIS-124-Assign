package peggame;
import org.junit.Test;
import static org.junit.Assert.*;
public class PegGameTest {
    @Test
    public void checkLocation() {
        // createw and test Location
        Location loc = new Location(2, 3);
        assertEquals(2, loc.getRow());
        assertEquals(3, loc.getColumn());
        assertEquals("(2, 3)", loc.toString());
        //if two locations are the same
        Location sameLoc = new Location(2, 3);
        assertEquals(loc, sameLoc);
        //two different locations
        Location diffLoc = new Location(1, 2);
        assertNotEquals(loc, diffLoc);
    }
    @Test
    public void verifyMove() {
        //move and test
        Location start = new Location(1, 2);
        Location end = new Location(3, 4);
        Move move = new Move(start, end);
        assertEquals(start, move.getFrom());
        assertEquals(end, move.getTo());
        assertEquals("Move from (1, 2) to (3, 4)", move.toString());
        // checks moves equality
        Move sameMove = new Move(new Location(1, 2), new Location(3, 4));
        assertEquals(move, sameMove);
        // different moves
        Move differentMove = new Move(new Location(2, 3), new Location(4, 5));
        assertNotEquals(move, differentMove);
    }
    @Test
    public void testException() {
        //custom exception message
        String errorMsg = "Invalid move";
        PegGameException exception = new PegGameException(errorMsg);
        assertEquals(errorMsg, exception.getMessage());
    }
}

