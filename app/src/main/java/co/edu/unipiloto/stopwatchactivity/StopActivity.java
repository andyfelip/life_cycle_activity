package co.edu.unipiloto.stopwatchactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class StopActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    private byte vuelta = 0;

    private int hora =0;
    private int TiempoTotal =0;

    private TextView vueltaTextView;
    private TextView horaTextView;
    private TextView tiempoTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_activity_main);

        vueltaTextView = (TextView) findViewById(R.id.vueltaTextView);
        horaTextView = (TextView) findViewById(R.id.horaTextView);
        tiempoTotalTextView = (TextView) findViewById(R.id.tiempoTotalTextView);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            vuelta = savedInstanceState.getByte("vuelta");
            hora = savedInstanceState.getInt("hora");
            TiempoTotal = savedInstanceState.getInt("tiempoTotal");
        }
        runTimer();
    }


    @Override
    public void onSaveInstanceState(Bundle savedIntanceState) {
        super.onSaveInstanceState(savedIntanceState);
        savedIntanceState.putInt("seconds", seconds);
        savedIntanceState.putBoolean("running", running);
        savedIntanceState.putBoolean("wasRunning", wasRunning);
        savedIntanceState.putByte("vuelta", vuelta);
        savedIntanceState.putInt("hora", hora);
        savedIntanceState.putInt("tiempoTotal", TiempoTotal);
    }
    @Override
    public void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
        vuelta =0;
        hora =0;
        TiempoTotal =0;
    }

    @Override
    public void onResume(){

        super.onResume();
        if(wasRunning){
            running = true;
        }
    }
    public void startButton(View view){running = true;}

    public void stopButton(View view){
        running = false;
    }

    public void resetButton(View view){
        running = false;
        seconds = 0;
        vuelta =0;
        hora=0;
        TiempoTotal=0;
        vueltaTextView.setText(getString(R.string.tiempoTotal));
        horaTextView.setText(getString(R.string.horas));
        tiempoTotalTextView.setText(getString(R.string.tiempoTotal));
    }
    public void vueltaButton(View view){
        running = true;
        vuelta ++;
        hora = (seconds%3600)/60;
        TiempoTotal = seconds;

        if(vuelta <= 5 ){

            vueltaTextView.append("\n" + vuelta);
            horaTextView.append("\n" + hora + " minuto ");
            tiempoTotalTextView.append("\n" + TiempoTotal + " Segundos ");

        }else{
            onPause();
        }


    }


    private void runTimer(){

        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds%3600) / 60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });


    }




}