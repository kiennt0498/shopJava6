package fpoly.shopbe.repository;

import fpoly.shopbe.domain.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
}