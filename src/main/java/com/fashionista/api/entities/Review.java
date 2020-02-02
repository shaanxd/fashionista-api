package com.fashionista.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotBlank(message = "Review title is required.")
    private String title;

    @NotBlank(message = "Review description is required.")
    private String description;

    @DecimalMin(value = "0.5", message = "Rating should be at least 1.0")
    @DecimalMax(value = "5.0", message = "Rating should not exceed 5.0")
    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;
}
