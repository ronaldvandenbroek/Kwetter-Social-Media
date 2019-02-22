package nl.fontys.kwetter.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"reports", "hearts", "tags", "mentions"})
public class Kwetter {

    private @Id
    @GeneratedValue
    Long id;

    @Size(max = 140)
    @NotNull
    private String text;
    private int reports;
    private int hearts;
    private Set<String> tags;

    private Set<User> mentions;
    private User owner;

    @NotNull
    private Date dateTime;

    public Kwetter(Kwetter toBeClonedKwetter) {
        this.id = toBeClonedKwetter.getId();
        this.text = toBeClonedKwetter.getText();
        this.reports = toBeClonedKwetter.getReports();
        this.hearts = toBeClonedKwetter.getHearts();
        this.tags = toBeClonedKwetter.getTags();
        this.mentions = toBeClonedKwetter.getMentions();
        this.owner = toBeClonedKwetter.getOwner();
        this.dateTime = toBeClonedKwetter.getDateTime();
    }

    public Kwetter(String text, User owner, Date dateTime, Long tempId) {
        this(text, new HashSet<>(), new HashSet<>(), owner, dateTime);
        this.id = tempId;
    }

    public Kwetter(String text, User owner, Date dateTime) {
        this(text, new HashSet<>(), new HashSet<>(), owner, dateTime);
    }

    public Kwetter(String text, Set<String> tags, Set<User> mentions, User owner, Date dateTime) {
        this.text = text;
        this.owner = owner;
        this.dateTime = dateTime;

        if (tags != null) {
            this.tags = tags;
        } else {
            this.tags = new HashSet<>();
        }

        if (mentions != null) {
            this.mentions = mentions;
        } else {
            this.mentions = new HashSet<>();
        }

        this.reports = 0;
        this.hearts = 0;
        owner.addCreatedKwetter(this);
    }

    public void report() {
        reports += 1;
    }

    public void heart() {
        hearts += 1;
    }

    public void removeReport() {
        reports -= 1;
    }

    public void removeHeart() {
        hearts -= 1;
    }

    public void removeOwner() {
        owner = null;
    }
}
