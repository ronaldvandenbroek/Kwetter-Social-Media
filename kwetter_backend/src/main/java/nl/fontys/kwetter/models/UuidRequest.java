package nl.fontys.kwetter.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UuidRequest implements Serializable {

    @Type(type = "uuid-char")
    private UUID id;

    public UuidRequest() {
    }
}
