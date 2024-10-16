package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.request.ProductRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product map(ProductRegisterRequest productRegisterRequest);

}
