public class GreenPeg extends Peg{
    public static final String PEG_SHAPE_HORIZONTAL = "res/green-horizontal-peg.png";
    public static final String PEG_SHAPE_VERTICAL = "res/green-vertical-peg.png";
    public static final String PEG_SHAPE_NORMAL = "res/green-peg.png";
    public static final String HORIZONTAL = "horizontal";
    public static final String VERTICAL = "vertical";

    /**
     * constructor
     * "convert" an other color peg to green peg
     * @param originPeg
     */
    public GreenPeg(Peg originPeg){
        super(PEG_SHAPE_NORMAL, originPeg.getX(), originPeg.getY() );
        setShape(originPeg.getShape());
        if( getShape() == HORIZONTAL){
            setImage(PEG_SHAPE_HORIZONTAL);
        }else if ( getShape() == VERTICAL){
            setImage(PEG_SHAPE_VERTICAL);
        }

    }

    @Override
    public void destroy() {
        setExist(false);
    }

}
