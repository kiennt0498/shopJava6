package fpoly.shopbe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorys")
public class Category  extends AbstractEntity{

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated
    @Column(name = "status", nullable = false)
    private CategoryStatus status;

}