package com.fashionista.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    private double totalPrice = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<PurchaseItem> items = new ArrayList<>();

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt = new Date();

    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;

    public Purchase(double totalPrice, User user) {
        this.totalPrice = totalPrice;
        this.user = user;
    }
}
