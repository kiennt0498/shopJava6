package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findByCustomers_Id(Long id);

}