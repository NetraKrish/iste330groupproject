import objects.Account;

import javax.swing.*;
import java.awt.*;


public class iste330Group4PresentationLayerGUI {

    private iste330Group4DataLayer dl;

    private Account account;

    public iste330Group4PresentationLayerGUI() {

        this.dl = new iste330Group4DataLayer();
        this.account = null;
    }

    /**********************
     * Swing Helper Methods
     **********************/

    /**
     * method to add x amount of components
     *
     * method to create panels (JPane) with grid?
     *
     */

    public JPanel createPanel(int row, int col) {

        return new JPanel(new GridLayout(row, col));
    }

    public JPanel getPanelWithComponents(int row, int col, Component[] components) {

        JPanel panel = new JPanel((new GridLayout(row, col)));

        for(int i = 0; i < components.length; i++) {

            panel.add(components[i]);
        }

        return panel;
    }




}
