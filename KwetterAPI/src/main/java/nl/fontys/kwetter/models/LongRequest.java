package nl.fontys.kwetter.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class LongRequest implements Serializable {
    private Long id;

    public LongRequest() {
    }
}
