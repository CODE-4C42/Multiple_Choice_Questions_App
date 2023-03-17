package com.example.qcm_berrarat.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.qcm_berrarat.Model.QuestionModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class QuestionRepository {
    public static InputStream i;
    public MutableLiveData<QuestionModel> getQuestion(int Num) {
        MutableLiveData<QuestionModel> mld = new MutableLiveData<>();
        QuestionModel que = new QuestionModel();
        try {
            i.reset();
            byte[] b = new byte[i.available()];
            i.read(b);
            String data = new String(b, "UTF-8");
            JSONObject d = new JSONObject(data);
            JSONArray questions = d.getJSONArray("questions");
            for(int i = 0; i < questions.length(); i ++) {
                JSONObject q = questions.getJSONObject(i);
                if (Num==q.getInt("number")) {
                    que = new QuestionModel(q.getInt("number"),q.getString("question"),q.getString("answer1"),q.getString("answer2"),q.getString("answer3"),q.getInt("rightAnswer"));
                    break;
                }
            }
        } catch (Exception e) {}
        mld.setValue(que);
        return mld;
    }
}
