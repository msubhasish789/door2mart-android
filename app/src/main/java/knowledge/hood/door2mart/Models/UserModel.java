package knowledge.hood.door2mart.Models;

public class UserModel {
    String user_id;
    String user_mobile,user_name,user_address,user_pincode,status;

    public UserModel(String user_id, String user_mobile, String user_name, String user_address, String user_pincode, String status) {
        this.user_id = user_id;
        this.user_mobile = user_mobile;
        this.user_name = user_name;
        this.user_address = user_address;
        this.user_pincode = user_pincode;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_pincode() {
        return user_pincode;
    }

    public void setUser_pincode(String user_pincode) {
        this.user_pincode = user_pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
