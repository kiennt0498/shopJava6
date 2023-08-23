package fpoly.shopbe.controllerShop;

import fpoly.shopbe.DTO.OrderDto;
import fpoly.shopbe.service.CustomerService;
import fpoly.shopbe.service.MapValidationErrorService;
import fpoly.shopbe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/sp/order")
public class OrderSPController {
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @Autowired
    MapValidationErrorService errorService;

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getListByCus(id), HttpStatus.OK);

    }
    @GetMapping("/odd/{id}")
    public ResponseEntity getListOddByOdId(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getListOrderDetailByOrderId(id),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity createOrder(@Valid @RequestBody OrderDto dto, BindingResult result){

        ResponseEntity error = errorService.mapValidationFields(result);

        if(error != null){
            return error;
        }

        var saveDto = orderService.insterOrder(dto);

        return new ResponseEntity(saveDto, HttpStatus.CREATED);

    }
}
