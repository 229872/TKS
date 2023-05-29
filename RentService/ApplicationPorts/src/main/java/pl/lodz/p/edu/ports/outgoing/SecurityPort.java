package pl.lodz.p.edu.ports.outgoing;

public interface SecurityPort {
    String generateToken(String subject, String role);

    boolean validateToken(String token);

    String getSubject(String token);

    String getRole(String token);
}
