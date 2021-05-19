package com.mikolajczyk.frontend.redudo.page.mainPageContent;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator.ContextMenuType;
import com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator.ItemsPreparer;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.mikolajczyk.frontend.redudo.source.service.ListType;
import com.mikolajczyk.frontend.redudo.source.service.SourceAccountService;
import com.mikolajczyk.frontend.redudo.source.service.SourceBookService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

            Div itemContainer = new Div();
            itemContainer.setClassName("itemContainer");

            List<Div> rows = itemsPreparer.prepareItems(result, ContextMenuType.MAIN);
            for (Div row : rows)
                itemContainer.add(row);

            pageMain.removeAll();
            pageMain.add(itemContainer);
            session.setSearchHistory(itemContainer);
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
            infoDiv.setText("Search books by field above.");
            infoDiv.setClassName("infoDivMainPage");
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
            List<Div> rows = itemsPreparer.prepareItems(bookList, ContextMenuType.TO_READ);
            for (Div row : rows)
                pageToRead.add(row);
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
            List<Div> rows = itemsPreparer.prepareItems(bookList, ContextMenuType.DURING);
            for (Div row : rows)
                pageDuring.add(row);
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
            List<Div> rows = itemsPreparer.prepareItems(bookList, ContextMenuType.DONE);
            for (Div row : rows)
                pageDone.add(row);
        } else
            pageDone.setText("You have not done any book yet...");
    }
}
