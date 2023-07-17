package fpoly.shopbe.DTO;

import fpoly.shopbe.domain.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    private Long id;
    @NotEmpty(message = "Không để trống tên")
    private String name;
    private CategoryStatus status;
}
