package fpoly.shopbe.controller;



import fpoly.shopbe.DTO.ProductDto;
import fpoly.shopbe.DTO.ProductImageDto;

import fpoly.shopbe.exception.FileStorageException;
import fpoly.shopbe.repository.ProductRepository;
import fpoly.shopbe.service.FileStorageService;
import fpoly.shopbe.service.MapValidationErrorService;
import fpoly.shopbe.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("api/ad/product")
public class ProductController {
    @Autowired
    FileStorageService file;
    @Autowired
    MapValidationErrorService errorService;
    @Autowired
    ProductService service;

    @PostMapping(value = "/images/one",consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile imageFile ){
        var fileInfo = file.storeUploadedProductImageFile(imageFile);
        ProductImageDto dto = new ProductImageDto();
        BeanUtils.copyProperties(fileInfo,dto);
        dto.setStatus("done");
        dto.setUrl("http://localhost:8080/api/ad/product/images/" + fileInfo.getFileName());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity createProduct(@Valid @RequestBody ProductDto dto, BindingResult result){
        ResponseEntity error = errorService.mapValidationFields(result);

        if(error != null){
            return error;
        }

        var savedDto = service.insertProduct(dto);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") Long id,
                                        @Valid @RequestBody ProductDto dto,
                                        BindingResult result){
        ResponseEntity responseEntity = errorService.mapValidationFields(result);
        if(responseEntity != null){
            return responseEntity;
        }

        var updateDto = service.updateProduct(id, dto);

        return new ResponseEntity<>(updateDto, HttpStatus.CREATED);
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

    @GetMapping("/find")
    public ResponseEntity findProduct(@RequestParam("query") String query, @PageableDefault(size = 5, sort = "name",
                                            direction = Sort.Direction.ASC)Pageable pageable){
        return new ResponseEntity(service.getProductByName(query,pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable long id){
        service.deleteproduct(id);

        return new ResponseEntity<>("Product with id: "+ id + " was deleted", HttpStatus.OK);
    }
}
