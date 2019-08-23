import java.io.IOException;

public class Game implements GameInterface {
    
    public Game() {
    
    }
    
    /** Also requires a main method which allows the user to choose the player types and start the game. The main method menu should allow users to: set the players (human or computer); load a game; continue a game; save the game; start a new game; exit the program. 
     *  If providing a GUI then the same options need to be available through the GUI. **/
    public static void main (String args[]) {
    
    }
    
    /** @param colour of the player to set
     *  @param player the player to use **/
    public void setPlayer(Colour colour, PlayerInterface player) {
    
    }

    /** @return the player who has the next turn. Green goes first. **/
    public Colour getCurrentPlayer() {
        return null;
    }

    /** Play the game until completion or pause. Should work either for a new game or the continuation of a paused game. This method should roll the dice and pass the dice values to the players. The players should be asked one after another for their choice of turn via their getTurn method. The board that is passed to the players should be a clone of the game board so that they can try out moves without affecting the state of the game.
     *  @return the colour of the winner if there is one, or null if not (the game has been paused by a player). If a player tries to take an illegal turn then they forfeit the game and the other player immediately wins.
     *  @throws PlayerNotDefinedException if one or both of the players is undefined **/
    public Colour play() throws PlayerNotDefinedException {
        return null;
    }

    /** Save the current state of the game (including the board, dice and player types) into a file so it can be re-loaded and game play continued. You choose what the format of the file is.
     *  @param filename the name of the file in which to save the game state
     *  @throws IOException when an I/O problem occurs while saving **/
    public void saveGame(String filename) throws IOException {
    
    }

    /** Load the game state from the given file
     *  @param filename  the name of the file from which to load the game state
     *  @throws IOException when an I/O problem occurs or the file is not in the correct format (as used by saveGame()) **/
    public void loadGame(String filename) throws IOException {
    
    }
}
