package nl.fontys.kwetter.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Kwetter {

    private @Id @GeneratedValue Long id;

    private String text;
    private int reports;
    private int hearts;
    private Set<String> tags;

    private Set<User> mentions;
    private User owner;
    private Date dateTime;

    public Kwetter(String text, Set<String> tags, Set<User> mentions, User owner, Date dateTime){
        this.text = text;
        this.tags = tags;
        this.mentions = mentions;
        this.owner = owner;
        this.dateTime = dateTime;

        owner.addCreatedKwetter(this);
    }

    public void report() {
        reports += 1;
    }

    public void heart() {
        hearts += 1;
    }
}
