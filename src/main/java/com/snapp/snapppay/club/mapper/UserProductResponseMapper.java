package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.response.UserProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProductResponseMapper {

    UserProductResponse map(Product product);

}
