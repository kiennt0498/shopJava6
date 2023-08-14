package fpoly.shopbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link fpoly.shopbe.domain.Order}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    Long id;
    Date createDate;
    String address;
    Long cusId;
    List<OrderDetailDto> orderDetails;
    CustomersDto customers;
}