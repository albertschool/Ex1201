package com.videxedge.ex1201;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    public void updatebtn(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menuDataIn) {
            Intent t = new Intent(this, MainActivity.class);
            startActivity(t);
        } else if (id==R.id.menuWatch) {
            Intent t = new Intent(this, Watchtables.class);
            startActivity(t);
        }
            return true;
    }
}
