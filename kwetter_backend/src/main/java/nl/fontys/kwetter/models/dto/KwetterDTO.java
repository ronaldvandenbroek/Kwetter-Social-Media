package nl.fontys.kwetter.models.dto;

import lombok.Data;
import nl.fontys.kwetter.models.entity.User;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class KwetterDTO {
    private UUID id;
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
    }
}
