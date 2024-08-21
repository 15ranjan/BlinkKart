package com.example.Blinkkart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Category extends BaseModel{
    String name;

    @OneToMany(mappedBy = "category")
    List<Product> productList;
}
