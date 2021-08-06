package com.yehor.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
   Long id;
   double price;
   String name;
}
