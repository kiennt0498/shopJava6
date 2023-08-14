package fpoly.shopbe.repository;

import fpoly.shopbe.domain.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder_Id(Long id);

}