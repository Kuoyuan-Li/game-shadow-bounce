import bagel.*;

/**
 * provide template for all sprites (pegs, ball, bucket and power up)
 */
public abstract class Sprite {
    private double x;
    private double y;
    private Image image;
    private Boolean exist;

    /**
     * constructor, initialises basic sprite distributes
     * @param xInitial
     * @param yInitial
     * @param exist
     * @param filename
     */
    public Sprite(double xInitial, double yInitial, Boolean exist, String filename){
        x = xInitial;
        y= yInitial;
        this.exist = exist;
        this.image = new Image(filename);
    }

    /**
     * draw the sprite on the screen
     */
    public void render(){
        if(exist){
            image.draw(x, y);
        }
    }

    /**
     * setter method for image
     * @param imageName
     */
    public void setImage(String imageName) { this.image = new Image(imageName);}

    /**
     * getter method for image
     * @return
     */
    public Image getImage(){return image;}

    /**
     * getter methods for position, i.e. x and y
     * @return
     */
    public double getX() {return x;}
    public double getY() {return y;}

    /**
     * setter methods for position, i.e. x and y
     * @param x
     */
    public void setX(double x){this.x = x;};
    public void setY(double y){this.y = y;};

    /**
     * getter method for sprite existence
     * @return
     */
    public boolean getExist(){return exist;}

    /**
     * setter method for sprite existence
     * @param exist
     */
    public void setExist(boolean exist){this.exist=exist;}

    /**
     * move the sprite by the given distances along x and y
     * @param x
     * @param y
     */
    public void move(double x, double y){
        if(exist) {
            this.x += x;
            this.y += y;
        }
    }


}
