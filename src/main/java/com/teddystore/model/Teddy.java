package com.teddystore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties(value = {
        "teddyOrder"
})
public final class Teddy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Nullable
    @Column(name = "DETAILS")
    private String details;

    @NotNull
    @Column(name = "SIZE", nullable = false)
    private Double size;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Nullable
    @Column(name = "IMAGE_URL")
    private String imageURL;

    @CreationTimestamp
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @ManyToMany(mappedBy = "teddies")
    @ToString.Exclude
    private List<TeddyOrder> teddyOrder;

    public void updateTeddyDetails(Teddy newTeddyDetails) {
        this.setName(newTeddyDetails.getName());
        this.setDetails(newTeddyDetails.getDetails());
        this.setSize(newTeddyDetails.getSize());
        this.setPrice(newTeddyDetails.getPrice());
        this.setImageURL(newTeddyDetails.getImageURL());
    }
}
