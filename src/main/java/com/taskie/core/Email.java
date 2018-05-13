package com.taskie.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

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
        return requireNonNull(emailAddress, "Address is required");
    }

    /**
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return Objects.equals(emailAddress, email.emailAddress);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(emailAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .toString();
    }
}
