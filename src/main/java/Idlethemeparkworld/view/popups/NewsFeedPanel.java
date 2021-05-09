package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.model.News;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class NewsFeedPanel extends JPanel {
    
    JTextArea textArea;
    ArrayList<JLabel> newsLines;

    public NewsFeedPanel() {
        this.newsLines = new ArrayList<>();
        
        JPanel newsFeed = new JPanel(new GridLayout(11, 1));
        newsFeed.add(new JLabel("NEWS FEED"));
        for (int i = 0; i < 10; i++) {
            JLabel newsLine = new JLabel();
            newsFeed.add(newsLine);
            newsLines.add(newsLine);
        }
        
        JScrollPane scrollPane = new JScrollPane(newsFeed); 
        add(scrollPane);
        
        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        timeTimer.start();
    }
    
    private void refresh(){
        ArrayList<String> news = News.getInstance().getNews();
        for (int i = 0; i < news.size(); i++) {
            newsLines.get(i).setText(news.get(news.size() - 1 - i));
        }
    }
}

