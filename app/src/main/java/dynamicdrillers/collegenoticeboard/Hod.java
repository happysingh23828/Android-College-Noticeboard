package dynamicdrillers.collegenoticeboard;

/**
 * Created by Happy-Singh on 2/28/2018.
 */

public class Hod {
    String Name;
    String Email;
    String Mobileno;
    String Dob;
    String Gender;
    String Photonname;
    String Dept;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhotonname() {
        return Photonname;
    }

    public void setPhotonname(String photonname) {
        Photonname = photonname;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public Hod(String name, String email, String mobileno, String dob, String gender, String photonname, String dept) {

        Name = name;
        Email = email;
        Mobileno = mobileno;
        Dob = dob;
        Gender = gender;
        Photonname = photonname;
        Dept = dept;
    }
}
