package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.QuestionViewHolder> {
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView questionText;
        RadioGroup radioGroup;
        Context context;

        QuestionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            questionText = (TextView) itemView.findViewById(R.id.questionTextView);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.answerRadioGroup);
            context = itemView.getContext();
        }

    }

    List<Question> questions;

    QuestionRecyclerViewAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiple_choice_question_card, viewGroup, false);
        QuestionViewHolder qv = new QuestionViewHolder(v);
        return qv;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder questionViewHolder, int i) {
        questionViewHolder.questionText.setText(questions.get(i).question);
        if(questionViewHolder.radioGroup.getChildCount()==0){
        for (int c = 0; c < questions.get(i).choices.length; c++) {
            final RadioButton answerChoice = new RadioButton(questionViewHolder.context);

            answerChoice.setText(questions.get(i).choices[c]);

            answerChoice.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    questions.get(questionViewHolder.getAdapterPosition()).setSelectedChoice(answerChoice.getText().toString());
                }
            });

            questionViewHolder.radioGroup.addView(answerChoice);
        }
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
