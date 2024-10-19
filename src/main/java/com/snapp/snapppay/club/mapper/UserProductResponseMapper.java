package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.Product;
import com.snapp.snapppay.club.domain.response.UserProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProductResponseMapper {

    @Mapping(source = "scorePrice",target = "scoreAmount")
    UserProductResponse map(Product product);

}
