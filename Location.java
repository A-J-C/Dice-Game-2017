import java.util.*;

public class Location implements LocationInterface {
    
    private String name;
    private boolean mixed;
    private HashMap<Colour, Integer> pieces;
    private boolean empty;
    
    public Location(String name) {
        this.name = name;
        mixed = false;
        empty = true;
        pieces = new HashMap<Colour, Integer>();
        for(Colour c : Colour.values())
            pieces.put(c, 0);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /** @return true if and only if the location allows pieces of both colours **/
    public boolean isMixed() {
        return mixed;
    }

    /** @param isMixed true if and only if the location allows pieces of both colours **/
    public void setMixed(boolean isMixed) {
        mixed = isMixed;
    }
    
    /** @return true if and only if the location has no pieces in it **/
    public boolean isEmpty() {
        return empty;
    }

    /** @param colour the colour of pieces to count
     *  @return the number of pieces of that colour **/
    public int numberOfPieces(Colour colour) {
        return pieces.get(colour);
    }

    /** @param colour the colour of the piece to add
     *  @return true if and only if a piece of that colour can be added (i.e. no IllegalMoveException) **/
    public boolean canAddPiece(Colour colour) {
        if (pieces.get(colour.otherColour()) > 1 && !mixed)
            return false;
        return true;
    }

    /** @param colour the colour of the piece to add
     *  @throws IllegalMoveException if the location is not mixed and already contains two or more pieces 
     *  of the other colour
     *  @return null if nothing has been knocked off, otherwise the colour of the piece that has been knocked off **/
    public Colour addPieceGetKnocked(Colour colour) throws IllegalMoveException {
        if (!canAddPiece(colour))
            throw new IllegalMoveException("Illegeal Move: that location is not mixed and already contains two or more pieces of the other colour.");
        
        pieces.put(colour, pieces.get(colour) + 1);
        Colour otherC = colour.otherColour();
        if (pieces.get(otherC) == 1 && !mixed) {
            removePiece(otherC);
            return otherC;
        }

        return null;
    }

    /** @param colour the colour of the piece to remove
     *  @return true if and only if a piece of that colour can be removed (i.e. no IllegalMoveException) **/
    public boolean canRemovePiece(Colour colour) {
        return (pieces.get(colour) > 0);
    }
    
    /** @param colour the colour of the piece to remove
     *  @throws IllegalMoveException if there are no pieces of that colout int the location **/
    public void removePiece(Colour colour) throws IllegalMoveException {
        if (!canRemovePiece(colour))
            throw new IllegalMoveException("Illegeal Move: there are no pieces to remove.");
        
        pieces.put(colour, pieces.get(colour) - 1);
    }

    /** @return true if and only if the Location is in a valid state depending on the number of each colour and whether or not it is a mixed location **/
    public boolean isValid() {
        boolean otherPieces = false;
        for (Colour e : pieces.keySet()) {
            if (pieces.get(e) < 0 || (otherPieces && !mixed))
                return false;
            else if(pieces.get(e) >= 0)
                otherPieces = true;
        }
        
        return true;
    }
    
}
