
package home.oleg.placenearme.domain.models;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private UserPhoto photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(UserPhoto photo) {
        this.photo = photo;
    }

}
