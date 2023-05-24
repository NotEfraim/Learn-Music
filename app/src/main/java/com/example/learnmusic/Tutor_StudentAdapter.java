package com.example.learnmusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Tutor_StudentAdapter extends RecyclerView.Adapter<Tutor_StudentAdapter.MyViewHolder> {

    private Context mContext;
    private List<TutorRequest_list> profile;
    private List<TutorRequestList_instrument> instrument;


    public Tutor_StudentAdapter(Context mContext, List<TutorRequest_list> profile,
                                List<TutorRequestList_instrument> instrument) {
        this.mContext = mContext;
        this. profile = profile;
        this. instrument = instrument;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.tutor_student_layout, parent, false);
        MyViewHolder vHolder = new MyViewHolder(view);
        return  vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final TutorRequest_list tutorRequest_list = profile.get(position);
        final TutorRequestList_instrument tutorRequestList_instrument = instrument.get(position);

        // holder
        holder.StudentName.setText(tutorRequest_list.getName());
        holder.StudentInstrument.setText(tutorRequestList_instrument.getInstrument());
        holder.StudentProficiency.setText(tutorRequestList_instrument.getProficiency());
        String img = "http://learn-music.click2drinkph.store/images/"+tutorRequest_list.getImage_url();
        Glide.with(mContext).load(img).into(holder.tutor_pic);

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(mContext,Student_List_View_Details.class);
                activity.putExtra("name", tutorRequest_list.getName());
                activity.putExtra("age", tutorRequest_list.getAge());
                activity.putExtra("gender", tutorRequest_list.getGender());
                activity.putExtra("mobileNumber", tutorRequest_list.getMobileNumber());
                activity.putExtra("emailAddress", tutorRequest_list.getEmailAddress());
                activity.putExtra("address", tutorRequest_list.getAddress());
                activity.putExtra("image_url", img);

                // student instrument details
                activity.putExtra("id",tutorRequestList_instrument.getId());
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView StudentName;
        private TextView StudentInstrument;
        private TextView StudentProficiency;
        ImageView tutor_pic;
        Button details;
        private LinearLayout Student;

        public MyViewHolder(View itemVew) {
            super(itemVew);

            StudentName = (TextView) itemVew.findViewById(R.id.student_Fullname);
            StudentInstrument = (TextView) itemVew.findViewById(R.id.student_instrument);
            StudentProficiency = (TextView) itemVew.findViewById(R.id.student_proficiency);
            tutor_pic = itemVew.findViewById(R.id.tutor_pic);
            details = itemVew.findViewById(R.id.view);


        }
    }


    @Override
    public int getItemCount() {
        return profile.size();
    }




}
