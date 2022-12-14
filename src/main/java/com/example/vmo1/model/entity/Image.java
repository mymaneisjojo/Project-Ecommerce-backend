package com.example.vmo1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileType")
    private String fileType;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated_at;

    @Column(name = "is_deleted")
    private boolean is_deleted = false;

}
