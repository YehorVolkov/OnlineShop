package com.yehor.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
   Long id;
   String name;
   double price;
   String description;
}
