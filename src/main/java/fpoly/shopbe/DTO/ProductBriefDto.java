package fpoly.shopbe.DTO;

import fpoly.shopbe.domain.ProductStatus;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductBriefDto implements Serializable {
    private Long id;

    private String name;

    private Integer quantity;

    private Double price;

    private Float discount;

    private Long viewCount;
    private Boolean isFeatured;
    private String brief;
    private String description;
    private ProductStatus status;

    private String categoryName;

    private String imageFileName;

}

