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

    public class ProductDto implements Serializable {
        private Long id;
        @NotEmpty
        private String name;
        @Min(value = 0)
        private Integer quantity;
        @Min(value = 0)
        private Double price;
        @Min(value = 0)
        @Max(value = 100)
        private Float discount;

        private Long viewCount;
        private Boolean isFeatured;
        private String brief;
        private String description;
        private ProductStatus status;

        private Long categoryId;

        private List<ProductImageDto> images;
        private ProductImageDto image;

        private CategoryDto category;

    }

