package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.ManufacturerDto;
import fpoly.shopbe.domain.Manufacturer;
import fpoly.shopbe.exception.FileStorageException;
import fpoly.shopbe.service.FileStorageService;
import fpoly.shopbe.service.ManufacturerService;
import fpoly.shopbe.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/ad/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerService service;

    @Autowired
    private MapValidationErrorService error;

    @Autowired
    private FileStorageService file;
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createManufacturer(@Valid @ModelAttribute ManufacturerDto dto, BindingResult result){
        ResponseEntity responseEntity = error.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }

        Manufacturer entity = service.insertManufacturer(dto);
        dto.setId(entity.getId());
        dto.setLogo(entity.getLogo());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("logo/{filename:.+}")
    public ResponseEntity dowloadFile(@PathVariable String filename, HttpServletRequest req){
        Resource resource = file.loadLogoFileAsResource(filename);

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
