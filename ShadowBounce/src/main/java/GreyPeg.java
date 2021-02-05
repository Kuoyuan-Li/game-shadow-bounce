public class GreyPeg extends Peg {
    private static final String PEG_SHAPE_HORIZONTAL = "res/grey-horizontal-peg.png";
    private static final String PEG_SHAPE_VERTICAL = "res/grey-vertical-peg.png";
    private static final String PEG_SHAPE_NORMAL = "res/grey-peg.png";
    private static final String HORIZONTAL = "horizontal";
    private static final String VERTICAL = "vertical";
    private static final String NORMAL = "normal";

    /**
     * constructor
     * use the information from input file to generate a specific shape grey peg
     * @param shape
     * @param x
     * @param y
     */
    public GreyPeg(String shape, double x, double y ){
        super(PEG_SHAPE_NORMAL,x,y);
        if(shape.contains(HORIZONTAL)) {
            setImage(PEG_SHAPE_HORIZONTAL);
        }else if(shape.contains(VERTICAL) ){
            setImage(PEG_SHAPE_VERTICAL);
        }else if(shape.contains(NORMAL)){
            setImage(PEG_SHAPE_NORMAL);
        }

    }

    /**
     * grey peg is undestroyable, do nothing when being touched
     */
    @Override
    public void destroy() {
    }

}

