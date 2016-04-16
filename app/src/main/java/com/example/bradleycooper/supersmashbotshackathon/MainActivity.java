package com.example.bradleycooper.supersmashbotshackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import java.net.URISyntaxException;




public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(getString(R.string.socketURI));
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.connect();


        final int colorDown = R.drawable.circle_battle_buttons_pressed;
        final int colorUp = R.drawable.circle_battle_buttons;

        final Button buttonFight = (Button)findViewById(R.id.buttonAttack);
        final Button buttonDefense = (Button)findViewById(R.id.buttonDefense);
        final Button buttonRotate = (Button)findViewById(R.id.buttonRotate);
        final Button buttonTaunt = (Button)findViewById(R.id.buttonTaunt);
        final Button buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });

        buttonFight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonFight.setBackgroundResource(colorDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        buttonFight.setBackgroundResource(colorUp);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        buttonFight.setBackgroundResource(colorUp);
                        break;
                }
                return true;
            }
        });
        buttonDefense.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonDefense.setBackgroundResource(colorDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        buttonDefense.setBackgroundResource(colorUp);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        buttonDefense.setBackgroundResource(colorUp);
                        break;
                }
                return true;
            }
        });
        buttonRotate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonRotate.setBackgroundResource(colorDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        displayResults("opponent");
                        buttonRotate.setBackgroundResource(colorUp);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        buttonRotate.setBackgroundResource(colorUp);
                        break;
                }
                return true;
            }
        });



        buttonTaunt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buttonTaunt.setBackgroundResource(colorDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        displayResults("user");
                        buttonTaunt.setBackgroundResource(colorUp);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        buttonTaunt.setBackgroundResource(colorUp);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayResults(String winner){
        final TextView textViewOpponentHealth = (TextView)findViewById(R.id.textViewOpponentHealth);
        final TextView textViewOpponentLabel = (TextView)findViewById(R.id.textViewOpponentLabel);
        final TextView textViewUserHealth = (TextView)findViewById(R.id.textViewUserHealth);
        final TextView textViewUserLabel = (TextView)findViewById(R.id.textViewUserLabel);

        View viewUser = (View)findViewById(R.id.viewUser);
        View viewOpponent = (View)findViewById(R.id.viewOpponent);

        if(winner.equalsIgnoreCase("user")) {
            textViewOpponentHealth.setText("Loser");
            viewOpponent.setBackgroundResource(R.drawable.circle_loser);
            textViewUserHealth.setText("Winner");
            viewUser.setBackgroundResource(R.drawable.circle_winner);
        }
        else if(winner.equalsIgnoreCase("opponent")){
            textViewOpponentHealth.setText("Winner");
            viewOpponent.setBackgroundResource(R.drawable.circle_winner);
            textViewUserHealth.setText("Loser");
            viewUser.setBackgroundResource(R.drawable.circle_loser);
        }

        final Button buttonFight = (Button)findViewById(R.id.buttonAttack);
        final Button buttonDefense = (Button)findViewById(R.id.buttonDefense);
        final Button buttonRotate = (Button)findViewById(R.id.buttonRotate);
        final Button buttonTaunt = (Button)findViewById(R.id.buttonTaunt);

        buttonFight.setVisibility(View.INVISIBLE);
        buttonDefense.setVisibility(View.INVISIBLE);
        buttonRotate.setVisibility(View.INVISIBLE);
        buttonTaunt.setVisibility(View.INVISIBLE);

        Button buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        buttonPlayAgain.setVisibility(View.VISIBLE);
    }
    public void playAgain(){
        final Button buttonFight = (Button)findViewById(R.id.buttonAttack);
        final Button buttonDefense = (Button)findViewById(R.id.buttonDefense);
        final Button buttonRotate = (Button)findViewById(R.id.buttonRotate);
        final Button buttonTaunt = (Button)findViewById(R.id.buttonTaunt);

        buttonFight.setVisibility(View.VISIBLE);
        buttonDefense.setVisibility(View.VISIBLE);
        buttonRotate.setVisibility(View.VISIBLE);
        buttonTaunt.setVisibility(View.VISIBLE);

        final TextView textViewOpponentHealth = (TextView)findViewById(R.id.textViewOpponentHealth);
        final TextView textViewUserHealth = (TextView)findViewById(R.id.textViewUserHealth);

        textViewOpponentHealth.setText("100%");
        textViewUserHealth.setText("100%");

        Button buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        buttonPlayAgain.setVisibility(View.INVISIBLE);

        View viewUser = (View)findViewById(R.id.viewUser);
        View viewOpponent = (View)findViewById(R.id.viewOpponent);
        viewOpponent.setBackgroundResource(R.drawable.circle_battle_buttons_layout);
        viewUser.setBackgroundResource(R.drawable.circle_battle_buttons_layout);
    }
}
