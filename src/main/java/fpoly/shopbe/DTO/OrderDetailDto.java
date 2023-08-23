package fpoly.shopbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto implements Serializable {
    Long id;

    Integer quantity;
    Long productId;
    ProductDto product;

}