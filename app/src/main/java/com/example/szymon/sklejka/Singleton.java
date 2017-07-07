package com.example.szymon.sklejka;

/**
 * Created by Szymon on 07.07.2017.
 */

public class Singleton {
    private static Singleton mInstance= null;

    public String name,level;
    public int drake_lvl,drake_hp;
    public boolean battle_result,battle_result_to_check,data_to_update;

    protected Singleton(){}

    public static synchronized Singleton getInstance(){
        if(null == mInstance){
            mInstance = new Singleton();
        }
        return mInstance;
    }
}
