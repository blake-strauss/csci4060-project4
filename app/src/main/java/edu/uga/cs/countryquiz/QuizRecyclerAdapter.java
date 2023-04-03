package edu.uga.cs.countryquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all quizzes.
 */

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {
    private final Context context;

    private List<Quizzes> values;
    private List<Quizzes> originalValues;

    public QuizRecyclerAdapter( Context context, List<Quizzes> quizList ) {
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<Quizzes>( quizList );
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public static class QuizHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView result;

        public QuizHolder( View itemView ) {
            super( itemView );

            date = itemView.findViewById( R.id.date );
            result = itemView.findViewById( R.id.result );
        }
    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.pastquizzes, parent, false );
        return new QuizHolder( view );
    }

    // This method fills in the values of a holder to show the past quizzes.
    // The position parameter indicates the position on the list of the quizzes.
    @Override
    public void onBindViewHolder( QuizHolder holder, int position ) {

        Quizzes quiz = values.get( position );

        holder.date.setText(quiz.getDate());
        holder.result.setText(quiz.getResult());
    }

    @Override
    public int getItemCount() {
        if( values != null )
            return values.size();
        else
            return 0;
    }
}
