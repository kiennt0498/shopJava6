package fpoly.shopbe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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


    @PrePersist
    public void prePersist() {
        createDate = new Date();
    }
}