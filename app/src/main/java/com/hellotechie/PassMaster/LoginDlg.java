package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/11/15.
 */
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class LoginDlg extends Dialog implements OnClickListener {
    Button okButton;

    public LoginDlg(Context context) {
        super(context);
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the title */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.login_main);
        okButton = (Button) findViewById(R.id.OkButton);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /** When OK Button is clicked, dismiss the dialog */
        if (v == okButton)
            dismiss();
    }

}
