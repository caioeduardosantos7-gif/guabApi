package com.dashapi.demo.adapter.http.product;

import com.dashapi.demo.domain.model.Product;
import com.dashapi.demo.domain.port.in.ProductUseCase.SaveProductCommand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    SaveProductCommand toCommand(ProductController.ProductRequest request);

    ProductController.ProductRequest toRequest(Product product);
}
