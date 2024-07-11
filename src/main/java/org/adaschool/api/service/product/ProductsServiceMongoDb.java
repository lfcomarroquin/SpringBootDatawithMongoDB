package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceMongoDb implements ProductsService {

    private final ProductMongoRepository productMongoRepository;

    @Autowired
    public ProductsServiceMongoDb(ProductMongoRepository productMongoRepository) {
        this.productMongoRepository = productMongoRepository;
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null || product.getName() == null) {
            System.out.println("Por favor, ingrese id y nombre del producto validos");
        }
        Product entitySaved = this.productMongoRepository.save(product);
        return entitySaved;
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<Product> productOptional = this.productMongoRepository.findById(id);
        if (productOptional.isEmpty()) {
            System.out.println("No se encontro ningun producto con el ID: " + id);
        }
        return productOptional;
    }

    @Override
    public List<Product> all() {
        return this.productMongoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Optional<Product> productOptional = this.productMongoRepository.findById(id);
        if (productOptional.isEmpty()) {
            System.out.println("No se encontro ningun producto con el ID: " + id);
        }
        productMongoRepository.deleteById(id);
        System.out.println("Producto con ID: " + id + " eliminado correctamente");
    }

    @Override
    public Product update(Product product, String productId) {
        Optional<Product> productOptional = this.productMongoRepository.findById(productId);
        if (productOptional.isEmpty()) {
            System.out.println("No se encontro ningun producto con el ID: " + productId);
            return null;
        }
        Product updatedProduct = productOptional.get();
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setTags(product.getTags());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setImageUrl(product.getImageUrl());
        return this.productMongoRepository.save(updatedProduct);
    }
}