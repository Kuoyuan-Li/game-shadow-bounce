public class Bucket extends Sprite  {
    private static final String BUCKET_IMAGE = "res/bucket.png";
    private double speed = 5;
    private static final double X_APPEAR=512;
    private static final double Y_APPEAR=744;
    private static final double LEFT_BOUND=0;
    private static final double RIGHT_BOUND=1024;

    /**
     * constructor for bucket
     * initialise the bucket with given attributes
     */
    public Bucket() {
        super(X_APPEAR,Y_APPEAR,true, BUCKET_IMAGE);
    }

    /**
     * bounce off screen edges
     */
    public void bounceCheck(){
        if(super.getX()<=LEFT_BOUND || super.getX()>=RIGHT_BOUND){
            speed=-speed;
        }
    }

    /**
     * move bucket horizontally by constant speed per frame
     */
    public void move() {
        super.move(speed,0);
    }
}
