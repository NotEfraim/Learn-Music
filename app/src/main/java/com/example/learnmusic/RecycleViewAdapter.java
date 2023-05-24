package com.example.learnmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
// Tutor Recycle view adapter
    private  Context mContext;
    private List<TutorGetProfile> tutor_profile;
    private List<TutorGetDetails> tutor_details; //
    String email_txt;

    String sharedEmail, sharedInstrument, sharedProficiency, sharedFee, sharedSunday, sharedMonday, sharedTuesday,
            sharedWednesday, sharedThursday, sharedFriday, sharedSaturday,sharedTime;

    public static String detailsURL = "https://learn-music.click2drinkph.store/php/Display_tutorDetails.php";//

    public RecycleViewAdapter(Context mContext, List<TutorGetProfile> tutor_profile, List<TutorGetDetails> tutor_details ) {
        this.mContext = mContext;
        this.tutor_profile = tutor_profile;
        this.tutor_details = tutor_details;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.tutor_finder_layout,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name; //
        private TextView tv_instrument; //
        private TextView tv_location; //
        private TextView tv_fee;

        private ImageView image;
        private ConstraintLayout tutorContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.NameTutor);
            tv_instrument = (TextView) itemView.findViewById(R.id.TutorInstrument); //
            tv_location = (TextView) itemView.findViewById(R.id.TutorLocation); //
            tv_fee = itemView.findViewById(R.id.tv_fee);
            image = (ImageView) itemView.findViewById(R.id.tutorPicture);
            tutorContainer = (ConstraintLayout) itemView.findViewById(R.id.tutor_Container);

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final TutorGetProfile getProfile = tutor_profile.get(position);
        final TutorGetDetails getDetails = tutor_details.get(position);

        holder.tv_name.setText(tutor_profile.get(position).getName());
        holder.tv_instrument.setText((tutor_details.get(position).getInstrument()));
        holder.tv_fee.setText(tutor_details.get(position).getFee()+" /hr");
        holder.tv_location.setText(tutor_profile.get(position).getMunicipality());
        String image_url = "http://learn-music.click2drinkph.store/images/"+tutor_profile.get(position).getImage_url();
        Glide.with(mContext).load(image_url).into(holder.image);


        holder.tutorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activity = new Intent(mContext,TutorViewDetails.class);
                // basic info
                activity.putExtra("name",getProfile.getName().toString());
                activity.putExtra("age", getProfile.getAge().toString());
                activity.putExtra("gender", getProfile.getGender().toString());
                activity.putExtra("address", getProfile.getAddress().toString());
                activity.putExtra("location", getProfile.getMunicipality().toString());
                activity.putExtra("email", getProfile.getEmailAddress().toString());
                activity.putExtra("mobileNumber", getProfile.getMobileNumber());
                activity.putExtra("image_url", image_url);


//                // details
                activity.putExtra("instrument", getDetails.getInstrument().toString());
                activity.putExtra("proficiency", getDetails.getProficiency().toString());
                activity.putExtra("fee", getDetails.getFee().toString());
                activity.putExtra("sunday", getDetails.getSunday().toString());
                activity.putExtra("monday", getDetails.getMonday().toString());
                activity.putExtra("tuesday", getDetails.getTuesday().toString());
                activity.putExtra("wednesday", getDetails.getWednesday().toString());
                activity.putExtra("thursday", getDetails.getThursday().toString());
                activity.putExtra("friday", getDetails.getFriday().toString());
                activity.putExtra("saturday", getDetails.getSaturday().toString());
                activity.putExtra("timeAm", getDetails.getTime().toString());

                activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tutor_profile.size();
    }


}
