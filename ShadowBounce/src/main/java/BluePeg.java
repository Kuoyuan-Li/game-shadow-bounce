public class BluePeg extends Peg {
    private static final String PEG_SHAPE_HORIZONTAL = "res/horizontal-peg.png";
    private static final String PEG_SHAPE_VERTICAL = "res/vertical-peg.png";
    private static final String PEG_SHAPE_NORMAL = "res/peg.png";
    private static final String HORIZONTAL = "horizontal";
    private static final String VERTICAL = "vertical";
    private static final String NORMAL = "normal";

    /**
     * generate new blue peg, set the shape and position based on the input file
     * @param shape
     * @param x
     * @param y
     */
    public BluePeg(String shape, double x, double y ){
        super(PEG_SHAPE_NORMAL,x,y);

        if(shape.contains(HORIZONTAL)) {
            setShape(HORIZONTAL);
            setImage(PEG_SHAPE_HORIZONTAL);

        }else if(shape.contains(VERTICAL) ){
            setShape(VERTICAL);
            setImage(PEG_SHAPE_VERTICAL);
        }else if (shape.contains(NORMAL)){
            setShape(NORMAL);
            setImage(PEG_SHAPE_NORMAL);
        }

    }

    /**
     * "convert" an other color peg to the blue peg
     * @param originPeg
     */
    public BluePeg(Peg originPeg){
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
