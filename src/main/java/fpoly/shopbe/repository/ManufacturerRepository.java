package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Manufacturer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ManufacturerRepository extends PagingAndSortingRepository<Manufacturer, Long> {
    List<Manufacturer> findByNameContainsIgnoreCase(String name);

    List<Manufacturer> findByIdNotAndNameContainsIgnoreCase(Long id, String name);


}