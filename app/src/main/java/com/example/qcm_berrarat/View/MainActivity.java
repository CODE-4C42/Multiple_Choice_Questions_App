package com.example.qcm_berrarat.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm_berrarat.Model.QuestionModel;
import com.example.qcm_berrarat.R;
import com.example.qcm_berrarat.Repository.QuestionRepository;
import com.example.qcm_berrarat.ViewModel.QuestionVm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    QuestionVm mvq;
    Button b1;
    TextView tv1;
    RadioButton rb1,rb2,rb3;
    Chronometer C1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.b1);
        tv1=findViewById(R.id.tv1);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        C1=findViewById(R.id.C1);
        ArrayList<Integer> arrQ=new ArrayList<>();
        ArrayList<Integer> arrAn=new ArrayList<>();
        final int[] cmptQ = {0};
        mvq = new ViewModelProvider(MainActivity.this).get(QuestionVm.class);
        try {
            QuestionRepository.i = getResources().getAssets().open("Data.json");
        } catch (IOException e) {}


        int [] l={15,30,40};
        final boolean[] b = {false};

        C1.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().equals("00:30")) {
                    int xRandom=getRandomNumber(cmptQ[0],arrQ);
                    if (xRandom!=0){
                        setQuestion(xRandom,cmptQ[0]);
                        arrAn.add(0);
                        chronometer.setText("00:00");
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }else{
                        arrAn.add(0);
                        NextPage(arrQ, arrAn);
                        C1.stop();
                        finish();
                    }

                }
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                C1.setBase(SystemClock.elapsedRealtime());

                if (cmptQ[0] ==0 && getCheckedRadio()!=0){
                    cmptQ[0] =l[getCheckedRadio()-1];
                    C1.start();
                }

                if(cmptQ[0]!=0){
                    int xRandom=getRandomNumber(cmptQ[0],arrQ);
                    if (xRandom!=0){
                        if (b[0]){
                            arrAn.add(getCheckedRadio());
                        }
                        setQuestion(xRandom,cmptQ[0]);

                        b[0] =true;
                    }else{
                        arrAn.add(getCheckedRadio());
                        NextPage( arrQ, arrAn);
                        C1.stop();
                        finish();


                    }

                }

            }
        });

    }
    public void NextPage(ArrayList<Integer> arrQ,ArrayList<Integer> arrAn){
        Intent intent = new Intent(MainActivity.this, Score.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("arrQ", arrQ);
        bundle.putSerializable("arrAn", arrAn);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void setQuestion(int xRandom,int cmptQ){
        mvq.returnData(xRandom).observe(MainActivity.this, new Observer<QuestionModel>() {
            public void onChanged(QuestionModel question) {

                tv1.setText(question.Question);
                rb1.setText(question.rep1);
                rb2.setText(question.rep2);
                rb3.setText(question.rep3);
            }
        });
    }
    public int getRandomNumber(int max, ArrayList<Integer> arr){
        while (true){
            if(arr.size()==max){
                return 0;
            }
            Random random = new Random();
            int randomInt = random.nextInt(40) + 1;
            boolean b= true;
            for(int a:arr){
                if(randomInt==a){
                    b=false;
                }
            }
            if(b){
                arr.add(randomInt);
                return randomInt;
            }
        }
    }
    public int getCheckedRadio(){
        if(rb1.isChecked()){
            return 1;
        }
        if(rb2.isChecked()){
            return 2;
        }
        if(rb3.isChecked()){
            return 3;
        }
        return 0;


    }
}
