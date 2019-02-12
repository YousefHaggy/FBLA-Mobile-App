package com.yousefhaggy.fblamobileapp;

public class Achievement {
    public boolean isEarned;
    public int progress;
    public int max;
    public String description;
    public Achievement(String description,int progress,int max){
        this.max=max;
        this.progress=progress;
        this.description=description;
        if(progress>=max)
        {
            isEarned=true;
            this.progress=max;
        }
    }

}
