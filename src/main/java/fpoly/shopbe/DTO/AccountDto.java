package fpoly.shopbe.DTO;

import fpoly.shopbe.domain.AccountRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    String username;
    String password;
    AccountRoles accountRoles;
}