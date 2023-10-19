package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a User in the address book.
 */
public class User {

    // Identity fields
    private final Username username;
    private final Password hashedPassword;


    /**
     * Every field must be present and not null.
     * This constructor assumes a non-hashed password is passed in
     */
    public User(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.hashedPassword = new Password(password.toString());
    }

    /**
     * Overloaded constructor for creating a new user with a hashed password
     * @param isHashed indicates whether the password is hashed
     */
    public User(Username username, Password password, boolean isHashed) {
        requireAllNonNull(username, password);
        this.username = username;
        this.hashedPassword = isHashed ? password : new Password(password.toString());
    }

    /**
     * Returns the username of the user.
     * @return username
     */
    public Username getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     * @return
     */
    public Password getPassword() {
        return hashedPassword;
    }

    /**
     * Returns true if both persons have the same username.
     */
    public boolean hasSameUsername(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
            && otherUser.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both users have the same username and password.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return username.equals(otherUser.username)
            && hashedPassword.equals(otherUser.hashedPassword);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, hashedPassword);
    }

    @Override
    public String toString() {
        // does not show password for security reason
        return new ToStringBuilder(this)
            .add("userName", username)
            .toString();
    }

}
