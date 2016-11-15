package ubay.id.model;

/**
 * Created              : Rahman on 10/26/2016.
 * Date Created         : 10/26/2016 / 2:54 PM.
 * ===================================================
 * Package              : ubay.id.model.
 * Project Name         : graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class UserData {
    private int id;
    //table user key
    private int uid;
    private String uname;
    private String email;
    private String role_id;

    public UserData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
