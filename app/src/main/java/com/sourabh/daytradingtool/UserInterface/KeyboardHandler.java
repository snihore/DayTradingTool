package com.sourabh.daytradingtool.UserInterface;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardHandler {

    public static void closeKeyboard(Activity activity, EditText view){

        try {

            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
