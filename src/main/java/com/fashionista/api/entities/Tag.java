package com.fashionista.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotBlank(message = "Tag name is required.")
    private String name;

    @NotBlank(message = "Tag type is required.")
    private String type;

    @NotBlank(message = "Tag description is required.")
    private String description;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductTag> productTags = new ArrayList<>();

    @CreationTimestamp
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private Date updatedAt;
}
