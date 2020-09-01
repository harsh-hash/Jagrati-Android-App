package com.example.jagratiapp.model;

import android.os.Parcelable;

import java.io.StringReader;

public class Students {

    private String studentName;
    private String className;
    private String classID;
    private String groupID;
    private String groupName;
    private String guardianName;
    private String mobileNo;
    private String villageName;
    private String Uid;

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }



    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Students(String classID,String groupID,String studentName, String className, String groupName, String guardianName, String mobileNo, String villageName) {
        this.classID = classID;
        this.groupID = groupID;
        this.studentName = studentName;
        this.className = className;
        this.groupName = groupName;
        this.guardianName = guardianName;
        this.mobileNo = mobileNo;
        this.villageName = villageName;
    }

    public Students(){}

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}
