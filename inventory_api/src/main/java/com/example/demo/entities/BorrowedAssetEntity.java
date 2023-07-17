package com.example.demo.entities;

import com.example.demo.entities.Inventory;
import com.example.demo.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "asset_borrowed")
public class BorrowedAssetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name="asset_id",referencedColumnName = "asset_id")
    private Inventory inventory;

    @Column(name = "bor_quantity")
    private Integer borQuantity;

    @Column(name = "bor_date")
    private LocalDate borDate;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @Column(name = "due_date")
    private LocalDate dueDate;
}
