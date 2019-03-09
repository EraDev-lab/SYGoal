package com.example.al_kahtani.sygoal.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aws on 21/12/2017.
 */

public class SharedPrefNoty {
    SharedPreferences mySharedPrefNotify ;
    public SharedPrefNoty(Context context) {
        mySharedPrefNotify = context.getSharedPreferences("filename1",Context.MODE_PRIVATE);
    }
    // this method will save the nightMode State : True or False




    public void setNotifyState(Boolean state) {
        SharedPreferences.Editor editor1 = mySharedPrefNotify.edit();
        editor1.putBoolean("Notify",state);
        editor1.commit();
    }
    // this method will load the Night Mode State
    public Boolean loadNotifyState (){
        Boolean state1 = mySharedPrefNotify.getBoolean("Notify",false);
        return  state1;
    }
}
