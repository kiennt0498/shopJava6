package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.CustomersDto;
import fpoly.shopbe.service.CustomerService;
import fpoly.shopbe.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("api/ad/cus")
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    private MapValidationErrorService errorService;

    @PatchMapping("/{id}")
    public ResponseEntity updateCus(@PathVariable Long id, @Valid @RequestBody CustomersDto dto, BindingResult result){
        ResponseEntity error = errorService.mapValidationFields(result);

        if(error != null){
            return error;
        }
        var savedto = service.updateCus(id,dto);
        return new ResponseEntity<>(savedto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity getCus(@PathVariable String id){
        CustomersDto dto =  service.getByUsername(id);
        System.out.println(dto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
