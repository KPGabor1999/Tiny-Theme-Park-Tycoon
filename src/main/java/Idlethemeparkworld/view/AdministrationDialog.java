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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdministrationDialog extends JDialog {

    private Board board;

    private JLabel foodPricesLabel;
    private JLabel ticketPricesLabel;

    private JPanel entryPriceSettingPanel;
    private JPanel foodPriceSettingsPanel;
    private JPanel ticketPriceSettingsPanel;

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
    private JLabel carouselPriceLabel;
    private JLabel carouselNotBuilt;
    private JSlider carouselPriceSlider;
    private JLabel shipPriceLabel;
    private JLabel shipNotBuilt;
    private JSlider shipPriceSlider;
    private JLabel ferrisPriceLabel;
    private JLabel ferrisNotBuilt;
    private JSlider ferrisPriceSlider;
    private JLabel rollerPriceLabel;
    private JLabel rollerNotBuilt;
    private JSlider rollerPriceSlider;
    private JLabel hauntedPriceLabel;
    private JLabel hauntedNotBuilt;
    private JSlider hauntedPriceSlider;

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

            //Belépõ
            entryPriceSettingPanel = new JPanel(new GridLayout(1, 2));
            this.getContentPane().add(entryPriceSettingPanel);
            ticketPriceLabel = new JLabel("Entry fee:");
            ticketPriceSlider = new JSlider(0, 30);
            ticketPriceSlider.setMajorTickSpacing(10);
            ticketPriceSlider.setMinorTickSpacing(1);
            ticketPriceSlider.setPaintTicks(true);
            ticketPriceSlider.setPaintLabels(true);
            ticketPriceSlider.setValue(board.getGameManager().getEntranceFee());
            entryPriceSettingPanel.add(ticketPriceLabel);
            entryPriceSettingPanel.add(ticketPriceSlider);

            //Ételek
            foodPricesLabel = new JLabel("Food prices");
            foodPricesLabel.setAlignmentX(CENTER_ALIGNMENT);
            foodPricesLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
            this.getContentPane().add(foodPricesLabel);

            foodPriceSettingsPanel = new JPanel(new GridLayout(4, 2));
            this.getContentPane().add(foodPriceSettingsPanel);

            //Jégkrémes
            iceCreamPriceLabel = new JLabel("Ice cream prices:");
            iceCreamPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
            foodPriceSettingsPanel.add(iceCreamPriceLabel);
            int icecreamCheck = board.getGameManager().getPark().checkFoodPrice(ICECREAMPARLOR);
            if (icecreamCheck == 0) {
                iceCreamNotBuilt = new JLabel("Build an Ice Cream parlor");
                iceCreamNotBuilt.setForeground(Color.RED);
                foodPriceSettingsPanel.add(iceCreamNotBuilt);
            } else {
                iceCreamPriceSlider = new JSlider(0, 30);
                iceCreamPriceSlider.setMajorTickSpacing(10);
                iceCreamPriceSlider.setMinorTickSpacing(1);
                iceCreamPriceSlider.setPaintTicks(true);
                iceCreamPriceSlider.setPaintLabels(true);
                iceCreamPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(ICECREAMPARLOR));
                foodPriceSettingsPanel.add(iceCreamPriceSlider);
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
            hotDogPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
            foodPriceSettingsPanel.add(hotDogPriceLabel);
            int hotdogCheck = board.getGameManager().getPark().checkFoodPrice(HOTDOGSTAND);
            if (hotdogCheck == 0) {
                hotDogNotBuilt = new JLabel("Build a Hot Dog stand");
                hotDogNotBuilt.setForeground(Color.RED);
                foodPriceSettingsPanel.add(hotDogNotBuilt);
            } else {
                hotDogPriceSlider = new JSlider(0, 30);
                hotDogPriceSlider.setMajorTickSpacing(10);
                hotDogPriceSlider.setMinorTickSpacing(1);
                hotDogPriceSlider.setPaintTicks(true);
                hotDogPriceSlider.setPaintLabels(true);
                hotDogPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(HOTDOGSTAND));
                foodPriceSettingsPanel.add(hotDogPriceSlider);
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
            hamburgerPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
            foodPriceSettingsPanel.add(hamburgerPriceLabel);
            int hamburgerCheck = board.getGameManager().getPark().checkFoodPrice(BURGERJOINT);
            if (hamburgerCheck == 0) {
                hamburgerNotBuilt = new JLabel("Build a Burger joint");
                hamburgerNotBuilt.setForeground(Color.RED);
                foodPriceSettingsPanel.add(hamburgerNotBuilt);
            } else {
                hamburgerPriceSlider = new JSlider(0, 30);
                hamburgerPriceSlider.setMajorTickSpacing(10);
                hamburgerPriceSlider.setMinorTickSpacing(1);
                hamburgerPriceSlider.setPaintTicks(true);
                hamburgerPriceSlider.setPaintLabels(true);
                hamburgerPriceSlider.setValue(board.getGameManager().getPark().checkFoodPrice(BURGERJOINT));
                foodPriceSettingsPanel.add(hamburgerPriceSlider);
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

            //Attrakciók
            ticketPricesLabel = new JLabel("Activity prices");
            ticketPricesLabel.setAlignmentX(CENTER_ALIGNMENT);
            ticketPricesLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
            this.getContentPane().add(ticketPricesLabel);

            ticketPriceSettingsPanel = new JPanel(new GridLayout(6, 2));
            this.getContentPane().add(ticketPriceSettingsPanel);

            //Carousel
            carouselPriceLabel = new JLabel("Carousel prices:");
            carouselPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
            ticketPriceSettingsPanel.add(carouselPriceLabel);
            int carouselCheck = board.getGameManager().getPark().checkTicketPrice(CAROUSEL);
            if (carouselCheck == 0) {
                carouselNotBuilt = new JLabel("Build a Carousel");
                carouselNotBuilt.setForeground(Color.RED);
                ticketPriceSettingsPanel.add(carouselNotBuilt);
            } else {
                carouselPriceSlider = new JSlider(0, board.getGameManager().getPark().checkTicketPrice(CAROUSEL)*2);
                carouselPriceSlider.setMajorTickSpacing(10);
                carouselPriceSlider.setMinorTickSpacing(1);
                carouselPriceSlider.setPaintTicks(true);
                carouselPriceSlider.setPaintLabels(true);
                carouselPriceSlider.setValue(board.getGameManager().getPark().getTicketPrice(CAROUSEL));
                ticketPriceSettingsPanel.add(carouselPriceSlider);
                //Slider Listener
                carouselPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateTicketPrice(CAROUSEL, value);
                        }
                    }
                });
            }

            //Swinging ship
            shipPriceLabel = new JLabel("Swinging ship prices:");
            shipPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));            
            ticketPriceSettingsPanel.add(shipPriceLabel);
            int shipCheck = board.getGameManager().getPark().checkTicketPrice(SWINGINGSHIP);
            if (shipCheck == 0) {
                shipNotBuilt = new JLabel("Build a Swinging ship");
                shipNotBuilt.setForeground(Color.RED);
                ticketPriceSettingsPanel.add(shipNotBuilt);
            } else {
                shipPriceSlider = new JSlider(0, board.getGameManager().getPark().checkTicketPrice(SWINGINGSHIP)*2);
                shipPriceSlider.setMajorTickSpacing(10);
                shipPriceSlider.setMinorTickSpacing(1);
                shipPriceSlider.setPaintTicks(true);
                shipPriceSlider.setPaintLabels(true);
                shipPriceSlider.setValue(board.getGameManager().getPark().getTicketPrice(SWINGINGSHIP));
                ticketPriceSettingsPanel.add(shipPriceSlider);
                //Slider Listener
                shipPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateTicketPrice(SWINGINGSHIP, value);
                        }
                    }
                });
            }
            
            //Haunted mansion
            hauntedPriceLabel = new JLabel("Haunted mansion prices:");
            hauntedPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));               
            ticketPriceSettingsPanel.add(hauntedPriceLabel);
            int hauntedCheck = board.getGameManager().getPark().checkTicketPrice(HAUNTEDMANSION);
            if (hauntedCheck == 0) {
                hauntedNotBuilt = new JLabel("Build a Haunted mansion");
                hauntedNotBuilt.setForeground(Color.RED);
                ticketPriceSettingsPanel.add(hauntedNotBuilt);
            } else {
                hauntedPriceSlider = new JSlider(0, board.getGameManager().getPark().checkTicketPrice(HAUNTEDMANSION)*2);
                hauntedPriceSlider.setMajorTickSpacing(10);
                hauntedPriceSlider.setMinorTickSpacing(1);
                hauntedPriceSlider.setPaintTicks(true);
                hauntedPriceSlider.setPaintLabels(true);
                hauntedPriceSlider.setValue(board.getGameManager().getPark().getTicketPrice(HAUNTEDMANSION));
                ticketPriceSettingsPanel.add(hauntedPriceSlider);
                //Slider Listener
                hauntedPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateTicketPrice(HAUNTEDMANSION, value);
                        }
                    }
                });
            }

            //Ferriswheel
            ferrisPriceLabel = new JLabel("Ferriswheel prices:");
            ferrisPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));              
            ticketPriceSettingsPanel.add(ferrisPriceLabel);
            int ferrisCheck = board.getGameManager().getPark().checkTicketPrice(FERRISWHEEL);
            if (ferrisCheck == 0) {
                ferrisNotBuilt = new JLabel("Build a Ferriswheel");
                ferrisNotBuilt.setForeground(Color.RED);
                ticketPriceSettingsPanel.add(ferrisNotBuilt);
            } else {
                ferrisPriceSlider = new JSlider(0, board.getGameManager().getPark().checkTicketPrice(FERRISWHEEL)*2);
                ferrisPriceSlider.setMajorTickSpacing(10);
                ferrisPriceSlider.setMinorTickSpacing(1);
                ferrisPriceSlider.setPaintTicks(true);
                ferrisPriceSlider.setPaintLabels(true);
                ferrisPriceSlider.setValue(board.getGameManager().getPark().getTicketPrice(FERRISWHEEL));
                ticketPriceSettingsPanel.add(ferrisPriceSlider);
                //Slider Listener
                ferrisPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateTicketPrice(FERRISWHEEL, value);
                        }
                    }
                });
            }

            //Roller coaster
            rollerPriceLabel = new JLabel("Roller coaster prices:");
            rollerPriceLabel.setBorder(new EmptyBorder(3, 0, 3, 0));                 
            ticketPriceSettingsPanel.add(rollerPriceLabel);
            int rollerCheck = board.getGameManager().getPark().checkTicketPrice(ROLLERCOASTER);
            if (rollerCheck == 0) {
                rollerNotBuilt = new JLabel("Build a Roller coaster");
                rollerNotBuilt.setForeground(Color.RED);
                ticketPriceSettingsPanel.add(rollerNotBuilt);
            } else {
                rollerPriceSlider = new JSlider(0, board.getGameManager().getPark().checkTicketPrice(ROLLERCOASTER)*2);
                rollerPriceSlider.setMajorTickSpacing(10);
                rollerPriceSlider.setMinorTickSpacing(1);
                rollerPriceSlider.setPaintTicks(true);
                rollerPriceSlider.setPaintLabels(true);
                rollerPriceSlider.setValue(board.getGameManager().getPark().getTicketPrice(ROLLERCOASTER));
                ticketPriceSettingsPanel.add(rollerPriceSlider);
                //Slider Listener
                rollerPriceSlider.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent evt) {
                        JSlider slider = (JSlider) evt.getSource();
                        if (!slider.getValueIsAdjusting()) {
                            int value = slider.getValue();
                            //Action
                            board.getGameManager().getPark().updateTicketPrice(ROLLERCOASTER, value);
                        }
                    }
                });
            }
            
            //Employee
            employeesLabel = new JLabel("Employees");
            employeesLabel.setAlignmentX(CENTER_ALIGNMENT);
            employeesLabel.setBorder(new EmptyBorder(5,0,5,0));
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

            maintainerNumberSlider.addChangeListener(new ChangeListener() {
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
            this.setVisible(true);
        }
    }
}
