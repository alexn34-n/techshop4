package com.example.techshop4.frontend;

import com.example.techshop4.entity.Order;
import com.example.techshop4.entity.Product;
import com.example.techshop4.entity.repository.OrderRepository;
import com.example.techshop4.service.CartService;
import com.example.techshop4.service.MailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.time.OffsetDateTime;
import java.util.UUID;

@Route("cart")
public class CartView extends VerticalLayout {
    private final Grid<Product> grid = new Grid<>(Product.class);

    private final CartService cartService;
    private final MailService mailService;
    private final OrderRepository orderRepository;

    public CartView(CartService cartService,
                    MailService mailService,
                    OrderRepository orderRepository) {
        this.cartService = cartService;
        this.mailService = mailService;
        this.orderRepository = orderRepository;

        initCartGrid();
    }

    private void initCartGrid() {
        var products = cartService.getProducts();

        grid.setItems(products);
        grid.setColumns("name", "count");
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<Product> dataProvider = DataProvider.ofCollection(products);
        grid.setDataProvider(dataProvider);

        grid.addColumn(new ComponentRenderer<>(item -> {
            var plusButton = new Button("+", i -> {
                cartService.increaseProductCount(item);
                grid.getDataProvider().refreshItem(item);
            });

            var minusButton = new Button("-", i -> {
                cartService.decreaseProductCount(item);
                grid.getDataProvider().refreshItem(item);
            });

            return new HorizontalLayout(plusButton, minusButton);
        }));

        var button = new Button("Создать заказ", buttonClickEvent -> {
            var order = new Order();
            order.setId(UUID.randomUUID());
            order.setCreatedAt(OffsetDateTime.now());
            orderRepository.save(order);

            //mailService.sendMessage("alexn8996@mail.ru", "Ваш заказ успешно создан. Прибудет через несколько дней.</b>");
        });

        add(grid, button);
    }
}

