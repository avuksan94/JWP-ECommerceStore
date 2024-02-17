package hr.algebra.dal.webshop2024dal.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

