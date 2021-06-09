package com.example.studentprofile.models;


public class Role {

    private Long id;

    private String role;

//    @OneToMany(mappedBy = "role")
//    private List<User> users;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String role) {
        this.role = role;
    }

    public String getName() {
        return role;
    }

    @Override
    public String toString() {
        return  getName();
    }
}
