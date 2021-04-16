package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.view.BuildingOptionsDialog;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Confirm extends JDialog {

    public Confirm(Window o, ActionListener al) {
        super(o, "Confirm");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JLabel confirmDemolitionLabel = new JLabel("Are you sure?");
        confirmDemolitionLabel.setAlignmentX(CENTER_ALIGNMENT);
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton yesButton = new JButton("YES");
        yesButton.addActionListener(al);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Confirm.this.dispose();
            }
        });
        JButton noButton = new JButton("NO");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Confirm.this.dispose();
            }
        });

        this.getContentPane().add(confirmDemolitionLabel);
        optionsPanel.add(yesButton);
        optionsPanel.add(noButton);
        this.getContentPane().add(optionsPanel);
        this.pack();
        int xLocation = this.getOwner().getX() + this.getOwner().getWidth() / 2 - this.getWidth() / 2;
        int yLocation = this.getOwner().getY() + this.getOwner().getHeight() / 2 - this.getHeight() / 2;
        this.setLocation(xLocation, yLocation);

        this.setVisible(true);
    }
}
