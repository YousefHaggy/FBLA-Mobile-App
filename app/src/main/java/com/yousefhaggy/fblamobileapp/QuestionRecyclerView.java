package com.yousefhaggy.fblamobileapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class QuestionRecyclerView extends RecyclerView.Adapter<QuestionRecyclerView.QuestionViewHolder> {
    public static class QuestionViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView questionText;
        RadioGroup radioGroup;
    QuestionViewHolder(View itemView){
        super(itemView);
        cardView=(CardView)itemView.findViewById(R.id.cardView);
        questionText=(TextView)itemView.findViewById(R.id.questionTextView);
        radioGroup=(RadioGroup)itemView.findViewById(R.id.answerRadioGroup);
    }
    }
    List<Question> questions;
    QuestionRecyclerView(List<Question> questions){
        this.questions=questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiple_choice_question_card,viewGroup,false);
        QuestionViewHolder qv=new QuestionViewHolder(v);
        return qv;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        questionViewHolder.questionText.setText(questions.get(i).question);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
