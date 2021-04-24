package Idlethemeparkworld.view.popups;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreditPanel extends JPanel {

    public CreditPanel() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Korom Pál Gábor"));
        JLabel hyperlink1 = new JLabel("Personal website");
        hyperlink1.setForeground(Color.BLUE.darker());
        hyperlink1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://korompalgabor.webnode.hu/"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        add(hyperlink1);
        add(new JLabel("Schmidt Richárd"));
        JLabel hyperlink2 = new JLabel("Personal website");
        hyperlink2.setForeground(Color.BLUE.darker());
        hyperlink2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://enony.info/"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        add(hyperlink2);
        add(new JLabel("Tran Bach Khoa"));
        JLabel hyperlink3 = new JLabel("Personal website");
        hyperlink3.setForeground(Color.BLUE.darker());
        hyperlink3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hyperlink3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://tbkhoa.web.elte.hu/"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JLabel githublink = new JLabel("Github");
        githublink.setForeground(Color.BLUE.darker());
        githublink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        githublink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Yazurai"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JPanel links = new JPanel();
        links.setLayout(new GridLayout(2, 1));
        links.add(hyperlink3);
        links.add(githublink);
        add(links);
        this.setPreferredSize(new Dimension(400, 200));
    }
}

