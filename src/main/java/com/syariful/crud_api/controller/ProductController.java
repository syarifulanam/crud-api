package com.syariful.crud_api.controller;

import com.syariful.crud_api.dto.ProductDto;
import com.syariful.crud_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Api endpoints for managing products")
public class ProductController {

    private final ProductService productService;

    // Create
    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product and returns the created product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(schema = @Schema(implementation = ProductDto.class
            ))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDto> createdProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createdProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    @Operation(summary = "Get all products", description = "Return a list of all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> product = productService.getAllProducts();
        return ResponseEntity.ok(product);
    }

    // READ BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Parameter(description = "Product ID", required = true, example = "1") Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> updateProduct(@PathVariable @Parameter(description = "Product ID", required = true) Long id, @Valid @RequestBody ProductDto productDto) {
        ProductDto updateProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updateProduct);
    }

    // Delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable @Parameter(description = "Product ID", required = true) Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // CUSTOM ENDPOINTS
    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "returns all products in a specific category")
    @ApiResponse(responseCode = "200", description = "Successfuly retrieved list")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable @Parameter(description = "category name", required = true, example = "Electronics") String category) {
        List<ProductDto> product = productService.getProductsByCategory(category);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/price-range")
    @Operation(summary = "get products by price range", description = "Returns products within a price range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @RequestParam @Parameter(description = "Minimun price", required = true, example = "1000") Double min,
            @RequestParam @Parameter(description = "Maximum price", deprecated = true, example = "10000") Double max) {
        List<ProductDto> product = productService.getProductsByPriceRange(min, max);
        return ResponseEntity.ok(product);
    }
}
