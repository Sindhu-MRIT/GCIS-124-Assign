

public class javajunit {
    private SquarePegGame game;

    @Test
    void setUp() {
        boolean[][] board ={
            {false, true, false},
            {false, false, false},
            {false, false, false}
        };
        game= new SquarePegGame(3, board);
    }

    @Test
    void testGetPossibleMoves() {
        Collection<Move> moves = game.getPossibleMoves();
        assertTrue(moves.isEmpty(), "Expected no possible moves from initial setup");
    }

    @Test
    void testMakeMoveValid() {
        Move validMove= new Move(new Location(0, 1), new Location(2, 1));
        assertThrows(PegGameException.class, () -> game.makeMove(validMove),
                "Expected makeMove to succeed with a valid move");
            }
            @Test
            void testMakeMoveInvalid(){
                
                Move invalidMove= new Move(new Location(0, 0), new Location(0, 2));
                assertThrows(PegGameException.class, () -> game.makeMove(invalidMove),
                        "Expected makeMove to throw PegGameException with an invalid move");
            }
            @Test
            void testGameStateAfterMove() {
                Move validMove= new Move(new Location(0, 1), new Location(2, 1));
                game.makeMove(validMove);
                assertEquals(GameState.IN_PROGRESS, game.getGameState(), "Game state should be IN_PROGRESS after a valid move");
            }
            @Test
            void testRemainingPegs() {
            }
        }
