package com.taskie.core;

import java.util.Objects;

/**
 * Email address representation
 */
public class Email {

    private final String emailAddress;

    /**
     * Constructs an email address
     *
     * @param emailAddress valid email address string
     */
    public Email(String emailAddress) {
        this.emailAddress = validate(emailAddress);
    }

    private static String validate(String emailAddress) {
        // TODO full pattern validation
        return Objects.requireNonNull(emailAddress, "Address is required");
    }

    /**
     * @return the email address
     */
    @Override
    public String toString() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(emailAddress, email.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }
}
