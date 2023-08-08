package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);
}