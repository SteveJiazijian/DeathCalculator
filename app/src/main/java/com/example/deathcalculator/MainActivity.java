package com.example.deathcalculator;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deathcalculator.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText birthdayText;
    private EditText deathText;
    private EditText yearsLived;
    private EditText monthLived;
    private EditText daysLived;
    private Button compute;
    private Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compute =  findViewById(R.id.compute);
        birthdayText  = findViewById(R.id.birthday);
        deathText = findViewById(R.id.death);
        yearsLived = findViewById(R.id.YearsL);
        monthLived = findViewById(R.id.MonthL);
        daysLived = findViewById(R.id.DaysL);
        reset = findViewById(R.id.reset);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");


        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthday = birthdayText.getText().toString();
                String death =  deathText.getText().toString();
                String yLived =  yearsLived.getText().toString();
                String mLived = monthLived.getText().toString();
                String dLived = daysLived.getText().toString();

                //check if all fields are empty if true pop up message
                if (yLived.matches("") && mLived.matches("") && dLived.matches("") && birthday.matches("") && death.matches("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Try Again");
                    alertDialog.setMessage("Enter at least two information");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                try{
                    /**
                     * User give in birthday and years Lived find death Date
                     */
                    if(death.matches("")){
                        Calendar calBi = Calendar.getInstance();
                        Date bi = dateFormat.parse(birthday);
                        calBi.setTime(bi);
                        int livedY = Integer.parseInt(yLived);
                        int livedM = Integer.parseInt(mLived);
                        int livedD = Integer.parseInt(dLived);

                        death = birthday;
                        Calendar calDe = Calendar.getInstance();
                        Date de = dateFormat.parse(death);
                        calDe.setTime(de);

                        calDe.add(Calendar.DAY_OF_MONTH, livedD);
                        calDe.add(Calendar.MONTH, livedM);
                        calDe.add(Calendar.YEAR, livedY);

                        // parse the calendar object back to date formatted it the correct way and parse it back to String
                        Date resultDate = calDe.getTime();
                        String resultString = dateFormat.format(resultDate);
                        deathText.setText(resultString);

                        /**
                         * User give Death date and years lived find birthday
                         */
                    }else if(birthday.matches("")){
                        Calendar calDe = Calendar.getInstance();
                        Date de = dateFormat.parse(death);
                        calDe.setTime(de);
                        int livedY = Integer.parseInt(yLived);
                        int livedM = Integer.parseInt(mLived);
                        int livedD = Integer.parseInt(dLived);
                        // convert positive to negative to add to the calendar object
                        int subLivedY = 0 - livedY;
                        int subLivedM = 0 - livedM;
                        int subLivedD = 0 - livedD;

                        birthday = death;
                        Calendar calBi = Calendar.getInstance();
                        Date bi = dateFormat.parse(birthday);
                        calBi.setTime(bi);

                        calBi.add(Calendar.DAY_OF_MONTH, subLivedD);
                        calBi.add(Calendar.MONTH, subLivedM);
                        calBi.add(Calendar.YEAR, subLivedY);

                        Date resultDate = calBi.getTime();
                        String resultString = dateFormat.format(resultDate);
                        birthdayText.setText(resultString);

                        /**
                         * give birthday and death date find years lived
                         */
                    }else if (yLived.matches("") && mLived.matches("") && dLived.matches("")){

                        Calendar calBi = Calendar.getInstance();
                        Date bi = dateFormat.parse(birthday);
                        calBi.setTime(bi);
                        Calendar calDe = Calendar.getInstance();
                        Date de = dateFormat.parse(death);
                        calDe.setTime(de);
                        if(calBi.before(calDe)) {
                            int yearsInBetween = calDe.get(Calendar.YEAR) - calBi.get(Calendar.YEAR);
                            int Month = Math.abs(calDe.get(Calendar.MONTH) - calBi.get(Calendar.MONTH));
                            int days = Math.abs(calDe.get(Calendar.DAY_OF_MONTH) - calBi.get(Calendar.DAY_OF_MONTH));
                            yearsLived.setText(Integer.toString(yearsInBetween));
                            monthLived.setText(Integer.toString(Month));
                            daysLived.setText(Integer.toString(days));
                        }else if(calDe.after(calBi)){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Try Again");
                            alertDialog.setMessage("Birthday can't BEFORE Death Date, Try again");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Try Again");
                            alertDialog.setMessage("Birthday can't AFTER Death Date, Try again");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });

        /**
         * Reset function
         */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdayText.setText(null);
                deathText.setText(null);
                yearsLived.setText(null);
                monthLived.setText(null);
                daysLived.setText(null);
            }
        });
    }
}
