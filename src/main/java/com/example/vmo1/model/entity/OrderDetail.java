package com.example.vmo1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated_at;

    @Column(name = "is_deleted")
    private boolean is_deleted;
}
