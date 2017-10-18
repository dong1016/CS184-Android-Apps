package edu.ucsb.he.dong.dhedrawingmultitouch;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawable;

    public static int[] colors;
    public static int[] temp = {0, 1, 2, 3};

    public int[] helper(){
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawable = (DrawingView)findViewById(R.id.drawing);
        Resources res = getResources();
        colors = res.getIntArray(R.array.paint_colors);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_clear){
            drawable = (DrawingView)findViewById(R.id.drawing);
            drawable.drawCanvas.drawARGB(255, 255, 255, 255);
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeColor1(View view){
        Button x = (Button)findViewById(R.id.button1);
        Random r = new Random();
        int result = r.nextInt(24);
        while(result == temp[1] || result == temp[2] || result == temp[3]){
            result = r.nextInt(24);
        }
        temp[0] = result;
        x.setBackgroundColor(colors[temp[0]]);
    }

    public void changeColor2(View view){
        Button x = (Button)findViewById(R.id.button2);
        Random r = new Random();
        int result = r.nextInt(24);
        while(result == temp[0] || result == temp[2] || result == temp[3]){
            result = r.nextInt(24);
        }
        temp[1] = result;
        x.setBackgroundColor(colors[temp[1]]);
    }

    public void changeColor3(View view){
        Button x = (Button)findViewById(R.id.button3);
        Random r = new Random();
        int result = r.nextInt(24);
        while(result == temp[0] || result == temp[1] || result == temp[3]){
            result = r.nextInt(24);
        }
        temp[2] = result;
        x.setBackgroundColor(colors[temp[2]]);
    }

    public void changeColor4(View view){
        Button x = (Button)findViewById(R.id.button4);
        Random r = new Random();
        int result = r.nextInt(24);
        while(result == temp[0] || result == temp[1] || result == temp[2]){
            result = r.nextInt(24);
        }
        temp[3] = result;
        x.setBackgroundColor(colors[temp[3]]);
    }

}
