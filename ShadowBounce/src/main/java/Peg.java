import bagel.*;

import java.security.PublicKey;
import java.util.Random;

/**
 * Peg abstract class
 * provide a template for specific color pegs
 */

public abstract class Peg extends Sprite{
    private String shape;

    /**
     * constructor
     * @param image
     * @param x
     * @param y
     */
    public Peg (String image, double x, double y ) {
        super(x, y, true, image);
    }

    /**
     * getter method for shape
     * @return
     */
    public String getShape(){return shape;}

    /**
     * setter method for shape
     * @param shape
     */
    public void setShape(String shape){this.shape=shape;}

    /**
     * abstract destroy method
     * concrete method is override in the subclasses of Peg
     * these subclasses override destroy method based on their expected action
     */
    public abstract void destroy();

}
