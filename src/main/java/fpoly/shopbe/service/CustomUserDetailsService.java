package fpoly.shopbe.service;

import fpoly.shopbe.domain.CustomUserDetails;
import fpoly.shopbe.exception.AccountException;
import fpoly.shopbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var found = dao.findById(username).orElseThrow(()-> new AccountException("Account not found"));

        return new CustomUserDetails(found);
    }
}
