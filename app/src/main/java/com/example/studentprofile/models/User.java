package com.example.studentprofile.models;

import java.util.List;

public class User {

    private Long id;

    private String login;

    private String password;

    private Role role;

    private UserInfo info;

    private List<Session> sessions;

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Session> getSessions(){
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }


//    @Override
//    public int hashCode() {
//        Objects.hash(this.id, this.login, this.password);
//        int hash = 7;
//        hash = 79 * hash + Objects.hashCode(this.id);
//        hash = 79 * hash + Objects.hashCode(this.login);
//        hash = 79 * hash + Objects.hashCode(this.password);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final User other = (User) obj;
//        if (!Objects.equals(this.login, other.login)) {
//            return false;
//        }
//        if (!Objects.equals(this.password, other.password)) {
//            return false;
//        }
//        return Objects.equals(this.id, other.id);
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
