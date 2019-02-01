package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.graphics.Color;
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

    public List<Question> questions;
    public boolean testSubmitted=false;
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
        questionViewHolder.radioGroup.removeAllViews();

        for (int c = 0; c < questions.get(i).choices.length; c++) {

            final RadioButton answerChoice = new RadioButton(questionViewHolder.context);

            answerChoice.setText(questions.get(i).choices[c]);
            if (questions.get(i).getSelectedChoice() != null && questions.get(i).getSelectedChoice().equals(questions.get(i).choices[c])) {
                answerChoice.setChecked(true);
            }
            answerChoice.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        questions.get(questionViewHolder.getAdapterPosition()).setSelectedChoice(answerChoice.getText().toString());
                        // Log.e("STUFF ", questionViewHolder.getAdapterPosition() + " Positon " + questions.get(questionViewHolder.getAdapterPosition()).getSelectedChoice());
                    }
                }
            });

            questionViewHolder.radioGroup.addView(answerChoice);
        }
        if (questions.get(i).isWrong) {
            questionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffe6e6"));
        }
        else if(testSubmitted){
            questionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#E6FFEE"));

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
