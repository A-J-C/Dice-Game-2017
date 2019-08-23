import java.util.*;

public class Dice implements DiceInterface {
    
    private Die firstDie;
    private Die secondDie;
    
    public Dice() {
        firstDie = new Die();
        secondDie = new Die();
    }
    
    /** @return true if and only if both of the dice have been rolled **/
    public boolean haveRolled() {
        return (firstDie.hasRolled() && secondDie.hasRolled());
    }

    /** Roll both of the dice */
    public void roll() {
        firstDie.roll();
        secondDie.roll();
    }

    /** @return four numbers if there is a double, otherwise two
     *  @throws NotRolledYetException if either of the dice have not been rolled yet **/
    public List<Integer> getValues() throws NotRolledYetException {
        List<Integer> values = new ArrayList<Integer>();
        int firstVal = firstDie.getValue();
        int secondVal = secondDie.getValue();
       
        int j = (firstVal == secondVal) ? 2 : 1;
        for(int i = 0; i < j; i++) {
            values.add(firstVal);
            values.add(secondVal);
        }
        
        return values;
    }
 
    /** clear both of the dice so they have no value until they are rolled again **/
    public void clear() {
        firstDie.clear();
        secondDie.clear();
    }

    /** Get the individual dice in a list.
     *  @return the Die objects in a list, which will have length 2 **/
    public List<DieInterface> getDice() {
        List<DieInterface> dice = new ArrayList<DieInterface>();
        dice.add(firstDie);
        dice.add(secondDie);
        
        return dice;
    }
}
