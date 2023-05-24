package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class TutorRequest_Adapter extends RecyclerView.Adapter<TutorRequest_Adapter.MyViewHolder> {

    private Context mContext;
    private List<TutorRequest_list> lstRequest;
    private List<TutorRequestList_instrument> lstInstrument;


    public TutorRequest_Adapter(Context mContext, List<TutorRequest_list> request_lists, List<TutorRequestList_instrument> instruments_list){
        this.lstRequest = request_lists;
        this.lstInstrument = instruments_list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.tutor_request_layout, parent, false);
        MyViewHolder vHolder = new MyViewHolder(view);

        return  vHolder;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView studentName;
        private TextView studentInstrument;
        private TextView studentProficiency;
        private LinearLayout requestLayout;
        ImageView tutor_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = (TextView) itemView.findViewById(R.id.student_Fullname);
            studentInstrument = (TextView) itemView.findViewById(R.id.student_instrument);
            studentProficiency = (TextView) itemView.findViewById(R.id.student_proficiency);
            requestLayout = (LinearLayout) itemView.findViewById(R.id.student_request);
            tutor_pic = itemView.findViewById(R.id.tutor_pic);


        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final TutorRequestList_instrument tutorRequestList_instrument = lstInstrument.get(position);
        final TutorRequest_list tutorRequest_list = lstRequest.get(position);

        holder.studentName.setText(tutorRequest_list.getName());
        holder.studentProficiency.setText(tutorRequestList_instrument.getProficiency());
        String image_url = "http://learn-music.click2drinkph.store/images/"+tutorRequest_list.getImage_url();
        Glide.with(mContext).load(image_url).into(holder.tutor_pic);

        holder.requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity = new Intent(mContext,StudentViewDetails.class);
                // student basic details
                activity.putExtra("name", tutorRequest_list.getName());
                activity.putExtra("age", tutorRequest_list.getAge());
                activity.putExtra("gender", tutorRequest_list.getGender());
                activity.putExtra("mobileNumber", tutorRequest_list.getMobileNumber());
                activity.putExtra("emailAddress", tutorRequest_list.getEmailAddress());
                activity.putExtra("address", tutorRequest_list.getAddress());
                activity.putExtra("image_url", image_url);

                // student instrument details
                activity.putExtra("tutor_email", tutorRequestList_instrument.getTutor_email());
                activity.putExtra("instrument", tutorRequestList_instrument.getInstrument());
                activity.putExtra("proficiency", tutorRequestList_instrument.getProficiency());
                activity.putExtra("sunday", tutorRequestList_instrument.getSunday());
                activity.putExtra("monday", tutorRequestList_instrument.getMonday());
                activity.putExtra("tuesday", tutorRequestList_instrument.getTuesday());
                activity.putExtra("wednesday", tutorRequestList_instrument.getWednesday());
                activity.putExtra("thursday", tutorRequestList_instrument.getThursday());
                activity.putExtra("friday", tutorRequestList_instrument.getFriday());
                activity.putExtra("saturday", tutorRequestList_instrument.getSaturday());


                mContext.startActivity(activity);


            }
        });

    }

    @Override
    public int getItemCount() {
        return lstInstrument.size();
    }


}
