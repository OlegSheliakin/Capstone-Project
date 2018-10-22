
package home.oleg.placenearme.models;

import java.util.Objects;

public class Contact {

    private String formattedPhone;

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(formattedPhone, contact.formattedPhone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(formattedPhone);
    }
}
