package peggame;
import java.util.Collection;

public interface PegGame
{
    Collection<Move> getPossibleMoves();  //to getmoves
    GameState getGameState();  //to get game state
    void makeMove(Move move) throws PegGameException;  //check for validity and print error when required
    String toString();
}
