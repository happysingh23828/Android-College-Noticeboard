package dynamicdrillers.collegenoticeboard;

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class Student {

    String name;
    String email;
    String mobileno;
    String dob;
    String gender;
    String collegecode;
    String studentproilename;
    String dept;
    String sem;
    String tgemail;
    String enrollment;

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

    public String getStudentproilename() {
        return studentproilename;
    }

    public void setStudentproilename(String studentproilename) {
        this.studentproilename = studentproilename;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getTgemail() {
        return tgemail;
    }

    public void setTgemail(String tgemail) {
        this.tgemail = tgemail;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public Student(String name, String email, String mobileno, String dob, String gender, String collegecode, String studentproilename, String dept, String sem, String tgemail, String enrollment) {

        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.dob = dob;
        this.gender = gender;
        this.collegecode = collegecode;
        this.studentproilename = studentproilename;
        this.dept = dept;
        this.sem = sem;
        this.tgemail = tgemail;
        this.enrollment = enrollment;
    }
}
