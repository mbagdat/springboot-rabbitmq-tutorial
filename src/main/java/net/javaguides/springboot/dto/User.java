package net.javaguides.springboot.dto;

import lombok.Data;
import net.javaguides.springboot.enumuration.UserStatus;

import java.util.Date;

@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateSent;
    private UserStatus status;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                '}';
    }
}
