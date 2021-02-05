public class RedPeg extends Peg {
    private static final String PEG_SHAPE_HORIZONTAL = "res/red-horizontal-peg.png";
    private static final String PEG_SHAPE_VERTICAL = "res/red-vertical-peg.png";
    private static final String PEG_SHAPE_NORMAL = "res/red-peg.png";
    private static final String HORIZONTAL = "horizontal";
    private static final String VERTICAL = "vertical";

    /**
     * constructor
     * "convert" an other color peg to red peg
     * @param originPeg
     */
    public RedPeg(Peg originPeg){
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
