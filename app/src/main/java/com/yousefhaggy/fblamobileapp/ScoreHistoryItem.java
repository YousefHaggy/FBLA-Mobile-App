package com.yousefhaggy.fblamobileapp;

public class ScoreHistoryItem {
    private  String itemTitle;
    private  double itemScore;
    public ScoreHistoryItem(String itemTitle, double itemScore) {
        this.itemTitle = itemTitle;
        this.itemScore=itemScore;
    }
    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public double getItemScore() {
        return itemScore;
    }

    public void setItemScore(double itemScore) {
        this.itemScore = itemScore;
    }



}
