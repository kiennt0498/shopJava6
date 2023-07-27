package fpoly.shopbe.DTO;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Data
@Value
public class ProductImageDto implements Serializable {
    Long id;
    String name;
    String fileName;
    String url;
    private String status;
    private String resp = "{\"status\": \"success\"}";
}