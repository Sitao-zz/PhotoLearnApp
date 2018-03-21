package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

public class ListLearningSessionActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "learningSession_list";

    Activity activity;
    ListView learningSessionView;
    Button mDetailsButton;
    ArrayList<LearningSession> learningSessions;

    LearningSessionAdapter learningSessionAdapter;
    // LearningSessionDAO learningSessionDAO;
    Calendar dateCalendar;
    private GetLsTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_learningsession);

        //  learningSessionDAO = new LearningSessionDAO(this);

        findViewsById();
        task = new GetLsTask(this);
        task.execute((Void) null);

        mDetailsButton = (Button) findViewById(R.id.buttonDetails);

        //new Test(Create2Activity.this, url).execute(); //async task for getting data from db
    }

    @Override
    public void onStart() {
        super.onStart();
        mDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListLearningSessionActivity.this, DetailsActivity.class);
                intent.putExtra("location", "place");
                startActivity(intent);
            }
        });
    }

    private void findViewsById() {
        learningSessionView = (ListView) findViewById(R.id.list_ls);
    }

    public class GetLsTask extends AsyncTask<Void, Void, ArrayList<LearningSession>> {
        private final WeakReference<Activity> activityWeakRef;

        public GetLsTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<LearningSession> doInBackground(Void... arg0) {
            // ArrayList<LearningSession> learningSessionList = reminderDAO.getReminders();
            ArrayList<LearningSession> learningSessionList = new ArrayList<LearningSession>();
            LearningSession ls = new LearningSession();
            dateCalendar = Calendar.getInstance();
            ls.setCourseDate(dateCalendar.getTime());
            ls.setCourseCode("aaaa");
            ls.setId("LS123");
            ls.setModuleNumber(1);

            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            learningSessionList.add(ls);
            return learningSessionList;
        }

        @Override
        protected void onPostExecute(ArrayList<LearningSession> lsList) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                Log.d("reminders", lsList.toString());
                learningSessions = lsList;
                if (lsList != null) {
                    if (lsList.size() != 0) {
                        learningSessionAdapter = new LearningSessionAdapter(getApplicationContext(), lsList);
                        learningSessionView.setAdapter(learningSessionAdapter);
                    } else {
                        Toast.makeText(activity, "No Learning Session List", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
