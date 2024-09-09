import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The start of it all
 */

public class Life extends JFrame implements ActionListener {
    /**
     * Declares variables
     * @view is the display's the grid of cells
     * @model is just a class that holds all data related to each cell
     * @JButtons are buttons
     * @files is just a array of all files that could be loaded
     */
    private static final long serialVersionUID = 1L;
    private LifeView view;
    private LifeModel model;
    private JButton runButton, pauseButton, resumeButton, stepButton;
    private String[] files = {"life100.txt","blinker_lif.dat","glgun13_lif.dat","penta_lif.dat","tumbler_lif.dat"};
    //private Timer timer;

    /**
     * It creates the JFrame
     * Picks a random file from the files area
     * creates all of the buttons
     * creates the View of all cells
     * and Creates the Model given the view
     */

    public Life() {
        super("Conway's Game of Life | File: ");
        String file = files[(int) (Math.random()*files.length)];
        super.setTitle("Conway's Game of Life | File: "+file);

        // build the buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        runButton.setEnabled(true);
        controlPanel.add(runButton);
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setEnabled(false);
        controlPanel.add(pauseButton);
        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        resumeButton.setEnabled(false);
        controlPanel.add(resumeButton);
        stepButton = new JButton("Step");
        stepButton.addActionListener(this);
        stepButton.setEnabled(true);
        controlPanel.add(stepButton);

        // build the view
        view = new LifeView();
        view.setBackground(Color.black);

        // put buttons, view together
        Container c = getContentPane();
        c.add(controlPanel, BorderLayout.NORTH);
        c.add(view, BorderLayout.CENTER);;

        // build the model
        model = new LifeModel(view,file);
    }

    /**
     * respond to each button press
     */
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        if (b == runButton) {
            model.run();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            stepButton.setEnabled(false);
        } else if (b == pauseButton) {
            model.pause();
            runButton.setEnabled(false);
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(true);
            stepButton.setEnabled(true);
        } else if (b == resumeButton) {
            model.resume();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
            stepButton.setEnabled(false);
        } else if (b == stepButton) {
            model.step();
            runButton.setEnabled(true);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        Life conway = new Life();
        conway.setDefaultCloseOperation(EXIT_ON_CLOSE);
        conway.setSize(570, 640);
        conway.setVisible(true);
    }


}
