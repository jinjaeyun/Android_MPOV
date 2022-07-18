package com.example.third;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class theater2 extends AppCompatActivity {
    ScrollView scrollView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater2);
        scrollView = findViewById(R.id.scrollView);
        scrollView.setHorizontalScrollBarEnabled(true);
        scrollView.setBackgroundColor(Color.parseColor("#333333"));
        Intent get2 = getIntent();
        if(get2.getStringExtra("theater").equals("theater2")){
            getSupportActionBar().setTitle("2관");
        }
        else if(get2.getStringExtra("theater").equals("theater5")){
            getSupportActionBar().setTitle("5관");
        }
        DBHelper dbHelper = new DBHelper(theater2.this, 2);

        RelativeLayout relative1 = new RelativeLayout(this);
        Button[][] seat = new Button[300][300];

        RelativeLayout.LayoutParams screen_param = new RelativeLayout.LayoutParams(1000, 150);
        RelativeLayout.LayoutParams right_param = new RelativeLayout.LayoutParams(130, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams bottom_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,150);

        TextView bottom = new TextView(this);

        bottom_param.topMargin = 2000;
        bottom.setLayoutParams(bottom_param);

        TextView right = new TextView(this);
        TextView screen = new TextView(this);

        right_param.leftMargin = 2200;
        right.setLayoutParams(right_param);

        screen.setText("SCREEN");
        screen.setTextColor(Color.parseColor("#888888"));
        screen.setBackgroundColor(Color.parseColor("#444444"));
        screen.setTextSize(COMPLEX_UNIT_SP, 18);
        screen_param.topMargin=110;
        screen.setGravity(Gravity.CENTER);
        screen_param.addRule(RelativeLayout.CENTER_HORIZONTAL);
        screen.setLayoutParams(screen_param);

        relative1.addView(right);
        relative1.addView(bottom);
        relative1.addView(screen);

        for(int i =0; i<14; i++) {
            if (i==0) {
                for (int j = 3; j <= 14; j++) {
                    if(j==6){
                        seat[i][j] = new Button(this);
                        RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                        seat[i][j].setId(12 * i + j-2);
                        r_param.addRule(RelativeLayout.ALIGN_LEFT, 12*i+j - 3);
                        r_param.leftMargin = 141;
                        r_param.topMargin = 370;
                        seat[i][j].setBackground(Drawable.createFromPath("#3e3e3e"));
                        seat[i][j].setLayoutParams(r_param);
                        relative1.addView(seat[i][j]);

                    }
                    else if(j==3){
                        seat[i][j] = new Button(this);
                        RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                        seat[i][j].setId(12 * i + j-2);
                        seat[i][j].setText((char) 65 + String.valueOf(j));
                        seat[i][j].setTextColor(Color.parseColor("#FFFFFF"));
                        seat[i][j].setBackgroundResource(R.drawable.button_shape);
                        seat[i][j].setTextSize(COMPLEX_UNIT_SP, 12);
                        r_param.leftMargin = 524;
                        r_param.topMargin = 370;
                        seat[i][j].setLayoutParams(r_param);
                        relative1.addView(seat[i][j]);
                        int finalJ0 = j;
                        seat[i][j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater inflater2 = getLayoutInflater();
                                final View v2 = inflater2.inflate(R.layout.seat_dialog, null);
                                AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                                final EditText et2 = v2.findViewById(R.id.height2);
                                Button move_button = v2.findViewById(R.id.buttonLogin);
                                Button clear_button = v2.findViewById(R.id.naga);
                                Button scope_button = v2.findViewById(R.id.buttonScope);
                                TextView value = v2.findViewById(R.id.value);
                                scope_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater inflater = getLayoutInflater();
                                        final View v3 = inflater.inflate(R.layout.rating, null);
                                        AlertDialog.Builder ad2 = new AlertDialog.Builder(theater2.this);
                                        final TextView score = v3.findViewById(R.id.score);
                                        Button scope_button2 = v3.findViewById(R.id.buttonScope2);
                                        Button clear_button = v3.findViewById(R.id.naga);
                                        RatingBar ratingBar = v3.findViewById(R.id.ratingBar);
                                        ad2.setView(v3);
                                        AlertDialog dialog2 = ad2.create();
                                        dialog2.show();
                                        dialog2.setCancelable(false);
                                        clear_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                                                score.setText(Float.toString(rating));
                                            }
                                        });
                                        scope_button2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ0)).length()==0) {
                                                    dbHelper.insert(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ0), Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ0)));
                                                }
                                                else{
                                                    dbHelper.Update(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ0),Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ0)));
                                                }
                                            }
                                        });
                                    }
                                });
                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ0)).length()==0){
                                    value.setText("평점 없음");
                                }
                                else{
                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ0)));
                                }
                                ad.setView(v2);
                                AlertDialog dialog = ad.create();
                                dialog.show();
                                dialog.setCancelable(false);
                                clear_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                move_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(et2.length()==0){
                                            Toast.makeText(getApplicationContext(),"키를 입력해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                        else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", (char) 65 + String.valueOf(finalJ0) + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                ad.setCancelable(false);
                            }
                        });
                    }
                    else if(j<15) {
                        seat[i][j] = new Button(this);
                        RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                        seat[i][j].setId(12 * i + j-2);
                        seat[i][j].setText((char) 65 + String.valueOf(j));
                        seat[i][j].setTextColor(Color.parseColor("#FFFFFF"));
                        seat[i][j].setBackgroundResource(R.drawable.button_shape);
                        seat[i][j].setTextSize(COMPLEX_UNIT_SP, 12);
                        r_param.addRule(RelativeLayout.ALIGN_LEFT, 12 * i + j - 3);
                        r_param.leftMargin = 141;
                        r_param.topMargin = 370;
                        seat[i][j].setLayoutParams(r_param);
                        relative1.addView(seat[i][j]);
                        int finalJ = j;
                        seat[i][j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater inflater2 = getLayoutInflater();
                                final View v2 = inflater2.inflate(R.layout.seat_dialog, null);
                                AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                                final EditText et2 = v2.findViewById(R.id.height2);
                                Button move_button = v2.findViewById(R.id.buttonLogin);
                                Button clear_button = v2.findViewById(R.id.naga);
                                Button scope_button = v2.findViewById(R.id.buttonScope);
                                TextView value = v2.findViewById(R.id.value);
                                scope_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater inflater = getLayoutInflater();
                                        final View v3 = inflater.inflate(R.layout.rating, null);
                                        AlertDialog.Builder ad2 = new AlertDialog.Builder(theater2.this);
                                        final TextView score = v3.findViewById(R.id.score);
                                        Button scope_button2 = v3.findViewById(R.id.buttonScope2);
                                        Button clear_button = v3.findViewById(R.id.naga);
                                        RatingBar ratingBar = v3.findViewById(R.id.ratingBar);
                                        ad2.setView(v3);
                                        AlertDialog dialog2 = ad2.create();
                                        dialog2.show();
                                        dialog2.setCancelable(false);
                                        clear_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                                                score.setText(Float.toString(rating));
                                            }
                                        });
                                        scope_button2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ)).length()==0) {
                                                    dbHelper.insert(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ), Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ)));
                                                }
                                                else{
                                                    dbHelper.Update(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ),Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ)));
                                                }
                                            }
                                        });
                                    }
                                });
                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char) 65 + String.valueOf(finalJ)).length()==0){
                                    value.setText("평점 없음");
                                }
                                else{
                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char) 65 + String.valueOf(finalJ)));
                                }
                                ad.setView(v2);
                                AlertDialog dialog = ad.create();
                                dialog.show();
                                dialog.setCancelable(false);
                                clear_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                move_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(et2.length()==0){
                                            Toast.makeText(getApplicationContext(),"키를 입력해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                        else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", (char) 65 + String.valueOf(finalJ) + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                ad.setCancelable(false);

                            }
                        });
                    }
                }
            }
            else if(i==11 || i==12 || i==13){
                for(int j=1; j<3; j++) {
                    if (j == 1) {
                        seat[i][j] = new Button(this);
                        RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                        seat[i][j].setId(12 * i + j);
                        seat[i][j].setText((char)(65+i-3) + String.valueOf(j));
                        r_param.addRule(RelativeLayout.BELOW, 12 * (i-3) + j - 12);
                        r_param.leftMargin = 141;
                        r_param.topMargin = 11;;
                        seat[i][j].setTextColor(Color.parseColor("#FFFFFF"));
                        seat[i][j].setBackgroundResource(R.drawable.button_shape);
                        seat[i][j].setTextSize(COMPLEX_UNIT_SP, 12);
                        seat[i][j].setLayoutParams(r_param);
                        relative1.addView(seat[i][j]);
                        int finalJ1 = j;
                        int finalI1 = i;
                        seat[i][j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater inflater2 = getLayoutInflater();
                                final View v2 = inflater2.inflate(R.layout.seat_dialog, null);
                                AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                                final EditText et2 = v2.findViewById(R.id.height2);
                                Button move_button = v2.findViewById(R.id.buttonLogin);
                                Button clear_button = v2.findViewById(R.id.naga);
                                Button scope_button = v2.findViewById(R.id.buttonScope);
                                TextView value = v2.findViewById(R.id.value);
                                scope_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater inflater = getLayoutInflater();
                                        final View v3 = inflater.inflate(R.layout.rating, null);
                                        AlertDialog.Builder ad2 = new AlertDialog.Builder(theater2.this);
                                        final TextView score = v3.findViewById(R.id.score);
                                        Button scope_button2 = v3.findViewById(R.id.buttonScope2);
                                        Button clear_button = v3.findViewById(R.id.naga);
                                        RatingBar ratingBar = v3.findViewById(R.id.ratingBar);
                                        ad2.setView(v3);
                                        AlertDialog dialog2 = ad2.create();
                                        dialog2.show();
                                        dialog2.setCancelable(false);
                                        clear_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                                                score.setText(Float.toString(rating));
                                            }
                                        });
                                        scope_button2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI1 -3) + String.valueOf(finalJ1)).length()==0) {
                                                    dbHelper.insert(get2.getStringExtra("theater"), (char)(65+ finalI1 -3) + String.valueOf(finalJ1), Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI1 -3) + String.valueOf(finalJ1)));
                                                }
                                                else{
                                                    dbHelper.Update(get2.getStringExtra("theater"),(char)(65+ finalI1 -3) + String.valueOf(finalJ1),Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI1 -3) + String.valueOf(finalJ1)));
                                                }
                                            }
                                        });
                                    }
                                });
                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI1 -3) + String.valueOf(finalJ1)).length()==0){
                                    value.setText("평점 없음");
                                }
                                else{
                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI1 -3) + String.valueOf(finalJ1)));
                                }
                                ad.setView(v2);
                                AlertDialog dialog = ad.create();
                                dialog.show();
                                dialog.setCancelable(false);
                                clear_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                move_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(et2.length()==0){
                                            Toast.makeText(getApplicationContext(),"키를 입력해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                        else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", (char)(65+ finalI1 -3) + String.valueOf(finalJ1) + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                ad.setCancelable(false);

                            }
                        });
                    }
                    else {
                        seat[i][j] = new Button(this);
                        RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                        seat[i][j].setId(12 * i + j);
                        seat[i][j].setText((char)(65+i-3) + String.valueOf(j));
                        seat[i][j].setTextColor(Color.parseColor("#FFFFFF"));
                        seat[i][j].setBackgroundResource(R.drawable.button_shape);
                        seat[i][j].setTextSize(COMPLEX_UNIT_SP, 12);
                        r_param.addRule(RelativeLayout.BELOW, 12 * (i-3) + j - 12);
                        r_param.addRule(RelativeLayout.ALIGN_LEFT, 12 * i + j - 1);
                        r_param.leftMargin = 141;
                        r_param.topMargin = 11;;
                        seat[i][j].setLayoutParams(r_param);
                        relative1.addView(seat[i][j]);
                        int finalJ2 = j;
                        int finalI2 = i;
                        seat[i][j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LayoutInflater inflater2 = getLayoutInflater();
                                final View v2 = inflater2.inflate(R.layout.seat_dialog, null);
                                AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                                final EditText et2 = v2.findViewById(R.id.height2);
                                Button move_button = v2.findViewById(R.id.buttonLogin);
                                Button clear_button = v2.findViewById(R.id.naga);
                                Button scope_button = v2.findViewById(R.id.buttonScope);
                                TextView value = v2.findViewById(R.id.value);
                                scope_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LayoutInflater inflater = getLayoutInflater();
                                        final View v3 = inflater.inflate(R.layout.rating, null);
                                        AlertDialog.Builder ad2 = new AlertDialog.Builder(theater2.this);
                                        final TextView score = v3.findViewById(R.id.score);
                                        Button scope_button2 = v3.findViewById(R.id.buttonScope2);
                                        Button clear_button = v3.findViewById(R.id.naga);
                                        RatingBar ratingBar = v3.findViewById(R.id.ratingBar);
                                        ad2.setView(v3);
                                        AlertDialog dialog2 = ad2.create();
                                        dialog2.show();
                                        dialog2.setCancelable(false);
                                        clear_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                                                score.setText(Float.toString(rating));
                                            }
                                        });
                                        scope_button2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI2 -3) + String.valueOf(finalJ2)).length()==0) {
                                                    dbHelper.insert(get2.getStringExtra("theater"), (char)(65+ finalI2 -3) + String.valueOf(finalJ2), Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI2 -3) + String.valueOf(finalJ2)));
                                                }
                                                else{
                                                    dbHelper.Update(get2.getStringExtra("theater"),(char)(65+ finalI2 -3) + String.valueOf(finalJ2),Float.parseFloat(score.getText().toString()));
                                                    dialog2.dismiss();
                                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI2 -3) + String.valueOf(finalJ2)));
                                                }
                                            }
                                        });
                                    }
                                });
                                if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI2 -3) + String.valueOf(finalJ2)).length()==0){
                                    value.setText("평점 없음");
                                }
                                else{
                                    value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI2 -3) + String.valueOf(finalJ2)));
                                }
                                ad.setView(v2);
                                AlertDialog dialog = ad.create();
                                dialog.show();
                                dialog.setCancelable(false);
                                clear_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                move_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(et2.length()==0){
                                            Toast.makeText(getApplicationContext(),"키를 입력해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                        else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", (char)(65+ finalI2 -3) + String.valueOf(finalJ2) + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                ad.setCancelable(false);

                            }
                        });
                    }
                }
            }
            else{
                for (int j = 3; j <= 14; j++) {
                    seat[i][j] = new Button(this);
                    RelativeLayout.LayoutParams r_param = new RelativeLayout.LayoutParams(130, 130);
                    seat[i][j].setId(12 * i + j-2);
                    seat[i][j].setText((char)(65 + i) + String.valueOf(j));
                    if(j==3){
                        r_param.addRule(RelativeLayout.BELOW, 12*i+j - 14);
                        r_param.leftMargin = 524;
                        r_param.topMargin = 11;;
                    }
                    else{
                        r_param.addRule(RelativeLayout.BELOW, 12*i+j - 14);
                        r_param.addRule(RelativeLayout.ALIGN_LEFT, 12*i+j - 3);
                        r_param.leftMargin = 141;
                        r_param.topMargin = 11;;
                    }
                    seat[i][j].setTextColor(Color.parseColor("#FFFFFF"));
                    seat[i][j].setBackgroundResource(R.drawable.button_shape);
                    seat[i][j].setTextSize(COMPLEX_UNIT_SP, 12);
                    seat[i][j].setLayoutParams(r_param);
                    relative1.addView(seat[i][j]);
                    int finalJ3 =j;
                    int finalI3 = i;
                    seat[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater inflater2 = getLayoutInflater();
                            final View v2 = inflater2.inflate(R.layout.seat_dialog, null);
                            AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                            final EditText et2 = v2.findViewById(R.id.height2);
                            Button move_button = v2.findViewById(R.id.buttonLogin);
                            Button clear_button = v2.findViewById(R.id.naga);
                            Button scope_button = v2.findViewById(R.id.buttonScope);
                            TextView value = v2.findViewById(R.id.value);
                            scope_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    LayoutInflater inflater = getLayoutInflater();
                                    final View v3 = inflater.inflate(R.layout.rating, null);
                                    AlertDialog.Builder ad2 = new AlertDialog.Builder(theater2.this);
                                    final TextView score = v3.findViewById(R.id.score);
                                    Button scope_button2 = v3.findViewById(R.id.buttonScope2);
                                    Button clear_button = v3.findViewById(R.id.naga);
                                    RatingBar ratingBar = v3.findViewById(R.id.ratingBar);
                                    ad2.setView(v3);
                                    AlertDialog dialog2 = ad2.create();
                                    dialog2.show();
                                    dialog2.setCancelable(false);
                                    clear_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog2.dismiss();
                                        }
                                    });
                                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                        @Override
                                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                                            score.setText(Float.toString(rating));
                                        }
                                    });
                                    scope_button2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI3) + String.valueOf(finalJ3)).length()==0) {
                                                dbHelper.insert(get2.getStringExtra("theater"), (char)(65+ finalI3) + String.valueOf(finalJ3), Float.parseFloat(score.getText().toString()));
                                                dialog2.dismiss();
                                                value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI3) + String.valueOf(finalJ3)));
                                            }
                                            else{
                                                dbHelper.Update(get2.getStringExtra("theater"),(char)(65+ finalI3) + String.valueOf(finalJ3),Float.parseFloat(score.getText().toString()));
                                                dialog2.dismiss();
                                                value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI3) + String.valueOf(finalJ3)));
                                            }
                                        }
                                    });
                                }
                            });
                            if(dbHelper.getResult(get2.getStringExtra("theater"),(char)(65+ finalI3) + String.valueOf(finalJ3)).length()==0){
                                value.setText("평점 없음");
                            }
                            else{
                                value.setText(dbHelper.getResult(get2.getStringExtra("theater"), (char)(65+ finalI3) + String.valueOf(finalJ3)));
                            }
                            ad.setView(v2);
                            AlertDialog dialog = ad.create();
                            dialog.show();
                            dialog.setCancelable(false);
                            clear_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            move_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(et2.length()==0){
                                        Toast.makeText(getApplicationContext(),"키를 입력해주세요.", Toast.LENGTH_LONG).show();
                                    }
                                    else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100) {
                                        Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                        Intent get1 = getIntent();
                                        intent1.putExtra("theater", "theater2");
                                        intent1.putExtra("seat", (char)(65+ finalI3) + String.valueOf(finalJ3) + et2.getText().toString());
                                        startActivity(intent1);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            ad.setCancelable(false);

                        }
                    });
                }
            }

        }
        scrollView.addView(relative1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_search:
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.direct_dialog, null);
                AlertDialog.Builder ad = new AlertDialog.Builder(theater2.this);
                final EditText et = v.findViewById(R.id.seat_number2);
                final EditText et2 = v.findViewById(R.id.height2);
                Button move_button = v.findViewById(R.id.buttonLogin);
                Button clear_button = v.findViewById(R.id.naga);

                ad.setView(v);
                AlertDialog dialog = ad.create();
                dialog.show();
                dialog.setCancelable(false);
                clear_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                move_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(et2.length()==0){
                            Toast.makeText(getApplicationContext(),"키를 입력해주세요",Toast.LENGTH_LONG).show();
                        }
                        else if(Integer.parseInt(et2.getText().toString())<=200 && Integer.parseInt(et2.getText().toString())>=100){
                            if(et.length()==0){
                                Toast.makeText(getApplicationContext(),"좌석을 입력해주세요",Toast.LENGTH_LONG).show();
                            }
                            else if(et.length()<=3 && et.length()>=2){
                                String sea = et.getText().toString().substring(0,1);
                                String ihei = et.getText().toString().substring(1);
                                if (sea.equals("B") || sea.equals("C") || sea.equals("D") || sea.equals("E") || sea.equals("F")
                                        || sea.equals("G") || sea.equals("H")) {
                                    if(ihei.matches("[+-]?\\d*(\\.\\d+)?")) {
                                        if(Integer.parseInt(ihei)>=3 && Integer.parseInt(ihei)<=14) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", et.getText().toString() + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"없는 좌석의 번호입니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"열 뒤에 숫자를 입력해주세요",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else if(sea.equals("A")){
                                    if(ihei.matches("[+-]?\\d*(\\.\\d+)?")) {
                                        if(Integer.parseInt(ihei)>=3 && Integer.parseInt(ihei)<=14 && Integer.parseInt(ihei) != 6) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", et.getText().toString() + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"없는 좌석의 번호입니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"열 뒤에 숫자를 입력해주세요",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else if(sea.equals("I") || sea.equals("J") || sea.equals("K")){
                                    if(ihei.matches("[+-]?\\d*(\\.\\d+)?")) {
                                        if(Integer.parseInt(ihei)>=1 && Integer.parseInt(ihei)<=14) {
                                            Intent intent1 = new Intent(theater2.this, unityPlugin.class);
                                            Intent get1 = getIntent();
                                            intent1.putExtra("theater", "theater2");
                                            intent1.putExtra("seat", et.getText().toString() + et2.getText().toString());
                                            startActivity(intent1);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"없는 좌석의 번호입니다.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"열 뒤에 숫자를 입력해주세요",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"없는 열입니다.",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"좌석 뒤 번호를 입력해주세요",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"키 범위를 벗어났습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }
}