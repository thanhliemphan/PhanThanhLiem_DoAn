package com.example.PhanThanhLiem_DoAn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @Column(length = 5000)
    private String comment;
    @Column
    private double rating;
    @Column(name = "created_time", updatable = false)
    private Date createdTime;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "product_review",
//            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "review_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
//    private List<Product> products;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
