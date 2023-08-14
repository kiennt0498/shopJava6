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
@Table(name = "Customers")
public class Customers extends AbstractEntity{

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "email", length = 100)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "photo_image_id")
    private ProductImage photoImage;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "customers", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createDate = new Date();
    }
}