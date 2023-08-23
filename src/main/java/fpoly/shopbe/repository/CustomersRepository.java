package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Customers;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomersRepository extends PagingAndSortingRepository<Customers, Long> {
    Customers findByAccount_Username(String username);
}