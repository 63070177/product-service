package com.example.productservice.command;

import com.example.productservice.core.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    // product state
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }

    // command handlers
    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        // validation business logic
        if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price can not be less than or equal to zero");
        }
        if (command.getTitle() == null || command.getTitle().isBlank()){
            throw new IllegalArgumentException("Title can not be empty");
        }
        // ส่งเข้า event bus
        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(command, event);
    }

}
