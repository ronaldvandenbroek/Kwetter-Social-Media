package nl.fontys.kwetter.service;

public interface IEmailService {

    void sendMessage(String to, String subject, String jwtToken, String userUuid);
}
