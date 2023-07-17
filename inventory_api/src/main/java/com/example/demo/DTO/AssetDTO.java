package com.example.demo.DTO;

import lombok.Data;

@Data
public class AssetDTO {
    private Integer assetId;
    private String assetName;
    private Integer quantity;
    private Integer minQuantity;
}
