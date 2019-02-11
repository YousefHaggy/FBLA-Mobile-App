package com.yousefhaggy.fblamobileapp;

public class LevelInfo {
    private int level;
    private int maxExp;
    private int currentExp;

    public LevelInfo(int level, int maxExp, int currentExp) {
        this.level = level;
        this.maxExp = maxExp;
        this.currentExp = currentExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxExp() {
        return maxExp;
    }

    public void setMaxExp(int maxExp) {
        this.maxExp = maxExp;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }
}
