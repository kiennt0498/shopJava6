package fpoly.shopbe.DTO;

import fpoly.shopbe.domain.Category;
import fpoly.shopbe.domain.ProductStatus;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Value
public class ProductDto implements Serializable {
    Long id;
    @NotEmpty
    String name;
    @Min(value = 0)
    Integer quantity;
    @Min(value = 0)
    Double price;
    @Min(value = 0)
    @Max(value = 100)
    Float discount;

    Long viewCount;
    Boolean isFeatured;
    String brief;
    String description;
    ProductStatus status;

    private Long categoryId;

    private List<ProductImageDto> images;
    private ProductImageDto image;

    private CategoryDto category;

}