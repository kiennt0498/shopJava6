package fpoly.shopbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link fpoly.shopbe.domain.JwtResponse}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto implements Serializable {
    String token;
    String username;
    String role;
}