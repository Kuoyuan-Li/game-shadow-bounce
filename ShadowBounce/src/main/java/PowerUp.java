import java.util.Random;

public class PowerUp extends Sprite{
    private static final String POWERUP_IMAGE = "res/powerup.png";
    private static final int RIGHT_BOUND = 1048;
    private static final int BOTTOM_BOUND =748;
    private static final double SPEED = 3;
    private static final int SHORTEST_DISTANCE = 5;
    public static final int CHANGE = 10;

    private double xDestination;
    private double yDestination;
    private double xStart;
    private double yStart;
    private double moveXPerFrame;
    private double moveYPerFrame;

    /**
     * constructor
     * initialise the power up
     * use decideReappear method to determine if the power up should appear
     * randomly choose start position and destination, set velocity based on these 2 points
     */
    public PowerUp(){
        super(0,0,false, POWERUP_IMAGE);
        Random rand = new Random();
        if (decideReappear()) {
            super.setExist(true);
        }
        xStart = rand.nextInt(RIGHT_BOUND);
        yStart = rand.nextInt(BOTTOM_BOUND);
        super.setX(xStart);
        super.setY(yStart);
        chooseNewDestinationAndSetSpeed(xStart,yStart);
    }

    /**
     * move based on the power up velocity
     */
    public void move() {
        super.move(moveXPerFrame, moveYPerFrame);
    }


    /**
     * reset the power up at the start of each turn
     * use decideReappear method to determine if the power up should appear at this turn
     * if yes, randomly choose start position and destination, set velocity based on these 2 points
     */
    public void reset(){
        setExist(false);
        Random rand = new Random();
        if (!decideReappear()) {
            return;
        }
        xStart = rand.nextInt(RIGHT_BOUND);
        yStart = rand.nextInt(BOTTOM_BOUND);
        super.setX(xStart);
        super.setY(yStart);
        chooseNewDestinationAndSetSpeed(xStart,yStart);
        super.setExist(true);
    }

    /**
     * when power up arrives its destination (within 5 pixels), rechoose a new destination
     */
    public void reChooseDestinationCheck(){
        double distanceToDestination = Math.sqrt((getX()-xDestination) * (getX()-xDestination) + (getY()
                - yDestination) * (getY() - yDestination));
        if(distanceToDestination < SHORTEST_DISTANCE) {
            chooseNewDestinationAndSetSpeed(getX(),getY());
        }
    }

    /**
     * randomly choose the destination point
     * calculate the velocity based on the current position(indicated by xNow, yNow) and the chosen destination
     * @param xNow
     * @param yNow
     */
    public void chooseNewDestinationAndSetSpeed(double xNow, double yNow){
        Random rand = new Random();
        xDestination = rand.nextInt(RIGHT_BOUND);
        yDestination = rand.nextInt(BOTTOM_BOUND);
        double distance = Math.sqrt((xNow-xDestination) * (xNow-xDestination) + (yNow
                - yDestination) * (yNow - yDestination));
        moveXPerFrame = SPEED * (xDestination - getX()) / distance;
        moveYPerFrame = SPEED * (yDestination - getY()) / distance;

    }

    /**
     * method to decide if the power up should appear in this turn or not
     * 1/10 chance the power up should appear again
     * @return if the power up should appear
     */
    public boolean decideReappear(){
        Random rand = new Random();
        int randInt = rand.nextInt(CHANGE);
        if (randInt == 0) {
            return true;
        }else{
            return false;
        }
    }


    /**
     * destroy the power up when being touched
     */
    public void destroy(){ setExist(false); }
}
