package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.model.GameManager;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FinancePanel extends JPanel {
    
    GameManager gm;
    JTextArea textArea;

    public FinancePanel(GameManager gm) {
        this.gm = gm;
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel();
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel title = new JLabel("Finances");

        JButton resetButton = new javax.swing.JButton();
        resetButton.setText("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.getFinance().resetFinanceData();
                refresh();
            }
        });
        topPanel.add(title);
        topPanel.add(resetButton);
       
        informationPanel.add(topPanel);
        
        textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);
        refresh();
        informationPanel.add(scrollPane);
        tabbedPane.addTab("Information", informationPanel);
        tabbedPane.setPreferredSize(new Dimension(400, 350));
        
        JPanel loanPanel = new JPanel();
        loanPanel.setLayout(new BoxLayout(loanPanel, BoxLayout.Y_AXIS));
        loanPanel.setPreferredSize(new Dimension(400, 350));
        
        if(gm.getFinance().getPrincipal() != -1){
            loanPanel.add(new JLabel("Principal: " + gm.getFinance().getPrincipal()));
            loanPanel.add(new JLabel("Total to pay back: " + gm.getFinance().getCalculatedPay()));
            loanPanel.add(new JLabel("Interest rate(5 days): " + gm.getFinance().getInterestRate()));
            loanPanel.add(new JLabel("Daily payback: " + gm.getFinance().getDailyFee()));
            loanPanel.add(new JLabel("Payback progress: " + gm.getFinance().getDayProgress() + "/" + gm.getFinance().getPaybackDuration() + " days" ));
        } else {
            JTextArea loanInfo = new JTextArea(5, 20);
            loanInfo.setEditable(false);
            loanInfo.setText("Take out a loan based on a 5 day interest rate.\n"
                    + "The amount to take out has to be between 0.1 x funds and 1 x funds.\n"
                    + "The interest rate will decrease the more you take out, or the longer you pay it back for.");
            loanInfo.setLineWrap(true);
            loanInfo.setWrapStyleWord(true);
            loanPanel.add(loanInfo);

            JTextField valueInput = new JTextField(10);
            JButton submitButton = new JButton("Submit");
            JSlider daySlider = new JSlider(5, 20);
            daySlider.setMajorTickSpacing(5);
            daySlider.setMinorTickSpacing(1);
            daySlider.setPaintTicks(true);
            daySlider.setPaintLabels(true);
            loanPanel.add(daySlider);
            loanPanel.add(valueInput);
            loanPanel.add(submitButton);
            JLabel error = new JLabel("");
            loanPanel.add(error);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int value = Integer.parseInt(valueInput.getText());
                        if(gm.getFinance().getLoan(value, daySlider.getValue())){
                            JComponent comp = (JComponent) e.getSource();
                            Window win = SwingUtilities.getWindowAncestor(comp);
                            win.dispose();
                        } else {
                            error.setText("Invalid value!");
                        }
                    } catch (NumberFormatException ex) {
                        error.setText("Not a number!");
                    }
                }
            });
        }
        
        tabbedPane.addTab("Loan", loanPanel);
        
        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        timeTimer.start();
        
        add(tabbedPane);
    }
    
    private void refresh(){
        textArea.setText(gm.getFinance().getFinancialDataString());
    }
}

