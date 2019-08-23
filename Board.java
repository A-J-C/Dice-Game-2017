import java.util.*;
import java.util.Set;

public class Board implements BoardInterface {
    
    private List<LocationInterface> locations;
    private String name;
    
    public Board() {
        locations = new ArrayList<LocationInterface>();
        for (int i = 0; i < NUMBER_OF_LOCATIONS + 3; i++) {
            Location l = new Location(""+i);
            locations.add(l);
        }
        locations.get(0).setMixed(true);
        locations.get(NUMBER_OF_LOCATIONS + 1).setMixed(true);
        locations.get(NUMBER_OF_LOCATIONS + 2).setMixed(true);
        locations.get(0).setName("Start");
        locations.get(NUMBER_OF_LOCATIONS + 1).setName("Knocked");
        locations.get(NUMBER_OF_LOCATIONS + 2).setName("End");
        try {
            for(Colour c : Colour.values())
                for (int j = 0; j < PIECES_PER_PLAYER; j++) 
                    locations.get(0).addPieceGetKnocked(c);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<LocationInterface> getLocations() {
        return locations;
    }
    
    public void setLocations(List<LocationInterface> loc) {
        locations = loc;
    }
    
    /** @return the Location off the board where all pieces start the game. This will be a mixed location. **/
    public LocationInterface getStartLocation() {
        return locations.get(0);
    }

    /** @return the Location off the board where pieces get to when they have gone all the way round the board. This will be a mixed location. **/
    public LocationInterface getEndLocation() {
        return locations.get(NUMBER_OF_LOCATIONS + 2);
    }

    /** @return the Location where pieces go to when they are knocked off the board by an opposing piece. This will be a mixed location. **/
    public LocationInterface getKnockedLocation() {
        return locations.get(NUMBER_OF_LOCATIONS + 1);
    }

    /** @return the Location corresponding to a numbered position on the board. This will not be a mixed location
     * @param locationNumber the number of the location going from 1-24
     * @throws NoSuchLocationException when position is not in the range 1-24 **/
    public LocationInterface getBoardLocation(int locationNumber) throws NoSuchLocationException {
        if(locationNumber <= 0 || locationNumber > NUMBER_OF_LOCATIONS) 
            throw new NoSuchLocationException("Location "+locationNumber+", is not  in the range 1-"+NUMBER_OF_LOCATIONS);
        return locations.get(locationNumber);
    }

    /** @param colour the colour to move
     * @param move the move to make
     * @return true if and only if, from the current board state it would be legal for the given colour to make the given move. **/
    public boolean canMakeMove(Colour colour, MoveInterface move) {
        int newLocPos = move.getSourceLocation() + move.getDiceValue();
        if (newLocPos > NUMBER_OF_LOCATIONS) 
            return true;
        LocationInterface startLoc = locations.get(move.getSourceLocation());
        LocationInterface newLoc = locations.get(newLocPos);
        return (newLoc.canAddPiece(colour) && startLoc.canRemovePiece(colour));
    }

    /** Update the Board state by making the given move for the given colour, including any knocking off.
     * @param colour the colour to move
     * @param move the move to make
     * @throws IllegalMoveException if and only if the move is not legal. **/
    public void makeMove(Colour colour, MoveInterface move) throws IllegalMoveException {
        if (!canMakeMove(colour, move)) 
            throw new IllegalMoveException("You can not make that move.");

        LocationInterface startLoc = locations.get(move.getSourceLocation());
        int newLocPos = move.getSourceLocation() + move.getDiceValue();
        if (newLocPos > NUMBER_OF_LOCATIONS) 
            newLocPos = NUMBER_OF_LOCATIONS + 2;
        
        LocationInterface newLoc = locations.get(newLocPos);
        startLoc.removePiece(colour);
        Colour col = newLoc.addPieceGetKnocked(colour);
        if (col != null)
            locations.get(NUMBER_OF_LOCATIONS + 1).addPieceGetKnocked(col);
    }

    /** Update the Board state by making the all of the moves in the given turn in order, including any knocking off, based on the given diceValues.
     * @param colour the colour to move
     * @param turn the turn to take
     * @param diceValues the values of the dice available in no particular order. There will be repeated values in the list if a double is thrown
     * @throws IllegalTurnException if and only if the turns in the move are not legal for the diceValues give. Each of the moves has to be legal, and the diceValues in the moves of the turn must match the diceValues parameter. The number of moves in the turn must be no less than the maximum possible number of legal moves: all available dice must be used. If IllegalTurnException is thrown then the board state remains unchanged. **/
    public void takeTurn(Colour colour, TurnInterface turn, List<Integer> diceValues) throws IllegalTurnException {
        Board b = (Board) this.clone();
        try {
            for(MoveInterface m : turn.getMoves()) {
                makeMove(colour, m);
                diceValues.remove(new Integer(m.getDiceValue()));
            }
        } catch (Exception e) {
            this.setLocations(b.getLocations());
            throw new IllegalTurnException("That turn is invalid");
        }
    }

    /** @param colour the colour to check
     * @return true if and only if the given colour has won **/
    public boolean isWinner(Colour colour) {
        return (getEndLocation().numberOfPieces(colour) ==  PIECES_PER_PLAYER);
    }

    /** @return the colour of the winner if there is one, otherwise null **/
    public Colour winner() {
        for(Colour c : Colour.values())
            if (isWinner(c))
                return c;
        return null;
    }

    /** @return true if and only if the Board is in a valid state (do not need to check whether or not it could be reached by a valid sequence of moves) **/
    public boolean isValid() {
        for(LocationInterface l : locations)
            if (!l.isValid())
                return false;
        return true;
    }
    
    /** @param colour the colour to move next
     *  @param diceValues the dice values available to use
     *  @return a list of moves that the given colour can make from the current board state with (any one of) the given diceValues **/
    public Set<MoveInterface> possibleMoves(Colour colour, List<Integer> diceValues) {
        Set<MoveInterface> moves = new HashSet<MoveInterface>();
        int incrementTo = NUMBER_OF_LOCATIONS;
        int b = 0;
        if(getKnockedLocation().numberOfPieces(colour) > 0) {
            b = NUMBER_OF_LOCATIONS + 1;
            incrementTo += 2;
        } 
        for(int a = b; a < incrementTo; a++) 
            if (locations.get(a).numberOfPieces(colour) > 0) 
                for(int i = 0; i < diceValues.size(); i++) 
                    try {
                        MoveInterface m = new Move();
                        m.setSourceLocation(a);
                        m.setDiceValue(diceValues.get(i));
                        moves.add(m);
                    } catch (Exception e) { System.out.println(e); }
        return moves;
    }

    /** @return a copy of the board that can be passed to players to work with **/
    public BoardInterface clone() {
        Board b = (Board) new Board();
        b.setName(this.name);
        List<LocationInterface> locs = b.getLocations();
        try {
            for(int i = 0; i < locations.size(); i++) {
                LocationInterface origL = locations.get(i);
                LocationInterface newL = locs.get(i);
                newL.setName(origL.getName());
                newL.setMixed(origL.isMixed());
                for (Colour c : Colour.values()) 
                    for (int j = 0; j < origL.numberOfPieces(c); j++)
                        newL.addPieceGetKnocked(c);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return b;
    }

    /** Overrides toString() from Object with a suitable String representation of the board state for displaying via the console to a human **/
    public String toString() {
        String out = "";
        
        for(int i = 0; i < locations.size(); i ++) { 
            LocationInterface l = locations.get(i);
            out += ((i%5 == 0) ? "\n" : "") + l.getName();
            for(Colour c : Colour.values())
                if(l.numberOfPieces(c) != 0)
                    out += c + ": "+  l.numberOfPieces(c);
        }
        return out;
    }
}
