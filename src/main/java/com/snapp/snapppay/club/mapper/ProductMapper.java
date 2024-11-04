package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "active",ignore = true)
    Product map(ProductRegisterRequest productRegisterRequest);

}
