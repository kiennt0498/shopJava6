package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.ManufacturerDto;
import fpoly.shopbe.domain.Manufacturer;
import fpoly.shopbe.exception.FileStorageException;
import fpoly.shopbe.service.FileStorageService;
import fpoly.shopbe.service.ManufacturerService;
import fpoly.shopbe.service.MapValidationErrorService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @PatchMapping(value = "/ud/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateManufacturer(@Valid @ModelAttribute ManufacturerDto dto,
                                                BindingResult result, @PathVariable Long id){
        ResponseEntity responseEntity = error.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }

        Manufacturer entity = service.updateManufacturer(id, dto);
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

    @GetMapping()
    public ResponseEntity<?> getListManu(){
        var list = service.findAll();
        var newList = list.stream().map(item -> {
            ManufacturerDto dto = new ManufacturerDto();
            BeanUtils.copyProperties(item,dto);
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(newList,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getListManu(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC)
                                         Pageable pageable){
        var list = service.findAll(pageable);
        var newList = list.stream().map(item -> {
            ManufacturerDto dto = new ManufacturerDto();
            BeanUtils.copyProperties(item,dto);
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(newList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findByid(@PathVariable("id") Long id){
        var entity = service.findById(id);
        ManufacturerDto dto = new ManufacturerDto();
        BeanUtils.copyProperties(entity, dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteManu(@PathVariable Long id){
        service.deleteManu(id);
        return new ResponseEntity<>("Delete Done", HttpStatus.OK);
    }
}
