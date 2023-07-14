package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;


@Entity
@Table(name = "asset_borrowed")
public class AssetBorrowed {
    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "asset_id")
    private Integer assetId;

    @Column(name = "bor_quantity")
    private Integer borQuantity;

    @Column(name = "bor_date")
    private LocalDate borDate;

    @Column(name = "bor_user_id")
    private Integer borUserId;

    @Column(name = "bor_return_date")
    private LocalDate borReturnDate;

    public Integer getBorId() {
        return transactionId;
    }

    public void setBorId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }


    public Integer getBorQuantity() {
        return borQuantity;
    }

    public void setBorQuantity(Integer borQuantity) {
        this.borQuantity = borQuantity;
    }

    public LocalDate getBorDate() {
        return borDate;
    }

    public void setBorDate(LocalDate borDate) {
        this.borDate = borDate;
    }

    public Integer getBorUserId() {
        return borUserId;
    }

    public void setBorUserId(Integer borUserId) {
        this.borUserId = borUserId;
    }

    public LocalDate getBorReturnDate() {
        return borReturnDate;
    }

    public void setBorReturnDate(LocalDate borReturnDate) {
        this.borReturnDate = borReturnDate;
    }
}
