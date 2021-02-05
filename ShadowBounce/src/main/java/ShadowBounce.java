import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Simple Bagel game.
 **
 * Modified Eleanor McMurtry Work
 * @author Kuoyuan Li, 13/10/3019
 */
public class ShadowBounce extends AbstractGame {
    private static final int BALL_TOUCH_BUCKET_CHANCE = 3;
    private static final int BOARD_MAX_INDEX =5;
    private static final int SHOTS = 20;
    private  Board currentBoard;
    private Board[] boards = new Board[BOARD_MAX_INDEX];
    private int currentBoardIndex=0;
    private int requireNewGreenPeg = 0;
    private int requireNewPowerUp = 0;
    private boolean[] ballTouchBucket;
    private int shots;

    /**
     * game constructor
     * create board list and all 5 boards based on the input file
     * initialise the current board
     * @param shots
     * @param filename
     */
    public ShadowBounce(int shots,String[] filename) {
        ballTouchBucket = new boolean[BALL_TOUCH_BUCKET_CHANCE];
        this.shots = shots;
        for(int i =0; i<BOARD_MAX_INDEX;i++) {
            boards[i] = new Board();
            processInputFile(filename[i], i);
        }
        currentBoardIndex=0;
        currentBoard = boards[currentBoardIndex];
        currentBoard.generateRedPegs();
        currentBoard.generateGreenPeg();
        requireNewGreenPeg = -1;
        requireNewPowerUp = -1;
        for(int i=0; i < BALL_TOUCH_BUCKET_CHANCE;i++){
            ballTouchBucket[i]=false;
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        String[] files = {"res/0.csv","res/1.csv","res/2.csv","res/3.csv","res/4.csv"};
        ShadowBounce game = new ShadowBounce(SHOTS, files );
        game.run();
    }



    /**
     * Performs a state update.
     * @param input
     */
    @Override

    public void update(Input input) {

        currentBoard.spritesMove();
        currentBoard.renderBoard();
        /**
        * game over check
        */
        if(input.wasPressed(Keys.ESCAPE) || shots <=0){
            Window.close();
        }

        /**
         * check if the ball should re-appear and a new turn starts
         */
        if(input.wasPressed(MouseButtons.LEFT)) {
            if(currentBoard.ballsAppearAndMove(input.getMouseX(), input.getMouseY())){
                for(int i=0; i < BALL_TOUCH_BUCKET_CHANCE;i++){
                    ballTouchBucket[i]=false;
                }
                requireNewGreenPeg = -1;
                requireNewPowerUp = -1;
            }
        }

        currentBoard.eachFrameChecks();

        /**
         * check if all balls fall off and the current turn ends
         */
        if(currentBoard.ballsClear()){
            currentBoard.turnOffFireMode();
            shots --;
            System.out.println("Remaining shots:" + shots);
            requireNewPowerUp ++;
            requireNewGreenPeg ++;
        }

        /**
         * check if the power up and green peg need to change their position
         */
        if(requireNewGreenPeg == 0 ){
            currentBoard.resetGreenPeg();
            requireNewGreenPeg = 1;
        }

        if(requireNewPowerUp == 0){
            currentBoard.resetPowerUp();
            requireNewPowerUp = 1;
        }
        /**
         * check if bucket catches balls
         */
        for(int i=0; i < BALL_TOUCH_BUCKET_CHANCE;i++){
            if(!ballTouchBucket[i]){
                ballTouchBucket[i] = currentBoard.bucketTouchCheck(i);
                if(ballTouchBucket[i]){
                    shots ++;
                }
            }
        }

        /**
         * check if the current board is clear
         * Proceed to next board when the current board was cleared
         */
        if(currentBoard.getRedPegCount() <= 0 ){
            currentBoardIndex ++;
            if(currentBoardIndex >= BOARD_MAX_INDEX){
                System.out.println("Game Over");
                Window.close();
                System.exit(0);
            }
            currentBoard=boards[currentBoardIndex];
            currentBoard.generateRedPegs();
            currentBoard.generateGreenPeg();
            requireNewGreenPeg = -1;
            requireNewPowerUp = -1;
            for(int i=0; i < BALL_TOUCH_BUCKET_CHANCE;i++){
                ballTouchBucket[i]=false;
            }
        }
    }


    /**
     * process the input file, store the pegs in the specific board (specified by boardIndex)
     * @param filename
     * @param boardIndex
     */
    public void processInputFile(String filename,int boardIndex){
        try(BufferedReader br= new BufferedReader(new FileReader(filename))){
            String text;
            while((text = br.readLine())!= null){
                String comma = ",";
                String[] cols = text.split(comma);
                boards[boardIndex].addNewPegs(cols[0],Double.parseDouble(cols[1]),Double.parseDouble(cols[2]));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
