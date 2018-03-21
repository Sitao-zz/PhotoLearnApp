package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

public class LearningSessionAdapter extends ArrayAdapter<LearningSession> {
    private Context context;
    List<LearningSession> LearningSessions;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public LearningSessionAdapter(Context context, List<LearningSession> LearningSessions) {
        super(context, R.layout.list_item_learning_session, LearningSessions);
        this.context = context;
        this.LearningSessions = LearningSessions;
    }

    private class ViewHolder {
        TextView lsIdTxt;
        TextView courseCodeTxt;
        TextView courseDateTxt;
        TextView moduleNumberTxt;
    }

    @Override
    public int getCount() {
        return LearningSessions.size();
    }

    @Override
    public void add(LearningSession object) {
        LearningSessions.add(object);
        notifyDataSetChanged();
        super.add(object);
    }

    @Override
    public void remove(LearningSession object) {
        LearningSessions.remove(object);
        notifyDataSetChanged();
        super.remove(object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_learning_session, null);
            holder = new ViewHolder();

            holder.lsIdTxt = (TextView) convertView.findViewById(R.id.lsIdTxt);
            holder.courseCodeTxt = (TextView) convertView.findViewById(R.id.courseCodeTxt);
            holder.courseDateTxt = (TextView) convertView.findViewById(R.id.courseDateTxt);
            holder.moduleNumberTxt = (TextView) convertView.findViewById(R.id.moduleNumberTxt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LearningSession learningSession = (LearningSession) getItem(position);
        holder.lsIdTxt.setText(learningSession.getId() + "");
        holder.courseCodeTxt.setText(learningSession.getCourseCode());
        holder.courseDateTxt.setText(formatter.format(learningSession.getCourseDate()));
        holder.moduleNumberTxt.setText(learningSession.getModuleNumber() + "");

        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
