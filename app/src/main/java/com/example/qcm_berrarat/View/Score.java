package com.example.qcm_berrarat.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qcm_berrarat.Model.QuestionModel;
import com.example.qcm_berrarat.R;
import com.example.qcm_berrarat.Repository.QuestionRepository;
import com.example.qcm_berrarat.ViewModel.QuestionVm;

import java.io.IOException;
import java.util.ArrayList;

public class Score extends AppCompatActivity  {
    TextView t2;
    QuestionVm mvq;
    Button btn2,btn3;
    ArrayList<String> arrStr=new ArrayList<>();
    ArrayList<Integer> arrPoint=new ArrayList<>();
    ArrayList<QuestionModel> arrQues= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Score.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Score.this).create();
                alertDialog.setTitle("Fermer l'application");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });





        t2=findViewById(R.id.txtv2);






        Bundle bundle = getIntent().getExtras();
        ArrayList<Integer> arrQ= (ArrayList<Integer>) bundle.getSerializable("arrQ");
        ArrayList<Integer> arrAn= (ArrayList<Integer>) bundle.getSerializable("arrAn");




        ListView listView = (ListView) findViewById(R.id.list_view);

        mvq = new ViewModelProvider(Score.this).get(QuestionVm.class);
        try {
            QuestionRepository.i = getResources().getAssets().open("Data.json");
        } catch (IOException e) {}

        for (int i = 0; i < arrQ.size(); i++) {
            getQuestion(arrQ.get(i),arrAn.get(i));
        }





        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrStr);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(Score.this).create();
                alertDialog.setTitle(""+arrQues.get(position).Question);
                alertDialog.setMessage("1) "+arrQues.get(position).rep1+"\n\n2) "+arrQues.get(position).rep2+"\n\n3) "+arrQues.get(position).rep3+"\n\nnuméro reponse juste est "+arrQues.get(position).numero_reponsejuste);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


    }
    public void getQuestion(int qNumber,int ansNumber){
        mvq.returnData(qNumber).observe(Score.this, new Observer<QuestionModel>() {
            @Override
            public void onChanged(QuestionModel question) {
                arrQues.add(question);
                if(question.numero_reponsejuste!=ansNumber){
                    arrStr.add("Question Numero "+question.Num+": "+question.Question+" < Fausse >");
                }else{
                    arrStr.add("Question Numero "+question.Num+": "+question.Question+" < Juste >");
                    arrPoint.add(2);
                    int sum = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        sum = arrPoint.stream().reduce(0, Integer::sum);
                    }
                    t2.setText(""+sum+" Pts");
                }

            }
        });
    }


}