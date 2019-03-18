package nl.fontys.kwetter.models;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UUIDRequest implements Serializable {
    private UUID id;

    public UUIDRequest() {
    }
}
