package Idlethemeparkworld.view;

import static Idlethemeparkworld.model.BuildType.*;
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
    private JLabel hotDogNotBuilt;
    private JSlider hotDogPriceSlider;
    private JLabel iceCreamPriceLabel;
    private JLabel iceCreamNotBuilt;
    private JSlider iceCreamPriceSlider;
    private JLabel hamburgerPriceLabel;
    private JLabel hamburgerNotBuilt;
    private JSlider hamburgerPriceSlider;

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
            priceSettingsPanel.add(ticketPriceLabel);
            priceSettingsPanel.add(ticketPriceSlider);

            //Jégkrémes
            iceCreamPriceLabel = new JLabel("Ice cream prices:");
            priceSettingsPanel.add(iceCreamPriceLabel);
            int icecreamCheck = board.getGameManager().getPark().checkFoodPrice(ICECREAMPARLOR);
            if (icecreamCheck == 0) {
                iceCreamNotBuilt = new JLabel("Build an Ice Cream parlor");
                iceCreamNotBuilt.setForeground(Color.RED);
                priceSettingsPanel.add(iceCreamNotBuilt);
            } else {
                iceCreamPriceSlider = new JSlider(0, 30);
                iceCreamPriceSlider.setMajorTickSpacing(10);
                iceCreamPriceSlider.setMinorTickSpacing(1);
                iceCreamPriceSlider.setPaintTicks(true);
                iceCreamPriceSlider.setPaintLabels(true);
                iceCreamPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(ICECREAMPARLOR));
                priceSettingsPanel.add(iceCreamPriceSlider);
                //Slider Listener
                iceCreamPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateFoodPrice(ICECREAMPARLOR, value);
                        }
                    }
                });
            }

            //Hotdog
            hotDogPriceLabel = new JLabel("Hot dog prices:");
            priceSettingsPanel.add(hotDogPriceLabel);
            int hotdogCheck = board.getGameManager().getPark().checkFoodPrice(HOTDOGSTAND);
            if (hotdogCheck == 0) {
                hotDogNotBuilt = new JLabel("Build a Hot Dog stand");
                hotDogNotBuilt.setForeground(Color.RED);
                priceSettingsPanel.add(hotDogNotBuilt);
            } else {
                hotDogPriceSlider = new JSlider(0, 30);
                hotDogPriceSlider.setMajorTickSpacing(10);
                hotDogPriceSlider.setMinorTickSpacing(1);
                hotDogPriceSlider.setPaintTicks(true);
                hotDogPriceSlider.setPaintLabels(true);
                hotDogPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(HOTDOGSTAND));
                priceSettingsPanel.add(hotDogPriceSlider);
                //Slider Listener
                hotDogPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateFoodPrice(HOTDOGSTAND, value);
                        }
                    }
                });
            }

            //Hamburgeres
            hamburgerPriceLabel = new JLabel("Hamburger prices:");
            priceSettingsPanel.add(hamburgerPriceLabel);
            int hamburgerCheck = board.getGameManager().getPark().checkFoodPrice(BURGERJOINT);
            if (hamburgerCheck == 0) {
                hamburgerNotBuilt = new JLabel("Build a Burger joint");
                hamburgerNotBuilt.setForeground(Color.RED);
                priceSettingsPanel.add(hamburgerNotBuilt);
            } else {
                hamburgerPriceSlider = new JSlider(0, 30);
                hamburgerPriceSlider.setMajorTickSpacing(10);
                hamburgerPriceSlider.setMinorTickSpacing(1);
                hamburgerPriceSlider.setPaintTicks(true);
                hamburgerPriceSlider.setPaintLabels(true);
                hamburgerPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(BURGERJOINT));
                priceSettingsPanel.add(hamburgerPriceSlider);
                //Slider Listener
                hamburgerPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateFoodPrice(BURGERJOINT, value);
                        }
                    }
                });
            }

            this.getContentPane().add(priceSettingsPanel);

            employeesLabel = new JLabel("Employees");
            employeesLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(employeesLabel);

            employeeSettingsPanel = new JPanel(new GridLayout(3, 2));
            janitorNumberLabel = new JLabel("Number of janitors:");
            janitorNumberSlider = new JSlider(0, 5);
            janitorNumberSlider.setValue(board.getGameManager().getAgentManager().getJanitors().size());
            janitorNumberSlider.setMajorTickSpacing(1);
            janitorNumberSlider.setPaintTicks(true);
            janitorNumberSlider.setPaintLabels(true);
            maintainerNumberLabel = new JLabel("Number of repairmen:");
            maintainerNumberSlider = new JSlider(0, 5);
            maintainerNumberSlider.setValue(0);
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
                    }
                }
            });

            janitorNumberSlider.addChangeListener(new ChangeListener() {
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

            this.getContentPane().add(employeeSettingsPanel);

            this.pack();

            int locationY = (int) owner.getLocation().getY() + this.getHeight() / 2;
            int locationX = (int) owner.getLocation().getX() + this.getWidth() / 2;
            this.setLocation(locationX, locationY);

            this.setVisible(true);
        }
    }
}
