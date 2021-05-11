package Idlethemeparkworld.model;

import java.util.ArrayList;

/**
 * A class responsible for handling and storing news connected to the park
 */
public class News {
    private static News instance = null;
    private static final int MAX_NEWS_COUNT = 10;
    
    private final ArrayList<String> newsFeed;
    
    /**
     * @return the only running instance of News
     */
    public static News getInstance() {
        if (instance == null) {
            instance = new News();
        }

        return instance;
    }
    
    /**
     * Constructor used for creating the only instance
     */
    private News(){
        this.newsFeed = new ArrayList();
    }
    
    /**
     * Resets the news feed, should be called every new game
     */
    public void reset(){
        newsFeed.clear();
    }
    
    /**
     * Add a new list into the system. If the feed is full, the oldest will be removed.
     * @param news 
     */
    public void addNews(String news){
        newsFeed.add(news);
        if(newsFeed.size() > MAX_NEWS_COUNT){
            newsFeed.remove(0);
        }
    }
    
    /**
     * @return The list of news
     */
    public ArrayList<String> getNews(){
        return newsFeed;
    }
}

