package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "asset_received")
public class AssetReceived {
    @Id
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "asset_id")
    private Integer assetId;

    @Column(name = "rec_quantity")
    private Integer recQuantity;

    @Column(name = "rec_date")
    private LocalDate recDate;

    @Column(name = "rec_location")
    private String recLocation;

    public Integer getRecId() {
        return transactionId;
    }

    public void setRecId(Integer transactionIdId) {
        this.transactionId = transactionId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getRecQuantity() {
        return recQuantity;
    }

    public void setRecQuantity(Integer recQuantity) {
        this.recQuantity = recQuantity;
    }

    public LocalDate getRecDate() {
        return recDate;
    }

    public void setRecDate(LocalDate recDate) {
        this.recDate = recDate;
    }

    public String getRecLocation() {
        return recLocation;
    }

    public void setRecLocation(String recLocation) {
        this.recLocation = recLocation;
    }
}
