package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.CategoryDto;
import fpoly.shopbe.domain.Category;
import fpoly.shopbe.exception.CategoryException;
import fpoly.shopbe.service.CategoryService;
import fpoly.shopbe.service.MapValidationErrorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/ad/category")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryService service;

    @Autowired
    MapValidationErrorService errorService;

    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody CategoryDto dto, BindingResult result){

        ResponseEntity responseEntity = errorService.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }

        Category entity = new Category();

        BeanUtils.copyProperties(dto, entity);

        entity = service.save(entity);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());

        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PatchMapping("ud/{id}")
    public ResponseEntity updateCategory(@PathVariable("id") Long id,
                                         @Valid @RequestBody CategoryDto dto,
                                         BindingResult result){

        ResponseEntity responseEntity = errorService.mapValidationFields(result);

        if(responseEntity != null){
            return responseEntity;
        }


        Category entity = new Category();

        BeanUtils.copyProperties(dto, entity);

        entity = service.update(id,entity);

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());

        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity getCategories(){
        return new ResponseEntity(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity getCategoriesPage(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable){
        return new ResponseEntity(service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findCategory(@PathVariable("id") Long id){
        return new ResponseEntity(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        service.deleteById(id);
        return new ResponseEntity<>("Delete Done", HttpStatus.OK);
    }

}
