package Idlethemeparkworld.view;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.JDialog;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdministrationDialog extends JDialog {

    private Board board;

    private JLabel pricesLabel;

    private JPanel priceSettingsPanel;
    private JLabel ticketPriceLabel;
    private JSlider ticketPriceSlider;
    private JLabel hotDogPriceLabel;
    private JSlider hotDogPriceSlider;
    private JLabel iceCreamPriceLabel;
    private JSlider iceCreamPriceSlider;
    private JLabel hamburgerPriceLabel;
    private JSlider hamburgerPriceSlider;
    private JLabel fishChipsPriceLabel;
    private JSlider fishChipsPriceSlider;

    private JLabel employeesLabel;

    private JPanel employeeSettingsPanel;
    private JLabel janitorNumberLabel;
    private JSlider janitorNumberSlider;
    private JLabel maintainerNumberLabel;
    private JSlider maintainerNumberSlider;

    private static int instanceCount;

    public AdministrationDialog(Frame owner, String title, Board board) {
        super(owner, title);
        if (instanceCount == 0) {
            instanceCount++;
            this.board = board;

            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AdministrationDialog.this.instanceCount--;
                    AdministrationDialog.this.dispose();
                }

            });
            this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), Y_AXIS));

            pricesLabel = new JLabel("Prices");
            pricesLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(pricesLabel);
            //Belépõ
            priceSettingsPanel = new JPanel(new GridLayout(5, 2));
            ticketPriceLabel = new JLabel("Entry fee:");
            ticketPriceSlider = new JSlider(0, 30);
            ticketPriceSlider.setMajorTickSpacing(10);
            ticketPriceSlider.setMinorTickSpacing(1);
            ticketPriceSlider.setPaintTicks(true);
            ticketPriceSlider.setPaintLabels(true);
            ticketPriceSlider.setValue(board.getGameManager().getEntranceFee());
            //Hotdog
            hotDogPriceLabel = new JLabel("Hot dog prices:");
            hotDogPriceSlider = new JSlider(0, 30);
            hotDogPriceSlider.setMajorTickSpacing(10);
            hotDogPriceSlider.setMinorTickSpacing(1);
            hotDogPriceSlider.setPaintTicks(true);
            hotDogPriceSlider.setPaintLabels(true);
            hotDogPriceSlider.setValue(board.getGameManager().getHotdogPrice());
            //Jégkrémes
            iceCreamPriceLabel = new JLabel("Ice cream prices:");
            iceCreamPriceSlider = new JSlider(0, 30);
            iceCreamPriceSlider.setMajorTickSpacing(10);
            iceCreamPriceSlider.setMinorTickSpacing(1);
            iceCreamPriceSlider.setPaintTicks(true);
            iceCreamPriceSlider.setPaintLabels(true);
            iceCreamPriceSlider.setValue(board.getGameManager().getIcecreamPrice());
            //Hamburgeres
            hamburgerPriceLabel = new JLabel("Hamburger prices:");
            hamburgerPriceSlider = new JSlider(0, 30);
            hamburgerPriceSlider.setMajorTickSpacing(10);
            hamburgerPriceSlider.setMinorTickSpacing(1);
            hamburgerPriceSlider.setPaintTicks(true);
            hamburgerPriceSlider.setPaintLabels(true);
            hamburgerPriceSlider.setValue(board.getGameManager().getHamburgerPrice());

            priceSettingsPanel.add(ticketPriceLabel);
            priceSettingsPanel.add(ticketPriceSlider);
            priceSettingsPanel.add(hotDogPriceLabel);
            priceSettingsPanel.add(hotDogPriceSlider);
            priceSettingsPanel.add(iceCreamPriceLabel);
            priceSettingsPanel.add(iceCreamPriceSlider);
            priceSettingsPanel.add(hamburgerPriceLabel);
            priceSettingsPanel.add(hamburgerPriceSlider);
            this.getContentPane().add(priceSettingsPanel);

            employeesLabel = new JLabel("Employees");
            employeesLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(employeesLabel);

            employeeSettingsPanel = new JPanel(new GridLayout(3, 2));
            janitorNumberLabel = new JLabel("Number of cleaners:");
            janitorNumberSlider = new JSlider(0, 5);
            janitorNumberSlider.setValue(board.getGameManager().getAgentManager().getJanitors().size());
            janitorNumberSlider.setMajorTickSpacing(1);
            janitorNumberSlider.setPaintTicks(true);
            janitorNumberSlider.setPaintLabels(true);
            maintainerNumberLabel = new JLabel("Number of repairmen:");
            maintainerNumberSlider = new JSlider(0, 5);
            maintainerNumberSlider.setValue(board.getGameManager().getAgentManager().getMaintainers().size());
            maintainerNumberSlider.setMajorTickSpacing(1);
            maintainerNumberSlider.setPaintTicks(true);
            maintainerNumberSlider.setPaintLabels(true);

            employeeSettingsPanel.add(janitorNumberLabel);
            employeeSettingsPanel.add(janitorNumberSlider);
            employeeSettingsPanel.add(maintainerNumberLabel);
            employeeSettingsPanel.add(maintainerNumberSlider);

            //Listeners and event handlers for sliders
            ticketPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        board.getGameManager().setEntranceFee(value);
                        board.getGameManager().updateFoodPrices();
                    }
                }
            });

            hotDogPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                        board.getGameManager().setHotdogPrice(value);
                        board.getGameManager().updateFoodPrices();
                    }
                }
            });
            iceCreamPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                        board.getGameManager().setIcecreamPrice(value);
                        board.getGameManager().updateFoodPrices();
                    }
                }
            });
            hamburgerPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                        board.getGameManager().setHamburgerPrice(value);
                        board.getGameManager().updateFoodPrices();
                    }
                }
            });
      
            janitorNumberSlider.addChangeListener(new ChangeListener(){
                @Override
                public void stateChanged(ChangeEvent event) {
                    //A beállított érték alapján menedzseljük a takarítók listáját.
                    JSlider slider = (JSlider) event.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int numberOfJanitors = slider.getValue();
                        //Action
                        board.getGameManager().getAgentManager().manageJanitors(numberOfJanitors);
                    }
                }
                
            });
            
            maintainerNumberSlider.addChangeListener(new ChangeListener(){
                @Override
                public void stateChanged(ChangeEvent event) {
                    //A beállított érték alapján menedzseljük a takarítók listáját.
                    JSlider slider = (JSlider) event.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int numberOfMaintainers = slider.getValue();
                        //Action
                        board.getGameManager().getAgentManager().manageMaintainers(numberOfMaintainers);
                    }
                }
                
            });

            this.getContentPane().add(employeeSettingsPanel);

            this.pack();

            int locationY = (int) owner.getLocation().getY() + this.getHeight()/ 2;
            int locationX = (int) owner.getLocation().getX() + this.getWidth()/ 2;
            this.setLocation(locationX, locationY);

            this.setVisible(true);
        }
    }
}
