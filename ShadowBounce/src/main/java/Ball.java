import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Side;


/**
 * Ball class
 * in my design, fire ball is the ball in a special condition, determined by fireMode
 */
public class Ball extends Sprite{
    private static final double X_APPEAR_POINT =512;
    private static final double Y_APPEAR_POINT =32;
    private static final int MAX_BALL_RANGE = 768;
    private static final double LEFT_BOUND=0;
    private static final double RIGHT_BOUND=1024;
    private static final double GRAVITY_VELOCITY = 0.15;
    private static final String FIREBALL_IMAGE = "res/fireball.png";
    private static final String BALL_IMAGE = "res/ball.png";
    private double moveXPerFrame;
    private double moveYPerFrame;
    private boolean fireMode;
    private static final double SPEED = 10;


    /**
     * Constructor
     * initialise attributes with default value
     */
    public Ball() {
        super(X_APPEAR_POINT,Y_APPEAR_POINT,false, BALL_IMAGE);
        moveXPerFrame=0;
        moveYPerFrame=0;
        fireMode = false;
    }

    /**
     * setter method for x speed
     * @param moveXPerFrame
     */
    public void setMoveXPerFrame(double moveXPerFrame){this.moveXPerFrame=moveXPerFrame;}

    /**
     * setter method for y speed
     * @param moveYPerFrame
     */
    public void setMoveYPerFrame(double moveYPerFrame){this.moveYPerFrame=moveYPerFrame;}

    /**
     * increase y speed by gravity velocity each frame
     * move by the x and y speeds (ball's velocity)
     */
    public void movePerFrame(){
        if(getExist()) {
            moveYPerFrame += GRAVITY_VELOCITY;
        }
        super.move(moveXPerFrame,moveYPerFrame);
    }

    /**
     * getter method for speed
     * @return
     */
    public double getSpeed(){return SPEED;}

    /**
     * turn on and turn off fire mode
     */
    public void turnOnFireMode(){
        fireMode = true;
        setImage(FIREBALL_IMAGE);
    }
    public void turnOffFireMode(){
        fireMode = false;
        setImage(BALL_IMAGE);
    }

    /**
     * getter method to check if the ball is in fire mode
     * @return
     */
    public boolean getFireMode(){return fireMode;}

    /**
     * check if the ball drop off the screen
     * @return
     */
    public Boolean outOfRange(){
        if(super.getY()>MAX_BALL_RANGE && getExist()){
            super.setExist(false);
            this.moveXPerFrame=0;
            this.moveYPerFrame=0;
            return true;
        }
        return false;
    }

    /**
     * ball bounces off a peg
     * @param peg
     */
    public void bounceByPeg(Peg peg){
        Rectangle rectPeg = peg.getImage().getBoundingBoxAt(new Point(peg.getX(),peg.getY()));
        Rectangle rectBall = getImage().getBoundingBoxAt(new Point(getX(),getY()));
        if(rectPeg.intersectedAt(rectPeg.centre(),rectBall.centre()) == Side.LEFT ||
                rectPeg.intersectedAt(rectPeg.centre(),rectBall.centre()) == Side.RIGHT){
            moveXPerFrame = -moveXPerFrame;
        }else if(rectPeg.intersectedAt(rectPeg.centre(),rectBall.centre()) == Side.TOP ||
                rectPeg.intersectedAt(rectPeg.centre(),rectBall.centre()) == Side.BOTTOM){
            moveYPerFrame = -moveYPerFrame;
        }

    }

    /**
     * appear at the appear point
     */
    public void appear(){
        super.setX( X_APPEAR_POINT);
        super.setY( Y_APPEAR_POINT);
        super.setExist(true);
    }

    /**
     * check if the ball should bounce off screen sides
     */
    public void bounceCheck() {
        if (getExist()) {
            if (super.getX() <= LEFT_BOUND || super.getX() >= RIGHT_BOUND) {
                moveXPerFrame = -moveXPerFrame;
            }
        }
    }


}
