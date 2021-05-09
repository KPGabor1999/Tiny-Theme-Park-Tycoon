package Idlethemeparkworld.model;

import java.util.ArrayList;

public class News {
    private static News instance = null;
    private static final int MAX_NEWS_COUNT = 10;
    
    private final ArrayList<String> newsFeed;
    
    public static News getInstance() {
        if (instance == null) {
            instance = new News();
        }

        return instance;
    }
    
    private News(){
        this.newsFeed = new ArrayList();
    }
    
    public void reset(){
        newsFeed.clear();
    }
    
    public void addNews(String news){
        newsFeed.add(news);
        if(newsFeed.size() > MAX_NEWS_COUNT){
            newsFeed.remove(0);
        }
    }
    
    public ArrayList<String> getNews(){
        return newsFeed;
    }
}

