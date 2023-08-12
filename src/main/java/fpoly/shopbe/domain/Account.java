package fpoly.shopbe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", length = 20)
    private String password;

    @Enumerated
    @Column(name = "account_roles")
    private AccountRoles accountRoles;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "customers_id")
    private Customers customers;

}