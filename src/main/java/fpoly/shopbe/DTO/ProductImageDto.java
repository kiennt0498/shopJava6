package fpoly.shopbe.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductImageDto implements Serializable {
    private Long id;
    private String name;
    private String fileName;
    private String url;
    private String status;
    private String resp = "{\"status\": \"success\"}";
}