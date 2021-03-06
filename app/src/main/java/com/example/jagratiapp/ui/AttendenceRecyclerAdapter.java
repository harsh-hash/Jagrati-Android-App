package com.example.jagratiapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jagratiapp.R;
import com.example.jagratiapp.model.Students;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;
//import static com.example.jagratiapp.R.drawable.ic_wrong_checkbox;

public class AttendenceRecyclerAdapter extends RecyclerView.Adapter<AttendenceRecyclerAdapter.ViewHolder>  {
    private Context context;
    private List<Students> studentsList;
    private Map<String,Boolean> recordedAttendance;
    private OnStudentListener onStudentListener;
    private boolean today;

    public AttendenceRecyclerAdapter(Context context, List<Students> studentsList, Map<String, Boolean> recordedAttendance, OnStudentListener onStudentListener,boolean today) {
        this.context = context;
        this.studentsList = studentsList;
        this.recordedAttendance = recordedAttendance;
        this.onStudentListener = onStudentListener;
        this.today = today;
    }

    @NonNull
    @Override
    public AttendenceRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_card,parent,false);
        return new ViewHolder(view,onStudentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Students student = studentsList.get(position);
        holder.studentName.setText(student.getStudentName());
        holder.villageName.setText(student.getVillageName());
        holder.studentID = student.getUid();
        holder.classID = student.getClassID();
        holder.groupID = student.getGroupID();


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        if (student.getStudent_dp() != null) {
            final long FIVE_MEGABYTE = 5 * 1024 * 1024;
            Bitmap bitmap = null;
            storageReference.child("students/" + student.getRollno() + ".jpg")
                    .getBytes(FIVE_MEGABYTE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {

                            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            holder.student_dp.setImageBitmap(bm);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
       /* else
            Toast.makeText(context,"",Toast.LENGTH_SHORT).show();*/

        if(!recordedAttendance.isEmpty()) {
            boolean attendanceState = recordedAttendance.get(student.getUid());
            if (attendanceState) {
                holder.attendanceChecker.setVisibility(View.VISIBLE);
            } else {
                holder.attendanceChecker.setVisibility(View.INVISIBLE);
            }
        }


        if (today) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean state = false;

                        if(recordedAttendance.get(holder.studentID)==null){
                            state = true;
                            holder.attendanceChecker.setVisibility(View.VISIBLE);
                        }
                        else if (recordedAttendance.get(holder.studentID)) {
                            state = false;
                            holder.attendanceChecker.setVisibility(View.INVISIBLE);
                        } else if (!recordedAttendance.get(holder.studentID)) {
                            state = true;
                            holder.attendanceChecker.setVisibility(View.VISIBLE);
                        } else {
                            holder.attendanceChecker.setVisibility(View.INVISIBLE);
                        }

                    onStudentListener.onStudentClick(holder.getAdapterPosition(), state);



                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView studentName;
        private TextView villageName;
        private ImageView attendanceChecker;
        private String studentID;
        private String classID;
        private String groupID;
        private CardView card1;
        private CardView card2;
        private ImageView student_dp;



        private OnStudentListener onStudentListener;


        public ViewHolder(@NonNull View itemView,OnStudentListener onStudentListener) {
            super(itemView);
            this.onStudentListener = onStudentListener;
            this.studentName = itemView.findViewById(R.id.student_name);
            this.villageName = itemView.findViewById(R.id.student_village);
            this.attendanceChecker = itemView.findViewById(R.id.attendance_checker);
            this.student_dp = itemView.findViewById(R.id.student_card_imageView);
            card1 = itemView.findViewById(R.id.card1);
            card2 = itemView.findViewById(R.id.card2);
            card1.setCardBackgroundColor(Color.TRANSPARENT);
            card1.setCardElevation(0);
            card2.setCardElevation(0);
            card2.setCardBackgroundColor(Color.TRANSPARENT);
        }



        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public interface OnStudentListener{
        void onStudentClick(int position,boolean state);
    }
}
