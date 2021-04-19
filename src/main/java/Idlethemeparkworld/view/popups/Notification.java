package Idlethemeparkworld.view.popups;

import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Notification extends JDialog {

    public Notification(Window o, String title, String msg) {
        super(o, msg);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel errorMessage = new JLabel(msg);
        errorMessage.setAlignmentX(CENTER_ALIGNMENT);
        JButton OKButton = new JButton("OK");
        OKButton.setAlignmentX(CENTER_ALIGNMENT);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notification.this.dispose();
            }
        });

        this.getContentPane().add(errorMessage);
        this.getContentPane().add(OKButton);

        this.pack();

        this.setLocationRelativeTo(this.getOwner());

        this.setVisible(true);
    }
}
