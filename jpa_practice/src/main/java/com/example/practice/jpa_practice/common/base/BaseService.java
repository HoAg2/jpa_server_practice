package com.example.practice.jpa_practice.common.base;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class BaseService extends CustomModelMapper {
}
