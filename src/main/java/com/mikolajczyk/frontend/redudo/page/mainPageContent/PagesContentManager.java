package com.mikolajczyk.frontend.redudo.page.mainPageContent;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator.ContextMenuType;
import com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator.ItemsPreparer;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.mikolajczyk.frontend.redudo.source.service.ListType;
import com.mikolajczyk.frontend.redudo.source.service.SourceAccountService;
import com.mikolajczyk.frontend.redudo.source.service.SourceBookService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PagesContentManager {

    private final SourceAccountService sourceAccountService;
    private final SourceBookService sourceBookService;
    private final ItemsPreparer itemsPreparer;
    private final Session session;

    public void mainSearchField(TextField searchField, Div pageMain) {
        try {
            List<Book> result = sourceBookService.getBooksByQ(searchField.getValue(), false);
            pageMain.removeAll();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setClassName("itemsLayout");
            verticalLayout = itemsPreparer.prepareItems(result, ContextMenuType.MAIN);
            pageMain.add(verticalLayout);
            session.setSearchHistory(verticalLayout);
        } catch (UnirestException unirestException) {
            unirestException.printStackTrace();
        }
    }

    public Div prepareMainPage() {
        Div pageMain = new Div();
        pageMain.setClassName("page");
        loadMainPage(pageMain);
        return pageMain;
    }

    public void loadMainPage(Div mainPage) {
        mainPage.removeAll();
        if (session.getSearchHistory() != null)
            mainPage.add(session.getSearchHistory());
        else {
            Div infoDiv = new Div();
            infoDiv.setClassName("infoDiv");
            infoDiv.add(new Paragraph("Search books by field above"));
            mainPage.add(infoDiv);
        }
    }

    public Div prepareToReadPage() {
        Div pageToRead = new Div();
        pageToRead.setClassName("page");
        pageToRead.setVisible(false);
        return pageToRead;
    }

    public void loadToReadPage(Div pageToRead) {
        List<Book> bookList = sourceAccountService.getBooksFromList(ListType.TO_READ);
        if (bookList != null && bookList.size() > 0) {
            pageToRead.removeAll();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setClassName("itemsLayout");
            verticalLayout = itemsPreparer.prepareItems(bookList, ContextMenuType.MAIN);
            pageToRead.add(verticalLayout);
        } else
            pageToRead.setText("You have not books you wanna read...");
    }

    public Div prepareDuringPage() {
        Div pageDuring = new Div();
        pageDuring.setClassName("page");
        pageDuring.setVisible(false);
        return pageDuring;
    }

    public void loadDuringPage(Div pageDuring) {
        List<Book> bookList = sourceAccountService.getBooksFromList(ListType.DURING);
        if (bookList != null && bookList.size() > 0) {
            pageDuring.removeAll();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setClassName("itemsLayout");
            verticalLayout = itemsPreparer.prepareItems(bookList, ContextMenuType.MAIN);
            pageDuring.add(verticalLayout);
        } else
            pageDuring.setText("You do not read any book...");
    }

    public Div prepareDonePage() {
        Div pageDone = new Div();
        pageDone.setClassName("page");
        pageDone.setVisible(false);
        return pageDone;
    }

    public void loadDonePage(Div pageDone) {
        List<Book> bookList = sourceAccountService.getBooksFromList(ListType.DONE);
        if (bookList != null && bookList.size() > 0) {
            pageDone.removeAll();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setClassName("itemsLayout");
            verticalLayout = itemsPreparer.prepareItems(bookList, ContextMenuType.MAIN);
            pageDone.add(verticalLayout);
        } else
            pageDone.setText("You have not done any book yet...");
    }
}
