package com.mbokinala.windemereperiods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import static com.mbokinala.windemereperiods.PeriodManager.setSchedule;

public class MainActivity extends AppCompatActivity {

    Period perOne;
    Period perTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity act = this;

        final TextView currentPeriodTextView = (TextView) findViewById(R.id.currentPeriodTextView);
        final TextView timingTextView = (TextView) findViewById(R.id.timingTextView);
        final TextView minutesRemainingTextView = (TextView) findViewById(R.id.minutesRemainingTextView);

        updateCurrentPeriod(currentPeriodTextView);
        updateTimings(timingTextView);
        updateMinutesRemaining(minutesRemainingTextView);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateCurrentPeriod(currentPeriodTextView);
                                updateTimings(timingTextView);
                                updateMinutesRemaining(minutesRemainingTextView);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Log.d("Error", e.getStackTrace().toString());
                }
            }
        };

        t.start();

        setSchedule();
    }

    private void updateCurrentPeriod(TextView currentPeriodTextView) {
        Calendar now = Calendar.getInstance();
        String currentPeriod = PeriodManager.getPeriod(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

        if(currentPeriod.equals("Error")) {
            showErrorDialog();
        }

        if (currentPeriod.equals("School is Over")) {
            currentPeriodTextView.setText(currentPeriod);
        } else {
            currentPeriodTextView.setText(currentPeriod + " period");
        }

    }

    private void updateTimings(TextView timingTextView) {
        String timings = PeriodManager.getTimings();

        if(timings.equals("School is Over")) {
            timingTextView.setText("");
        } else {
            timingTextView.setText(timings);
        }
    }

    private void updateMinutesRemaining(TextView minutesRemainingTextView) {
        int minutesRemaining = PeriodManager.getMinutesRemaining();

        Log.d("Custom", "" + minutesRemaining);

        if(minutesRemaining >= 0) {
            minutesRemainingTextView.setText(""+minutesRemaining);
        } else {
            minutesRemainingTextView.setText("");
            TextView t = (TextView) findViewById(R.id.text2);
            t.setText("");
        }
    }

    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("There was an error. An error report was sent to the developer");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(1);
                    }
                });
        alertDialog.show();
    }

}
