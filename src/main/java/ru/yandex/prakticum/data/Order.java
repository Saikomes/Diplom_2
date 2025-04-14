package ru.yandex.prakticum.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private String[] ingredients;

    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }



}
