package Serialization.User;

import java.util.ArrayList;

public class User {
    private boolean active;
    private String email;
    private String name;
    private ArrayList<Roles> roles;

    public User(boolean active, String email, String name, ArrayList<Roles> roles) {
        this.active = active;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
    public User( String email, String name, ArrayList<Roles> roles) {
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Roles> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Roles> roles) {
        this.roles = roles;
    }
}
