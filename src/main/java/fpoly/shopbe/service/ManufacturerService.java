package fpoly.shopbe.service;

import fpoly.shopbe.DTO.ManufacturerDto;
import fpoly.shopbe.domain.Manufacturer;
import fpoly.shopbe.exception.ManufacturerException;
import fpoly.shopbe.repository.ManufacturerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Manufacturer updateManufacturer(Long id, ManufacturerDto dto){
        var found = dao.findById(id);

        if(found.isEmpty()){
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

    public List<?> findAll(){
        return (List<?>) dao.findAll();
    }

    public Page<Manufacturer> findAll(Pageable pageable){
        return dao.findAll(pageable);
    }

    public Manufacturer findById(Long id){
        Optional<Manufacturer> found = dao.findById(id);

        if(found.isEmpty()){
            throw new ManufacturerException("Manufacturer with id "+ id + " does not existed");
        }

        return found.get();
    }

    public void deleteManu(Long id){
        Manufacturer entity = findById(id);

        dao.delete(entity);
    }

}
