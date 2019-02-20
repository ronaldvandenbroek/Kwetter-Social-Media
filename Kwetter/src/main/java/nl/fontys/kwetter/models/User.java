package main.java.nl.fontys.kwetter.models;

public class User {
    private int id;
    private Role role;
    private Credentials credentials;
    private String name;
    private String bio;
    private String website;
    private String location;
    private byte[] photo;

    private Kwetter[] createdKwetters;
    private Kwetter[] reportedKwetters;
    private Kwetter[] heartedKwetters;

    private User[] followers;
    private User[] following;
}
