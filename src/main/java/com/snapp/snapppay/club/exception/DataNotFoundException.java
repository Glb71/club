package com.snapp.snapppay.club.exception;

import com.snapp.snapppay.club.domain.entity.EntityStructure;
import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    private final String type;
    private final Long id;

    public DataNotFoundException(Class<? extends EntityStructure> entityClass, Long id) {
        this.type = entityClass.getSimpleName();
        this.id = id;
    }
}
