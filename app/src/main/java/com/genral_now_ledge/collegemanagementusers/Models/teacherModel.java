package com.genral_now_ledge.collegemanagementusers.Models;

public class teacherModel {
    String teacherProfile;
    String teacherName;
    String teacherPhone;
    String teacherPost;
    String teacherSubjects;

    String teacher_uniqueKey ;

    int testImage;

    public teacherModel(String teacherProfile, String teacherName, String teacherPhone, String teacherPost, String teacherSubjects) {
        this.teacherProfile = teacherProfile;
        this.teacherName = teacherName;
        this.teacherPhone = teacherPhone;
        this.teacherPost = teacherPost;
        this.teacherSubjects = teacherSubjects;
    }


/////////////////////////////////////////////
public teacherModel(  String teacherName, String teacherPhone, String teacherPost, String teacherSubjects, int testImage) {

    this.teacherName = teacherName;
    this.teacherPhone = teacherPhone;
    this.teacherPost = teacherPost;
    this.teacherSubjects = teacherSubjects;
    this.testImage = testImage;
}


    public int getTestImage() {
        return testImage;
    }

    public void setTestImage(int testImage) {
        this.testImage = testImage;
    }

    /////////////////////////////////////////////


    public teacherModel() {
    }


    public String getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(String teacherProfile) {
        this.teacherProfile = teacherProfile;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public String getTeacher_uniqueKey() {
        return teacher_uniqueKey;
    }

    public void setTeacher_uniqueKey(String teacher_uniqueKey) {
        this.teacher_uniqueKey = teacher_uniqueKey;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getTeacherPost() {
        return teacherPost;
    }

    public void setTeacherPost(String teacherPost) {
        this.teacherPost = teacherPost;
    }

    public String getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(String teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }


}
