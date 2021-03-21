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
    private JLabel securityGuardNumberLabel;
    private JSlider securityGuardNumberSlider;

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

            pricesLabel = new JLabel("Árak");
            pricesLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(pricesLabel);

            priceSettingsPanel = new JPanel(new GridLayout(5, 2));
            ticketPriceLabel = new JLabel("Belépõjegy ára:");
            ticketPriceSlider = new JSlider(0, 30);
            ticketPriceSlider.setMajorTickSpacing(10);
            ticketPriceSlider.setMinorTickSpacing(1);
            ticketPriceSlider.setPaintTicks(true);
            ticketPriceSlider.setPaintLabels(true);
            hotDogPriceLabel = new JLabel("Hot dogok ára:");
            hotDogPriceSlider = new JSlider(0, 30);
            hotDogPriceSlider.setMajorTickSpacing(10);
            hotDogPriceSlider.setMinorTickSpacing(1);
            hotDogPriceSlider.setPaintTicks(true);
            hotDogPriceSlider.setPaintLabels(true);
            iceCreamPriceLabel = new JLabel("Fagyi ára:");
            iceCreamPriceSlider = new JSlider(0, 30);
            iceCreamPriceSlider.setMajorTickSpacing(10);
            iceCreamPriceSlider.setMinorTickSpacing(1);
            iceCreamPriceSlider.setPaintTicks(true);
            iceCreamPriceSlider.setPaintLabels(true);
            hamburgerPriceLabel = new JLabel("Hamburger ára:");
            hamburgerPriceSlider = new JSlider(0, 30);
            hamburgerPriceSlider.setMajorTickSpacing(10);
            hamburgerPriceSlider.setMinorTickSpacing(1);
            hamburgerPriceSlider.setPaintTicks(true);
            hamburgerPriceSlider.setPaintLabels(true);
            fishChipsPriceLabel = new JLabel("Fish & Chips ára:");
            fishChipsPriceSlider = new JSlider(0, 30);
            fishChipsPriceSlider.setMajorTickSpacing(10);
            fishChipsPriceSlider.setMinorTickSpacing(1);
            fishChipsPriceSlider.setPaintTicks(true);
            fishChipsPriceSlider.setPaintLabels(true);

            priceSettingsPanel.add(ticketPriceLabel);
            priceSettingsPanel.add(ticketPriceSlider);
            priceSettingsPanel.add(hotDogPriceLabel);
            priceSettingsPanel.add(hotDogPriceSlider);
            priceSettingsPanel.add(iceCreamPriceLabel);
            priceSettingsPanel.add(iceCreamPriceSlider);
            priceSettingsPanel.add(hamburgerPriceLabel);
            priceSettingsPanel.add(hamburgerPriceSlider);
            priceSettingsPanel.add(fishChipsPriceLabel);
            priceSettingsPanel.add(fishChipsPriceSlider);
            this.getContentPane().add(priceSettingsPanel);

            employeesLabel = new JLabel("Alkalmazottak");
            employeesLabel.setAlignmentX(CENTER_ALIGNMENT);
            this.getContentPane().add(employeesLabel);

            employeeSettingsPanel = new JPanel(new GridLayout(3, 2));
            janitorNumberLabel = new JLabel("Takarítók száma:");
            janitorNumberSlider = new JSlider(0, 10);
            janitorNumberSlider.setMajorTickSpacing(10);
            janitorNumberSlider.setMinorTickSpacing(1);
            janitorNumberSlider.setPaintTicks(true);
            janitorNumberSlider.setPaintLabels(true);
            maintainerNumberLabel = new JLabel("Karbantartók száma:");
            maintainerNumberSlider = new JSlider(0, 10);
            maintainerNumberSlider.setMajorTickSpacing(10);
            maintainerNumberSlider.setMinorTickSpacing(1);
            maintainerNumberSlider.setPaintTicks(true);
            maintainerNumberSlider.setPaintLabels(true);
            securityGuardNumberLabel = new JLabel("Biztonsági õrök száma:");
            securityGuardNumberSlider = new JSlider(0, 10);
            securityGuardNumberSlider.setMajorTickSpacing(10);
            securityGuardNumberSlider.setMinorTickSpacing(1);
            securityGuardNumberSlider.setPaintTicks(true);
            securityGuardNumberSlider.setPaintLabels(true);

            employeeSettingsPanel.add(janitorNumberLabel);
            employeeSettingsPanel.add(janitorNumberSlider);
            employeeSettingsPanel.add(maintainerNumberLabel);
            employeeSettingsPanel.add(maintainerNumberSlider);
            employeeSettingsPanel.add(securityGuardNumberLabel);
            employeeSettingsPanel.add(securityGuardNumberSlider);

            //Listeners and event handlers for sliders
            ticketPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        board.getPark().setEntranceFee(value);
                        //System.out.println(value);
                    }
                }
            });

            hotDogPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                    }
                }
            });
            iceCreamPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                    }
                }
            });
            hamburgerPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                    }
                }
            });
            fishChipsPriceSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent evt) {
                    JSlider slider = (JSlider) evt.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        int value = slider.getValue();
                        //Action
                    }
                }
            });

            this.getContentPane().add(employeeSettingsPanel);

            this.pack();

            int screenHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2;
            int screenWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2;
            //System.out.println(screenHeight + " " + screenWidth);
            this.setLocation(screenWidth, screenHeight);

            this.setVisible(true);
        }
    }
}
