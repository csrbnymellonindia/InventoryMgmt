package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "asset_id")
    private Integer assetId;

    @Column(name = "asset_name")
    private String assetName;

    @ManyToOne
    @JsonIgnore
    private Category category;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "min_quantity")
    private Integer minQuantity;

    @Column(name = "bor_quantity")
    private Integer borQuantity;

    @OneToMany(mappedBy = "inventory")
    private List<BorrowedAssetEntity> borrowRecord;

}
