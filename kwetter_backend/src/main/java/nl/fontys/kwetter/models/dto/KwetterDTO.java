package nl.fontys.kwetter.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class KwetterDTO extends ResourceSupport {
    private UUID uuid;
    private String text;
    private int reports;
    private int hearts;
    private Set<String> tags;
    private Set<User> mentions;
    private User owner;
    private Date dateTime;

    public KwetterDTO(String text, User owner, Date dateTime) {
        this.text = text;
        this.owner = owner;
        this.dateTime = dateTime;
    }

    public KwetterDTO() {
        // Empty constructor
    }

    public KwetterDTO(Kwetter kwetter) {
        this.uuid = kwetter.getUuid();
        this.text = kwetter.getText();
        this.reports = kwetter.getReports();
        this.hearts = kwetter.getHearts();
        this.tags = kwetter.getTags();
        this.mentions = kwetter.getMentions();
        this.owner = kwetter.getOwner();
    }
}
