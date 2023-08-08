package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Product;
import fpoly.shopbe.domain.ProductImage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Long> {

}