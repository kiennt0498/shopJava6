package fpoly.shopbe.controller;

import fpoly.shopbe.DTO.OrderDto;
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
@RequestMapping("api/ad/order")
public class OrderController {
    @Autowired
    OrderService service;

    @Autowired
    MapValidationErrorService errorService;



    @GetMapping()
    public ResponseEntity getListOd(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/odd/{id}")
    public ResponseEntity getListOddByOdId(@PathVariable Long id){
        return new ResponseEntity<>(service.getListOrderDetailByOrderId(id),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getListOdByCus(@PathVariable Long id){
        return new ResponseEntity(service.getListByCus(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id){
        service.deleteOdd(id);
        return new ResponseEntity("Order with id: "+ id + " was deleted", HttpStatus.OK);
    }

    @DeleteMapping("/odd/{id}")
    public ResponseEntity deleteOrderDetail(@PathVariable Long id){
        service.deleteOrderDetail(id);
        return new ResponseEntity("Order with id: "+ id + " was deleted", HttpStatus.OK);
    }
}
