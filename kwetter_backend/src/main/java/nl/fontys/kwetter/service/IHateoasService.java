package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.FailedToAddLinksException;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;

import java.util.List;

public interface IHateoasService {

    UserDTO getUserDTOWithLinks(User user) throws FailedToAddLinksException;

    List<UserDTO> getUserDTOWithLinks(List<User> users) throws FailedToAddLinksException;

    KwetterDTO getKwetterDTOWithLinks(Kwetter kwetter) throws FailedToAddLinksException;

    List<KwetterDTO> getKwetterDTOWithLinks(List<Kwetter> kwetters) throws FailedToAddLinksException;
}
