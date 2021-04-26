package com.vavv.web.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "User")
@Table(name = "user")
@NamedNativeQuery(name = "User.getUserByUsernameAndPassword",
        query = "SELECT * FROM User user WHERE BINARY user.username = :username AND BINARY user.password = :password",
        resultClass = User.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(String guid, String firstName, String lastName) {
        this.guid = guid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "guid", unique = true)
    private String guid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "license_num")
    private String licenseNum;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(guid, user.guid) &&
                Objects.equals(licenseNum, user.licenseNum) &&
                Objects.equals(username, user.username) &&
                role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(guid, licenseNum, username, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "guid='" + guid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", licenseNum='" + licenseNum + '\'' +
                ", dob=" + dob +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
