package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.content.res.ColorStateList;
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
// This recycler view adapter and its corresponding recycler view
// contain test questions in an easily navigable and visually appealing
// fashion. In addition, recycler views are very efficient when it comes
// to handling large data sets, so it can handle large tests
public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.QuestionViewHolder> {
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView questionText;
        TextView correctAnswerView;
        RadioGroup radioGroup;
        Context context;

        QuestionViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            questionText = (TextView) itemView.findViewById(R.id.questionTextView);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.answerRadioGroup);
            correctAnswerView = (TextView) itemView.findViewById(R.id.correctAnswerTextView);
            context = itemView.getContext();
        }

    }

    public List<Question> questions;
    public boolean testSubmitted = false;

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
        questionViewHolder.questionText.setText((i + 1) + ". " + questions.get(i).question);
        questionViewHolder.radioGroup.removeAllViews();
        for (int c = 0; c < questions.get(i).choices.length; c++) {

            final RadioButton answerChoice = new RadioButton(questionViewHolder.context);

            answerChoice.setText(questions.get(i).choices[c]);

            answerChoice.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (b) {
                        questions.get(questionViewHolder.getAdapterPosition()).setSelectedChoice(answerChoice.getText().toString());
                    }

                }
            });

            questionViewHolder.radioGroup.addView(answerChoice);
            if (questions.get(i).getSelectedChoice() != null && questions.get(i).getSelectedChoice().equals(questions.get(i).choices[c])) {
                //answerChoice.setChecked(true);
                questionViewHolder.radioGroup.check(((RadioButton) questionViewHolder.radioGroup.getChildAt(c)).getId());

            }
        }
        if (testSubmitted) {
            for (int ch = 0; ch < questionViewHolder.radioGroup.getChildCount(); ch++) {

                if (!((RadioButton) questionViewHolder.radioGroup.getChildAt(ch)).getText().toString().equals(questions.get(i).getSelectedChoice())) {
                    questionViewHolder.radioGroup.getChildAt(ch).setEnabled(false);
                }

            }
            if (questions.get(i).isWrong) {
                questionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#ffe6e6"));
                questionViewHolder.correctAnswerView.setText("Correct answer: " + questions.get(i).answer);
                questionViewHolder.correctAnswerView.setVisibility(View.VISIBLE);

            } else {
                questionViewHolder.cardView.setCardBackgroundColor(Color.parseColor("#E6FFEE"));
                questionViewHolder.correctAnswerView.setVisibility(View.GONE);
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
