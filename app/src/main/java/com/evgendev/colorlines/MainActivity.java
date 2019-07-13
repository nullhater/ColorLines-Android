package com.evgendev.colorlines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewGame(View view) {
        ColorLines colorLines = new ColorLines();
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("colorlines",colorLines);
        startActivity(intent);
    }
}
