public class Move implements MoveInterface {
    
    private int diceValue;
    private int sourceLocation;
    
    public Move() {
        diceValue = 0;
        sourceLocation = 0;
    }
    
    /** @param locationNumber represents the board position to move a piece from
     *  in the range 0-24. 0 reresents off the board (the knocked location if there are pieces there, otherwise the off-board star location). A locationNumber of 1-24 refers to locations on the board with 1 being the first and 24 being the last.
     *  @throws NoSuchLocationException if locationNumer is not in the range 0-24 **/
    public void setSourceLocation(int locationNumber) throws NoSuchLocationException {
        if (locationNumber < 0 || locationNumber > 24 + 2)
            throw new NoSuchLocationException("The value ("+locationNumber+") is not in the valid range 0-24.");
        sourceLocation = locationNumber;
    }

    public int getSourceLocation() {
        return sourceLocation;
    }

    /** @param diceValue represents the value of the dice to be used in the move 
     *  @throws IllegalMoveException if diceValue is not in the range 0-6  **/
    public void setDiceValue(int diceValue) throws IllegalMoveException {
        if (diceValue < 0 || diceValue > 6)
            throw new IllegalMoveException("The value on the dice ("+diceValue+") is not in the valid range 0-6.");
        this.diceValue = diceValue;
    }

    public int getDiceValue() {
        return diceValue;
    }
}
