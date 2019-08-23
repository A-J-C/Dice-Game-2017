import java.util.Random;

public class Die implements DieInterface {
    private static Random rand = new Random();
    private int value;
    private boolean rolled;

    public Die() {
        value = 0;
        rolled = false;
    }
    
    /** @return false when first constructed or cleared, then true once rolled (unless it is then cleared) **/
    public boolean hasRolled() {
        return rolled;
    }

    /** rolls the die to give a it a value in the range 1-6 **/
    public void roll() {
        value = rand.nextInt(NUMBER_OF_SIDES_ON_DIE) + 1;
        rolled = true;
    }

    /** @return the visible face of the die, a value in the range 1-6
        @throws NotRolledYetException if the die has not been rolled or has been cleared since the last roll **/
    public int getValue() throws NotRolledYetException {
        if (!rolled) 
            throw new NotRolledYetException("This die has no value, it has not been rolled.");
        return value;
    }

    /** set the face value of the die: only needed when recreating a game state
     * @param value the new value of the die. If it is not in an acceptable range then afterwards hasRolled() should return false. **/
    public void setValue(int value) {
        if (value > 0 && value <= NUMBER_OF_SIDES_ON_DIE) {
            this.value = value;
            rolled = true;
        } else {
            rolled = false;
        }
    }
    
    /** clears the die so it has no value until it is rolled again **/
    public void clear() {
        value = 0;
        rolled = false;
    }

    /** sets the seed for the random number generator used by all dice
     * @param seed the seed value to use for randomisation **/
    public void setSeed(long seed){
        rand.setSeed(seed);
    }
}
