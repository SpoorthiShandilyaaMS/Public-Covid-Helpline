package com.example.publicohelpline;

public class ComplaintHelperClass {
    String complaint_title,complaint_desc,sector_name,posted_by_name,user_email,user_constituency,image_url,dateTime;

    public ComplaintHelperClass() {
    }

    public ComplaintHelperClass(String image_url) {
        this.image_url = image_url;
    }

    public ComplaintHelperClass(String complaint_title, String complaint_desc, String sector_name, String posted_by_name, String user_email, String user_constituency,String dateTime) {
        this.complaint_title = complaint_title;
        this.complaint_desc = complaint_desc;
        this.sector_name = sector_name;
        this.posted_by_name = posted_by_name;
        this.user_email = user_email;
        this.user_constituency = user_constituency;
        this.dateTime = dateTime;
    }

    public ComplaintHelperClass(String complaint_title, String complaint_desc, String sector_name, String posted_by_name, String user_email, String user_constituency, String image_url,String dateTime) {
        this.complaint_title = complaint_title;
        this.complaint_desc = complaint_desc;
        this.sector_name = sector_name;
        this.posted_by_name = posted_by_name;
        this.user_email = user_email;
        this.user_constituency = user_constituency;
        this.dateTime = dateTime;
        this.image_url = image_url;
    }

    public String getComplaint_title() {
        return complaint_title;
    }

    public void setComplaint_title(String complaint_title) {
        this.complaint_title = complaint_title;
    }

    public String getComplaint_desc() {
        return complaint_desc;
    }

    public void setComplaint_desc(String complaint_desc) {
        this.complaint_desc = complaint_desc;
    }

    public String getSector_name() {
        return sector_name;
    }

    public void setSector_name(String sector_name) {
        this.sector_name = sector_name;
    }

    public String getPosted_by_name() {
        return posted_by_name;
    }

    public void setPosted_by_name(String posted_by_name) {
        this.posted_by_name = posted_by_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_constituency() {
        return user_constituency;
    }

    public void setUser_constituency(String user_constituency) {
        this.user_constituency = user_constituency;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
