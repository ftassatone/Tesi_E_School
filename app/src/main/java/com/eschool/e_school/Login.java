package com.eschool.e_school;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by LenovoZ70 on 17/09/2017.
 */
public class Login extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView textView = (TextView) findViewById(R.id)
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
