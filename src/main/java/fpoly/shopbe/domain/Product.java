package fpoly.shopbe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "brief", length = 200)
    private String brief;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "status")
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "product_image_id")
    private ProductImage image;

    @ManyToMany
    @JoinTable(name = "product_productImages",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "productImages_id"))
    private Set<ProductImage> images = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createDate = new Date();

        if(isFeatured == null) isFeatured = false;

        viewCount = 0L;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
}