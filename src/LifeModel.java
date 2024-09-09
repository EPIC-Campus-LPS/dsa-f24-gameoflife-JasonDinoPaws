                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import java.util.Scanner;

/**
 * the Model for all cells
 */

public class LifeModel implements ActionListener
{

    /**
     *  This is the Model component.
     */

    public static final int SIZE = 60;
    private LifeCell[][] myGrid; //grid of cells
    private LifeView myView; //Jpanel that draws grid
    private Timer timer; //makes the animation possible

    // initial population from file or random if no file available

    /**
     * loads in the given file name
     * creates the grid and fills it with cells
     * sets some cells accoring to the file name to being alive
     */
    public LifeModel(LifeView view, String file)
    {
    	
    	// initialize 
        myGrid = new LifeCell[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++ )
            for (int c = 0; c < SIZE; c++ )
                myGrid[r][c] = new LifeCell();

        try
        {
        	File reader = new File(file);
        	Scanner infile = new Scanner(reader);

        	int numInitialCells = infile.nextInt();
            for (int count=0; count<numInitialCells; count++)
            {
                int r = infile.nextInt();
                int c = infile.nextInt();
                myGrid[r][c].setAliveNow(true);
            }
            infile.close();
        }
        catch (IOException e)
        {
        	// use random population
        	System.out.println("using a random setup");
                                                       
                for (LifeCell[] row: myGrid)
                    for ( LifeCell cell: row)
                        if ( Math.random() > 0.85)
                            cell.setAliveNow(true);
        }
        myView = view; //set the view
        myView.updateView(myGrid); //draw the grid
    }

    /**
     * This method stop the timer and animation
     */
    public void pause()
    {
        timer.stop();
    }
    
    /**
     * This method restarts the timer and animation
     */
    public void resume()
    {
        timer.restart();
    }
    
    /**
     * This does one generation of Life and stops
     */
    public void step()
    {
        oneGeneration();
        myView.updateView(myGrid);
    }
    
    /**
     * This starts the timer and sets speed
     */
    public void run()
    {
        timer = new Timer(75, this); //create timer and set delay
        timer.setCoalesce(true); //keep timer events even
        timer.start(); //start the timer
    }

   
    /**
     * This makes the animation.  Every time the timer fires
     * a new generation of life is created and drawn on the screen
     */
    public void actionPerformed(ActionEvent e)
    {
        oneGeneration();
        myView.updateView(myGrid);
    }

    /**
     * with a given row and col; it will check all 8 nearest neighbors and returns the amount that are alive
     */
    private int NumLiveNeighbors(int r, int c)
    {
        int alive = 0;
        int[][] nabs = {
                {r-1,c},{r-1,c-1},{r-1,c+1},
                {r+1,c},{r+1,c-1},{r+1,c+1},
                {r,c-1},{r,c+1}
        };

        for (int[] pos: nabs)
        {
            int x = pos[1];
            int y = pos[0];

            if (y > -1 && y < myGrid.length && x > -1 && x < myGrid[0].length)
                if (myGrid[x][y].alive_now)
                    alive ++;
        }

        return alive;
    }

    /**
     * will check all rows and cols for what neighbors are alive to see if it would be alive Next Generation
     *
     * Not alive Now and 3 neighbors are it will be alive next Gen
     * Less than 4 and greater than 1 neighbors alive it will be alive next Gen
     * else it will die next Gen
     */
    public void oneGeneration()
    {
        for (int r = 0; r < myGrid.length; r++)
            for (int c = 0; c < myGrid[0].length; c++)
            {
                int alive = NumLiveNeighbors(r,c);
                LifeCell cell = myGrid[r][c];

                if (!cell.alive_now && alive == 3)
                    cell.setAliveNext(true);
                else if (alive < 4 && alive > 1)
                    cell.setAliveNext(true);
                else
                    cell.setAliveNext(false);

            }
        updateNextGen();
    } 
    
    /**
     * Runs after oneGeneration() that just sets all cells Alive now Var based on the Alive next
     */
    private void updateNextGen() {
        for (int r = 0; r < myGrid.length; r++)
            for (int c = 0; c < myGrid[0].length; c++)
            {
                LifeCell cell = myGrid[r][c];

                cell.setAliveNow(cell.alive_next);
            }
    }
}

