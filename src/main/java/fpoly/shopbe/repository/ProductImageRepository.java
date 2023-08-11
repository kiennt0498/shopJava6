package fpoly.shopbe.repository;

import fpoly.shopbe.domain.ProductImage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Long> {

}