package home.oleg.placesnearme.models;

/**
 * Created by Oleg on 12.04.2016.
 */
public class User {

    private long id;

    private String firstName;

    private String lastName;

    private String gender;

    private UserPhoto photo;

    public String getPhoto() {
        return photo.getPrefix() +"100x100"+  photo.getSuffix();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
