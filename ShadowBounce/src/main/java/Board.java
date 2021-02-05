
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    private static final int BALLS_NUM = 3;
    private static final double BALL_FROM_GREENPEG_Vy = -7.1;
    private static final double BALL_FROM_GREENPEG_Vx = 7.1;
    private static final String PEG_COLOR_GREY = "grey";
    private static final String PEG_COLOR_BLUE = "blue";
    private static final int BURN_AREA = 70;
    private boolean nextTurnFire;
    private Bucket bucket;
    private PowerUp powerUp;
    private Ball[] balls;
    private ArrayList<Peg> pegs = new ArrayList<>();
    private int redPegCount;

    /**
     * constructor
     * create new balls, bucket and power up
     * set the next turn fire false because the ball can not touch the power up before the first shot
     */
    public Board(){
        balls = new Ball[BALLS_NUM];
        for(int i =0;i<BALLS_NUM;i++){
            balls[i] = new Ball();
        }
        nextTurnFire = false;
        bucket = new Bucket();
        powerUp = new PowerUp();
    }

    /**
     * getter method for red peg count
     * @return theCount
     */
    public int getRedPegCount(){return redPegCount;}

    /**
     * check the sprites (bucket, ball and powerup) bounces and interactions (between balls and other sprites) in each frame
     */
    public void eachFrameChecks() {
        bucket.bounceCheck();
        powerUp.reChooseDestinationCheck();
        for (int i = 0; i < BALLS_NUM; i++) {
            balls[i].bounceCheck();
            if (twoSpriteTouch(balls[i], powerUp)) {
                powerUp.destroy();
                nextTurnFire=true;
            }
        }
        for (Peg peg : pegs) {
            for (int i = 0; i < BALLS_NUM; i++) {
                if (twoSpriteTouch(balls[i], peg)) {
                    balls[i].bounceByPeg(peg);
                    redPegAndGreenPegCheck(peg);
                    peg.destroy();
                    if(balls[i].getFireMode()){
                        burnPegs(balls[i].getX(),balls[i].getY());
                    }
                }
            }
        }
    }

    /**
     * turn the fire mode on for all 3 balls
     */

    public void turnOffFireMode(){
        if(balls[0].getFireMode()) {
            for (int i=0;i<BALLS_NUM;i++ ) {
                balls[i].turnOffFireMode();
            }
        }
    }

    /**
     * burn pegs within 70 pixels(based on the given centre)
     * @param centralX
     * @param centralY
     */

    public void burnPegs(double centralX,double centralY){
        double distance = 0;
        for (Peg peg : pegs) {
            distance = Math.sqrt((peg.getX() - centralX)*(peg.getX() - centralX)
                    + (peg.getY() - centralY)*(peg.getY() - centralY));
            if(distance <= BURN_AREA){
                redPegAndGreenPegCheck(peg);
                peg.destroy();
            }
        }
    }

    /**
     * check if all balls fall off the screen
     * @return
     */

    public boolean ballsClear(){
        if((!balls[1].getExist())&&(!balls[2].getExist())){
            return balls[0].outOfRange();
        }else {
            balls[0].outOfRange();
            balls[1].outOfRange();
            balls[2].outOfRange();
            return !(balls[0].getExist() || balls[1].getExist() || balls[2].getExist());
        }
    }

    /**
     * check if the specific ball touches the bucket
     * @param ballIndex
     * @return
     */

    public boolean bucketTouchCheck(int ballIndex){
        if (twoSpriteTouch(bucket, balls[ballIndex])){
            return true;
        } else{
            return false;
        }
    }


    /**
     * reset powerup
     */
    public void resetPowerUp(){
        powerUp.reset();
    }

    /**
     * decide the peg colors, shapes and positions based on one line of the input file
     * created corresponding peg
     * @param colorAndShape
     * @param xPosition
     * @param yPosition
     */
    public void addNewPegs(String colorAndShape, double xPosition, double yPosition){
        if(colorAndShape.contains(PEG_COLOR_BLUE)){
            Peg newPeg = new BluePeg(colorAndShape,xPosition,yPosition);
            pegs.add(newPeg);

        }else if (colorAndShape.contains(PEG_COLOR_GREY)){
            Peg newPeg = new GreyPeg(colorAndShape,xPosition,yPosition);
            pegs.add(newPeg);
        }
    }

    /**
     * make the ball appear if it is not exist
     * set the velocity of ball at the start of each turn
     * @param xDirection
     * @param yDirection
     * @return
     */

    public Boolean ballsAppearAndMove(double xDirection, double yDirection) {
        if ((!balls[0].getExist())&&(!balls[1].getExist())&&(!balls[2].getExist())) {
            balls[0].appear();
            double distance = Math.sqrt((balls[0].getX() - xDirection) * (balls[0].getX() - xDirection) + (balls[0].getY()
                    - yDirection) * (balls[0].getY() - yDirection));
            balls[0].setMoveXPerFrame(balls[0].getSpeed() * (xDirection - balls[0].getX()) / distance);
            balls[0].setMoveYPerFrame(balls[0].getSpeed() * (yDirection - balls[0].getY()) / distance);
            if(nextTurnFire){
                for(Ball ball:balls){
                    ball.turnOnFireMode();
                }
                nextTurnFire = false;
            }
            return true;
        }
        return false;
    }

    /**
     * check if the touched peg is an instance of green peg or red peg
     * if so, do the corresponding actions
     * @param peg
     */

    public void redPegAndGreenPegCheck(Peg peg){
        if(peg instanceof RedPeg && peg.getExist()){
            redPegCount--;
        }
        if (peg instanceof GreenPeg && peg.getExist()) {
            for(int j = 1; j < BALLS_NUM; j++){
                balls[j].setExist(true);
                balls[j].setX(peg.getX());
                balls[j].setY(peg.getY());
                balls[j].setMoveYPerFrame(BALL_FROM_GREENPEG_Vy);
            }
            balls[1].setMoveXPerFrame(BALL_FROM_GREENPEG_Vx);
            balls[2].setMoveXPerFrame(-BALL_FROM_GREENPEG_Vx);
        }
    }
    /**
     * generate red pegs for this board
     */

    public void generateGreenPeg(){
        boolean bluePegExist=false;
        for(Peg peg: pegs){
            if(peg instanceof BluePeg && peg.getExist()){
                bluePegExist = true;
            }
        }
        if(bluePegExist) {
            Random rand = new Random();
            int randInt = rand.nextInt(pegs.size());
            while (!(pegs.get(randInt) instanceof BluePeg) || !(pegs.get(randInt).getExist())) {
                randInt = rand.nextInt(pegs.size());
            }
            GreenPeg replacedGreenPeg = new GreenPeg(pegs.get(randInt));
            pegs.set(randInt, replacedGreenPeg);
        }
    }
    /**
     * generate red pegs for this board
     */

    public void generateRedPegs(){
        int bluePegCount = 0;
        int toBeChangedBluepegCount = 0;
        for(Peg peg: pegs){
            if(peg instanceof BluePeg){
                bluePegCount++;
            }
        }
        redPegCount = 0;
        toBeChangedBluepegCount = bluePegCount/5;
       while(toBeChangedBluepegCount >=0){
           Random rand = new Random();
           int randInt = rand.nextInt(pegs.size());
           if(pegs.get(randInt) instanceof BluePeg){
               RedPeg replacedRedPeg = new RedPeg(pegs.get(randInt));
               pegs.set(randInt,replacedRedPeg);
               redPegCount ++;
               toBeChangedBluepegCount --;
           }
       }
    }

    /**
     * reset the position of the green peg at the start of each turn
     */

    public void resetGreenPeg(){
        for(Peg peg: pegs){
            if(peg instanceof GreenPeg && peg.getExist()){
                BluePeg replacedBluePeg = new BluePeg(peg);
                int index = pegs.indexOf(peg);
                pegs.set(index, replacedBluePeg);
            }
        }
        generateGreenPeg();
    }

    /**
     * check if 2 sprites touch each others
     * @param spriteA
     * @param spriteB
     * @return
     */
    public Boolean twoSpriteTouch(Sprite spriteA, Sprite spriteB){
        if(spriteA.getExist() && spriteB.getExist()) {
            Rectangle rectA = spriteA.getImage().getBoundingBoxAt(new Point(spriteA.getX(),spriteA.getY()));
            Rectangle rectB = spriteB.getImage().getBoundingBoxAt(new Point(spriteB.getX(),spriteB.getY()));
            return rectA.intersects(rectB);
        }
        return false;
    }

    /**
     * render all sprites if they exist
     */
    public void renderBoard(){
        for(Peg peg: pegs){
            peg.render();
        }
        powerUp.render();
        for(int i =0;i<BALLS_NUM;i++){
            balls[i].render();
        }

        bucket.render();
    }

    /**
     * move all movable sprites
     */
    public void spritesMove(){
        powerUp.move();
        for(int i =0;i<BALLS_NUM;i++){
            balls[i].movePerFrame();
        }
        bucket.move();
    }

}
