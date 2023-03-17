package com.example.qcm_berrarat.Model;

public class QuestionModel {
    public  int Num, numero_reponsejuste;
    public String Question ,rep1, rep2, rep3;


    public QuestionModel(){

    }
    public QuestionModel(int num, String question, String rep1, String rep2, String rep3,int numero_reponsejuste) {
        Num = num;
        this.numero_reponsejuste = numero_reponsejuste;
        Question = question;
        this.rep1 = rep1;
        this.rep2 = rep2;
        this.rep3 = rep3;
    }

    public boolean check(int rep){
        if (rep==this.numero_reponsejuste){
            return true;
        }else {
            return false;
        }

    }
}