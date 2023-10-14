package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.Business;
import com.silovale.silovale_api.domain.Product;
import com.silovale.silovale_api.model.ProductDTO;
import com.silovale.silovale_api.repos.BusinessRepository;
import com.silovale.silovale_api.repos.ProductRepository;
import com.silovale.silovale_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;

    public ProductService(final ProductRepository productRepository,
            final BusinessRepository businessRepository) {
        this.productRepository = productRepository;
        this.businessRepository = businessRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(final Long id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory());
        productDTO.setImage(product.getImage());
        productDTO.setBusinessId(product.getBusinessId() == null ? null : product.getBusinessId().getId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImage(productDTO.getImage());
        final Business businessId = productDTO.getBusinessId() == null ? null : businessRepository.findById(productDTO.getBusinessId())
                .orElseThrow(() -> new NotFoundException("businessId not found"));
        product.setBusinessId(businessId);
        return product;
    }

}
