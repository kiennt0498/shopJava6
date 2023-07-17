package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    List<Category> findByNameStartsWith(String name, Pageable pageable);

}