package com.syariful.crud_api.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product Data Transfer Object")
public class ProductDto {

    @Schema(description = "Product ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100)
    @Schema(description = "Product name", example = "Laptop Asus ROG", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 500)
    @Schema(description = "Product description", example = "Laptop gaming dengan RTX 4060", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "Price is required")
    @Positive
    @Schema(description = "Product price in Rupiah", example = "15000000",requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @Min(0)
    @Schema(description = "Available stock", example = "10", defaultValue = "0")
    private Integer stock;

    @NotBlank(message = "Category is required")
    @Schema(description = "Product category", example = "Electronics", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;
}
