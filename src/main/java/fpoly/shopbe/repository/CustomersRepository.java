package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Customers;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomersRepository extends PagingAndSortingRepository<Customers, Long> {
}