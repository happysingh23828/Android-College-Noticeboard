package dynamicdrillers.collegenoticeboard;

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class Faculty {
    String name;
    String email;
    String mobileno;
    String dob;
    String gender;
    String collegecode;
    String profilename;
    String tgflag;
    String tgsem;

    public Faculty(String name, String email, String mobileno, String dob, String gender,
                   String collegecode, String profilename, String tgflag, String tgsem, String dept, String role) {
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.dob = dob;
        this.gender = gender;
        this.collegecode = collegecode;
        this.profilename = profilename;
        this.tgflag = tgflag;
        this.tgsem = tgsem;
        this.dept = dept;
        this.role = role;
    }

    String dept;

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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollegecode() {
        return collegecode;
    }

    public void setCollegecode(String collegecode) {
        this.collegecode = collegecode;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getTgflag() {
        return tgflag;
    }

    public void setTgflag(String tgflag) {
        this.tgflag = tgflag;
    }

    public String getTgsem() {
        return tgsem;
    }

    public void setTgsem(String tgsem) {
        this.tgsem = tgsem;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    String role;


}
