package fpoly.shopbe.controller;



import fpoly.shopbe.DTO.ProductImageDto;

import fpoly.shopbe.service.FileStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@CrossOrigin
@RequestMapping("api/ad/product")
public class ProductController {
    @Autowired
    FileStorageService file;

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


}
