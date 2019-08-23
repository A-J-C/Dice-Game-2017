import java.util.*;

public class Turn implements TurnInterface {
    
    private List<MoveInterface> moves;
    
    public Turn() {
        moves = new ArrayList<MoveInterface>();
    }
    
    /** @param move to be added after the moves already defined in the current turn
     *  @throws IllegalTurnException if there are already four or more moves in the turn **/
    public void addMove(MoveInterface move) throws IllegalTurnException {
        if(moves.size() >= 4) 
            throw new IllegalTurnException("There's already been four or more moves in the turn.");
        moves.add(move);
    }

    public List<MoveInterface> getMoves() {
        return moves;
    }
}
