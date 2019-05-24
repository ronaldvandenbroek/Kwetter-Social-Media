package nl.fontys.kwetter.models.hateoas;

import lombok.Data;

@Data
public class Link {
    private String link;
    private String ref;

    public Link(String link, String ref){
        this.link = link;
        this.ref = ref;
    }
}
