package sg.edu.nus.iss.pt5.photolearnapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.TitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

/**
 * Created by mjeyakaran on 18/3/18.
 */

public class LearningSessionListAdapter extends RecyclerView.Adapter<LearningSessionListAdapter.LearningSessionViewHolder> {

    private List<LearningSession> learningSessionList;
    private Context context;

    public LearningSessionListAdapter(Context context, List<LearningSession> learningSessionList) {
        this.context = context;
        this.learningSessionList = learningSessionList;
    }

    @NonNull
    @Override
    public LearningSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_learning_session, parent, false);
        final LearningSessionViewHolder learningSessionViewHolder = new LearningSessionViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TitleActivity.class);
                intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSessionList.get(learningSessionViewHolder.getLayoutPosition()));
                context.startActivity(intent);

            }
        });

        return learningSessionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LearningSessionViewHolder holder, int position) {
        LearningSession learningSession = learningSessionList.get(position);
        holder.setSessionID(learningSession.getId());
        holder.setCourseName(learningSession.getCourseName());
        holder.setModuleName(learningSession.getModuleName());
        holder.setCourseDate(learningSession.getCourseDate());
    }

    @Override
    public int getItemCount() {
        return learningSessionList.size();
    }

    public static class LearningSessionViewHolder extends RecyclerView.ViewHolder {

        private TextView sessionIDTextView;
        private TextView courseNameTextView;
        private TextView moduleNameTextView;
        private TextView courseDateTextView;

        public LearningSessionViewHolder(View itemView) {

            super(itemView);

            sessionIDTextView = (TextView) itemView.findViewById(R.id.sessionIDTextViewID);
            courseNameTextView = (TextView) itemView.findViewById(R.id.courseNameTextViewID);
            moduleNameTextView = (TextView) itemView.findViewById(R.id.moduleNameTextViewID);
            courseDateTextView = (TextView) itemView.findViewById((R.id.courseDateTextViewID));

        }

        public void setSessionID(String sessionID) {
            sessionIDTextView.setText(sessionID);
        }

        public void setCourseName(String courseName) {
            courseNameTextView.setText(courseName);
        }

        public void setModuleName(String moduleName) {
            moduleNameTextView.setText(moduleName);
        }

        public void setCourseDate(Date courseDate) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            courseDateTextView.setText(dateFormat.format(courseDate));
        }
    }

    public void removeLearningSession(int position) {
        learningSessionList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

}
