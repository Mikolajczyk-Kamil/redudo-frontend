package com.mikolajczyk.frontend.redudo.page;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator.BookDataPreparer;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("book")
public class BookPage extends AppLayout implements HasDynamicTitle {

    private Book book;

    @Autowired
    public BookPage(Session session, BookDataPreparer bookDataPreparer) {
        this.book = session.getBook();
        Button backButton = bookDataPreparer.prepareBackButton();
        Div pageTop = bookDataPreparer.prepareTop(book);
        Div description = bookDataPreparer.prepareDescription(book);
        Div pageBottom = bookDataPreparer.preparePageBottom(book);
        VerticalLayout bookContentBox = new VerticalLayout(backButton, pageTop, description, pageBottom);
        bookContentBox.setClassName("bookContentBox");
        if (session.isDarkMode())
            bookContentBox.getClassNames().add("darkMode");
        setContent(bookContentBox);
    }

    @Override
    public String getPageTitle() {
        return book.getTitle();
    }
}
