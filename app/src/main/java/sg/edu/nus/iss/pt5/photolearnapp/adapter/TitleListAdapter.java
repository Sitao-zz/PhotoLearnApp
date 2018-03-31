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
import sg.edu.nus.iss.pt5.photolearnapp.activity.AnswerQuizItemActivity;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ItemActivity;
import sg.edu.nus.iss.pt5.photolearnapp.activity.TitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

/**
 * Created by mjeyakaran on 18/3/18.
 */

public class TitleListAdapter<T extends Title> extends RecyclerView.Adapter<TitleListAdapter.TitleViewHolder> {

    private List<T> titleList;
    private Context context;

    public TitleListAdapter(Context context, List<T> titleList) {
        this.context = context;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_title, parent, false);
        final TitleViewHolder titleViewHolder = new TitleViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantAnswerMode()) {
                    intent = new Intent(context, AnswerQuizItemActivity.class);
                } else {
                    intent = new Intent(context, ItemActivity.class);
                }

                intent.putExtra(AppConstants.TITLE_OBJ, titleList.get(titleViewHolder.getLayoutPosition()));
                context.startActivity(intent);

            }
        });

        return titleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        T title = titleList.get(position);
        holder.setTitle(title.getTitle());
        holder.setDateTime(title.getDateTime());
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView dateTimeTextView;

        public TitleViewHolder(View itemView) {

            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.titleTextViewID);
            dateTimeTextView = (TextView) itemView.findViewById((R.id.dateTimeTextViewID));

        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setDateTime(Date dateTime) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_DISPLAY_PATTERN);
            dateTimeTextView.setText(dateFormat.format(dateTime));
        }
    }

    public void removeTitle(int position) {
        titleList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void filter(List<T> titleList) {
        this.titleList = titleList;
        notifyDataSetChanged();
    }
}
