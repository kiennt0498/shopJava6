package fpoly.shopbe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity{
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "address", nullable = false, length = 100)
    private String address;



    @JsonIgnore
    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customers customers;

    @PrePersist
    public void prePersist() {
        createDate= new Date();
    }
}