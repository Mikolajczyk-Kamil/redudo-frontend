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
        List<Book> result = new ArrayList<>();
        try {
            result = sourceBookService.getBooksByQ(searchField.getValue(), false);
        } catch (UnirestException unirestException) {
            unirestException.printStackTrace();
        }
        pageMain.removeAll();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setClassName("itemsLayout");
        verticalLayout = itemsPreparer.prepareItems(result, ContextMenuType.MAIN);

        pageMain.add(verticalLayout);
        session.setSearchHistory(verticalLayout);
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

    public Div prepareDuringPage() {
        Div pageDuring = new Div();
        pageDuring.setClassName("page");
        pageDuring.setVisible(false);
        return pageDuring;
    }

    public Div prepareDonePage() {
        Div pageDone = new Div();
        pageDone.setClassName("page");
        pageDone.setVisible(false);
        return pageDone;
    }

    public void loadThePage(Div thePage, ListType listType, ContextMenuType contextMenuType, String message) {
        List<Book> bookList = sourceAccountService.getBooksFromList(listType);
        thePage.removeAll();
        if (bookList != null && bookList.size() > 0) {
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setClassName("itemsLayout");
            verticalLayout = itemsPreparer.prepareItems(bookList, contextMenuType);
            thePage.add(verticalLayout);
        } else {
            Div infoDiv = new Div();
            infoDiv.setClassName("infoDiv");
            infoDiv.add(new Paragraph(message));
            thePage.add(infoDiv);
        }
    }
}
