package fpoly.shopbe.service;

import fpoly.shopbe.DTO.AccountDto;
import fpoly.shopbe.DTO.CustomersDto;
import fpoly.shopbe.domain.ProductImage;
import fpoly.shopbe.exception.AccountException;
import fpoly.shopbe.exception.CustomerException;
import fpoly.shopbe.repository.CustomersRepository;
import fpoly.shopbe.repository.ProductImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomersRepository dao;
    @Autowired
    private ProductImageRepository imgDao;

    @Transactional(rollbackOn = Exception.class)
    public CustomersDto updateCus(Long id, CustomersDto dto){

        var found = dao.findById(id).orElseThrow(()-> new CustomerException("Customer not found"));

        String ignoreFields[] = new String[]{"createDate", "photoImage"};

        BeanUtils.copyProperties(dto, found, ignoreFields);

        if(found.getPhotoImage() != null){
            if(dto.getPhotoImage().getId() != found.getPhotoImage().getId()){
                var imgDel = found.getPhotoImage();
                imgDao.delete(imgDel);
            }
        }

        if(dto.getPhotoImage() != null && dto.getPhotoImage().getId() == null){
            ProductImage img = new ProductImage();

            BeanUtils.copyProperties(dto.getPhotoImage(), img);

            var saveImg = imgDao.save(img);
            found.setPhotoImage(saveImg);
            dto.getPhotoImage().setId(saveImg.getId());
        }


        var saveDto = dao.save(found);

        return dto;

    }
    public CustomersDto getByUsername(String username){
        var found = dao.findByAccount_Username(username);

        CustomersDto dto = new CustomersDto();
        BeanUtils.copyProperties(found, dto);
        AccountDto ac = new AccountDto();
        ac.setUsername(found.getAccount().getUsername());
        dto.setUsername(ac);
        return dto;
    }
}
