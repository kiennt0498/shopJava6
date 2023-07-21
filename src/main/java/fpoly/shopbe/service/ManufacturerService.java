package fpoly.shopbe.service;

import fpoly.shopbe.DTO.ManufacturerDto;
import fpoly.shopbe.domain.Manufacturer;
import fpoly.shopbe.exception.ManufacturerException;
import fpoly.shopbe.repository.ManufacturerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository dao;

    @Autowired
    private FileStorageService fileService;

    public Manufacturer insertManufacturer(ManufacturerDto dto){
        List<?> foundlist = dao.findByNameContainsIgnoreCase(dto.getName());

        if(foundlist.size() > 0){
            throw new ManufacturerException("Manufacturer name is existed");
        }

        Manufacturer entity = new Manufacturer();
        BeanUtils.copyProperties(dto, entity);

        if(dto.getLogoFile() != null) {
            String filename = fileService.storeLogoFile(dto.getLogoFile());

            entity.setLogo(filename);

        }
        return dao.save(entity);
    }
}
