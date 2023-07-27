package fpoly.shopbe.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFileInfo {

    private String name;
    private String fileName;
    private String uid;

}
