package com.example.techshop4.frontend;

import com.example.techshop4.entity.Product;
import com.example.techshop4.entity.filter.ProductFilter;
import com.example.techshop4.entity.repository.ProductRepository;
import com.example.techshop4.service.CartService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {
    private final Grid<Product> grid = new Grid<>(Product.class);

    private final ProductRepository productRepository;
    private final CartService cartService;
    private final Authentication authentication;

    public MainView(ProductRepository productRepository,
                    CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();

        initPage();
    }

    private void initPage() {
        initProductGrid();
        initMainPage();
        //На случай когда нужно разделить роли, вырезать лишние кнопки у тех у кого нет прав
        /*if(SecurityUtils.isAdmin(authentication)) {
            add(initAddToCartButton());
        }*/
    }

    private void initMainPage() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(initFirstRow(), grid, initAddToCartButton());
    }

    private Component initFirstRow() {
        var toCartButton = new Button("Корзина", item -> {
            UI.getCurrent().navigate("cart");
        });

        var logoutButton = new Button("Выход", item -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("login");
        });

        return new HorizontalLayout(toCartButton, logoutButton);
    }

    private HorizontalLayout initAddToCartButton() {
        var addToCartButton = new Button("Добавить в корзину", items -> {
            cartService.addProduct(grid.getSelectedItems());
            Notification.show("Товар успешно добавлен в корзину");
        });

        return new HorizontalLayout(addToCartButton);
    }

    private void initProductGrid() {
        var products = productRepository.findAll();

        grid.setItems(products);
        grid.setColumns("name", "price", "count");
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<Product> dataProvider = DataProvider.ofCollection(products);
        grid.setDataProvider(dataProvider);

        grid.addColumn(new ComponentRenderer<>(item -> {
            var plusButton = new Button("+", i -> {
                item.incrementCount();
                productRepository.save(item);
                grid.getDataProvider().refreshItem(item);
            });

            var minusButton = new Button("-", i -> {
                item.decreaseCount();
                productRepository.save(item);
                grid.getDataProvider().refreshItem(item);
            });

            return new HorizontalLayout(plusButton, minusButton);
        }));
    }
}