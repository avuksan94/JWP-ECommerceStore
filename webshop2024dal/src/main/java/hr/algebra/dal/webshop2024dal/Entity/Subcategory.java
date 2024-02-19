package hr.algebra.dal.webshop2024dal.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subcategories")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Subcategory(Long subcategoryId, String name) {
        this.subcategoryId = subcategoryId;
        this.name = name;
    }

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Subcategory(Long subcategoryId, String name, Category category) {
        this.subcategoryId = subcategoryId;
        this.name = name;
        this.category = category;
    }
}
