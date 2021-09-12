package models;

public class ViolenceReports {
    private String type,description,location,phone,user_id;

    public ViolenceReports() {
    }

    public ViolenceReports(String type, String description, String location, String phone, String user_id) {
        this.type = type;
        this.description = description;
        this.location = location;
        this.phone = phone;
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
