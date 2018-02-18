package com.privat.kobbigal.donotdisturb;

import android.content.Context;
import android.view.View;

/**
 * Created by kobbigal on 2/18/18.
 */

class CustomOnClickListener implements View.OnClickListener, View.OnLongClickListener {

    private final Context context;

    CustomOnClickListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

}
