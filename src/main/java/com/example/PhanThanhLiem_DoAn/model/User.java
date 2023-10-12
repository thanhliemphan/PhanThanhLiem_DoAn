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
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username","image","number_phone"}))
public class User {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String password;
    @Column(name = "number_phone")
    private String number_phone;
    private String country;
    @Lob
    @Column(name = "image",columnDefinition = "MEDIUMBLOB")
    private String image;

    @Column(name = "city")
    private String city;

    @Column(name = "created_time", updatable = false)
    private Date createdTime;
    @Column(columnDefinition = "boolean default false")
    private boolean enabled;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PaymentInfo> paymentHistories;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;
}
