package com.yousefhaggy.fblamobileapp;

public class Achievement {
    public boolean isEarned;
    public int progress;
    public int max;
    public String description;
    //Constructor for count based achievements
    public Achievement(String description,int progress,int max){
        this.max=max;
        this.progress=progress;
        this.description=description;
        //If progress > max (for count based achievements) set earned as true
        if(progress>=max)
        {
            isEarned=true;
            this.progress=max;
        }
    }
    //Constructor for Yes/No achievements
    public Achievement(String description,boolean isEarned){
        this.max=1;
        this.progress= isEarned ? 1:0;
        this.description=description;
        this.isEarned=isEarned;
    }


}
