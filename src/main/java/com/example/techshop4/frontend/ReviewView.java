package com.example.techshop4.frontend;

import com.example.techshop4.config.security.CustomPrincipal;
import com.example.techshop4.entity.Product;
import com.example.techshop4.entity.User;
import com.example.techshop4.entity.repository.ReviewRepository;
import com.example.techshop4.trash.Review;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

@Route("review")
public class ReviewView extends VerticalLayout {
    private final ReviewRepository reviewRepository;
    private final Authentication authentication;

    private final Product product;

    public ReviewView(ReviewRepository reviewRepository) {
        this.reviewRepository=reviewRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();

        this. product=(Product)ComponentUtil.getData(UI.getCurrent(),"product");
        if(this.product==null){
            UI.getCurrent().navigate("");
        }else {

            var reviews = reviewRepository.findByProductId(product.getId());
            initView(reviews);
        }
    }

    private void initView(List<Review> reviews) {
        for(Review review:reviews) {
            TextArea  textArea=new TextArea(review.getUser().getFIO());
            textArea.setValue(review.getText()!=null?review.getText():"");
           textArea.setReadOnly(true);
            textArea.setSizeFull();
            add(textArea);
        }
        TextArea editableTextArea=new TextArea();
        editableTextArea.setSizeFull();
        var saveReviewButton=new Button("Сохранить отзыв",event->{
            var review=new Review();
            review.setId(UUID.randomUUID());
            review.getProduct();
            review.setUser(((CustomPrincipal)authentication.getPrincipal()).getUser());
            review.setText(editableTextArea.getValue());
            reviewRepository.save(review);

            Notification.show("Ваш отзыв успешно сохранён");
        });
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);


        add(editableTextArea,saveReviewButton);
    }
}
