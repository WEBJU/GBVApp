package models;

public class Profile {
    private String user_id ,phone,address,kin,kinContact,relationship;

    public Profile() {
    }

    public Profile(String user_id, String phone, String address, String kin, String kinContact, String relationship) {
        this.user_id = user_id;
        this.phone = phone;
        this.address = address;
        this.kin = kin;
        this.kinContact = kinContact;
        this.relationship = relationship;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKin() {
        return kin;
    }

    public void setKin(String kin) {
        this.kin = kin;
    }

    public String getKinContact() {
        return kinContact;
    }

    public void setKinContact(String kinContact) {
        this.kinContact = kinContact;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
