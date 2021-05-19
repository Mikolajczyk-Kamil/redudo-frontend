package com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.domain.Rating;
import com.mikolajczyk.frontend.redudo.dto.RatingDto;
import com.mikolajczyk.frontend.redudo.mapper.RatingMapper;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.mikolajczyk.frontend.redudo.source.service.SourceBookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDialogPreparer {

    private final Session session;
    private final SourceBookService sourceBookService;
    private final RatingMapper ratingMapper;

    public Div prepareDialog(Book book) {
        Image image = new Image(book.getCoverUrl(), "Cover");
        image.setClassName("bookImage");

        Paragraph author = new Paragraph(book.getAuthor());
        author.setClassName("bookAuthor");

        Paragraph title = new Paragraph(book.getTitle());
        title.setClassName("bookTitle");

        Paragraph description = new Paragraph("There is no description");
        description.setClassName("bookDescription");
        if (book.getDescription() != null && book.getDescription().length() > 0)
            description.setText(book.getDescription());

        Div bookData = new Div(author, title);
        bookData.setClassName("bookData");
        Div pageTop = new Div(image, bookData);
        pageTop.setClassName("pageTop");

        NumberField numberField = new NumberField();
        numberField.setClassName("valueField");
        numberField.setValue(6d);
        numberField.setHasControls(true);
        numberField.setMin(0);
        numberField.setMax(6);

        TextArea inputComment = new TextArea();
        inputComment.setClassName("inputComment");
        inputComment.setPlaceholder("Write...");

        Div commentsContainer = new Div();
        commentsContainer.setClassName("commentContainer");

        Button buttonAddComment = new Button("Add comment");
        buttonAddComment.setClassName("buttonAddComment");
        buttonAddComment.addClickListener(e -> {
            if (!inputComment.getValue().isEmpty()) {
                String comment = inputComment.getValue();
                int value = numberField.getValue().intValue();
                Rating rating = new Rating(session.getUser(), book, value, comment);
                RatingDto ratingDto = ratingMapper.mapToRatingDto(rating);
                try {
                    Long response = sourceBookService.addRating(book, ratingDto);
                    if (response > 0) {
                        new Notification("Success", 3000, Notification.Position.TOP_CENTER).open();
                        commentsContainer.add(prepareItem(rating));
                    } else
                        new Notification("It's our fault. Try again later...", 3000, Notification.Position.TOP_CENTER).open();
                } catch (UnirestException unirestException) {
                    unirestException.printStackTrace();
                }
            } else
                new Notification("You have not write comment yet!", 3000, Notification.Position.TOP_CENTER).open();
        });

        Div descriptionDiv = new Div(description);
        descriptionDiv.setClassName("bookDescriptionDiv");

        Div pageBottom = new Div(numberField, inputComment, buttonAddComment, commentsContainer);
        pageBottom.setClassName("pageBottom");

        try {
            List<Rating> ratingList = sourceBookService.getRatings(book);
            System.out.println(ratingList.size());
            if (ratingList.size() > 0) {
                commentsContainer.removeAll();
                for (Rating rating : ratingList) {
                    commentsContainer.add(prepareItem(rating));
                }
            }
            else
                commentsContainer.setText("There is not any comment yet. Add as first!");
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        Div box = new Div(pageTop, descriptionDiv, pageBottom);
        box.setClassName("bookBox");
        return box;
    }

    public Div prepareItem(Rating rating) {
        Image image = new Image(rating.getUser().getPictureUrl(), "img");
        image.setClassName("commentImage");
        Paragraph name = new Paragraph(rating.getUser().getName());
        name.setClassName("commentName");
        Paragraph value = new Paragraph("gives " + rating.getValue());
        value.setClassName("commentValue");
        Paragraph comment = new Paragraph(rating.getComment());
        comment.setClassName("commentString");
        Div commentTop = new Div(image, name, value);
        commentTop.setClassName("commentTop");
        Div commentBottom = new Div(comment);
        commentBottom.setClassName("commentBottom");
        Div commentBox = new Div(commentTop, commentBottom);
        commentBox.setClassName("commentBox");
        return commentBox;
    }
}