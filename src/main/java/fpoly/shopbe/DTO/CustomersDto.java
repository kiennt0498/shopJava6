package fpoly.shopbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersDto implements Serializable {
    Long id;
    String fullname;
    String email;
    Date createDate;
    String phone;
    String address;
    ProductImageDto photoImage;
    AccountDto username;

}