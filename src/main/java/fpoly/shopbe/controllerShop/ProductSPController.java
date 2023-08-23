package fpoly.shopbe.controllerShop;

import fpoly.shopbe.exception.FileStorageException;
import fpoly.shopbe.service.FileStorageService;
import fpoly.shopbe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Service
@CrossOrigin
@RequestMapping("api/sp/product")
public class ProductSPController {
    @Autowired
    ProductService service;
    @Autowired
    FileStorageService file;

    @GetMapping
    public ResponseEntity getList(){
        return new ResponseEntity(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("images/{filename:.+}")
    public ResponseEntity dowloadFile(@PathVariable String filename, HttpServletRequest req){
        Resource resource = file.loadProductImageFileAsResource(filename);

        String contentType = null;
        try {
            contentType = req.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            throw new FileStorageException("Could not determine file type.");
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename\""
                        + resource.getFilename()+"\"").body(resource);
    }
}
