package fpoly.shopbe.service;

import fpoly.shopbe.DTO.AccountDto;
import fpoly.shopbe.domain.*;
import fpoly.shopbe.exception.AccountException;
import fpoly.shopbe.repository.AccountRepository;
import fpoly.shopbe.repository.CustomersRepository;
import fpoly.shopbe.repository.ProductImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository service;

    @Autowired
    private ProductImageRepository imgService;

    @Autowired
    private CustomersRepository csService;



    @Transactional(rollbackOn = Exception.class)
    public AccountDto insertAccount(AccountDto dto){
        var found =  service.findById(dto.getUsername()).orElse(null);

        if(found != null){
            throw new AccountException("Account already exists");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(dto,entity);

        Customers cs = new Customers();


        var saveCs = csService.save(cs);

        entity.setCustomers(saveCs);


        service.save(entity);


        return dto;

    }

    @Transactional(rollbackOn = Exception.class)
    public AccountDto updateAccount(String username, AccountDto dto){
        var found =  service.findById(username).orElseThrow(()->new AccountException("Account not found"));

        BeanUtils.copyProperties(dto,found, "customers");

        service.save(found);


        return dto;

    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteAccount(String username){
        var found = service.findById(username).orElseThrow(()->new AccountException("Account not found"));


        service.delete(found);
    }





    public List fillAll(){return (List) service.findAll();}

    public Account findByid(String id){return service.findById(id).get();}
}
