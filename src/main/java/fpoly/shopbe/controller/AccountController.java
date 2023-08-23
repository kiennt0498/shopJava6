package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.AccountDto;
import fpoly.shopbe.service.AccountService;
import fpoly.shopbe.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/ad/acc")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    MapValidationErrorService errorService;

    @GetMapping()
    public ResponseEntity getListAccount(){
        return new ResponseEntity<>(service.fillAll(),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createAcc(@Valid @RequestBody AccountDto dto, BindingResult result){
        System.out.println("run");

        ResponseEntity error = errorService.mapValidationFields(result);

        if(error != null){
            return error;
        }
        var savedto = service.insertAccount(dto);
        return new ResponseEntity<>(savedto, HttpStatus.CREATED);

    }

    @PatchMapping("/ud/{username}")
    public ResponseEntity updateAcc(@PathVariable String username,@Valid @RequestBody AccountDto dto, BindingResult result){

        ResponseEntity error = errorService.mapValidationFields(result);

        if(error != null){
            return error;
        }
        var savedto = service.updateAccount(username, dto);
        return new ResponseEntity<>(savedto, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable String id){
        service.deleteAccount(id);
        return new ResponseEntity("Account with username: "+ id + " was deleted", HttpStatus.OK);
    }

}
