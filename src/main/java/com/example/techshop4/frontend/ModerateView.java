package com.example.techshop4.frontend;


import com.example.techshop4.config.security.CustomPrincipal;
import com.example.techshop4.entity.User;
import com.example.techshop4.entity.repository.ReviewRepository;
import com.example.techshop4.trash.Review;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

@Route("manager")
@PageTitle("Manager | Vaadin Shop")
public class ModerateView extends VerticalLayout {
    private final ReviewRepository reviewRepository;
    private final Authentication authentication;
    private final User role;
    private final Review review;

    public ModerateView(ReviewRepository reviewRepository, User role, Review review) {
        this.reviewRepository=reviewRepository;
        this.role = (User) role;

        this.authentication = SecurityContextHolder.getContext().getAuthentication();

        this.review=(Review) ComponentUtil.getData(UI.getCurrent(),"review");


        if(this.review==null){
            UI.getCurrent().navigate("");
        }else {

            var reviews = reviewRepository.findByProductId(review.getId());
            moderateView(reviews);
        }
    }

    public void moderateAccess(List<User> users) {
        if (role.equals("Manager")) {
            Notification.show("Добро пожаловать на  страницу менеджера");
        }  Notification.show("Доступ на страницу менеджера запрешен");
    }

    private void moderateView(List<Review> reviews) {
        for(Review review:reviews) {
            TextArea textArea=new TextArea(review.getUser().getFIO());
            textArea.setValue(review.getText()!=null?review.getText():"");
            textArea.setReadOnly(true);
            textArea.setSizeFull();
            add(textArea);
        }
        TextArea editableTextArea=new TextArea();
        editableTextArea.setSizeFull();
        var saveModerateButton=new Button("Модерировать отзыв", event->{
            var review=new Review();
            review.setId(UUID.randomUUID());
            review.getProduct();
            review.setUser(((CustomPrincipal)authentication.getPrincipal()).getUser());
            review.setText(editableTextArea.getValue());
            reviewRepository.save(review);

            Notification.show("Отзыв пользователя успешно сохранён");
        });
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);


        add(editableTextArea,saveModerateButton);
    }


}
