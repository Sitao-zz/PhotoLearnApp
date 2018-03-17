package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.R;

public class MainActivity extends AppCompatActivity {

    TextView mConditionTextView;
    Button mSunnyButton;
    Button mFoggyButton;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get UI elements
        mConditionTextView=(TextView)findViewById(R.id.textViewCondition);
        mSunnyButton=(Button)findViewById(R.id.buttonSunny);
        mFoggyButton=(Button)findViewById(R.id.buttonFoggy);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSunnyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionRef.setValue("Sunny");
            }
        });

        mFoggyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionRef.setValue("Foggy");
            }
        });
    }
}
