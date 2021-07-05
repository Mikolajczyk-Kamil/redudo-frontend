package com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemsPreparer {

    private final ContextMenuPreparer contextMenuPreparer;
    private final Session session;

    public VerticalLayout prepareItems(List<Book> bookList, ContextMenuType contextMenuType) {
        List<Div> itemsLists = new ArrayList<>();
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("itemsLayout");
        for (Book book : bookList)
            itemsLists.add(prepareItem(book, contextMenuType));

        for (Div item : itemsLists)
            layout.add(item);
        return layout;
    }

    private Div prepareItem(Book book, ContextMenuType contextMenuType) {
        Image image = new Image(book.getCoverUrl(), "Cover");
        image.setClassName("itemImage");
        Header title = new Header();
        title.setClassName("itemTitle");
        title.setText(book.getTitle());
        Paragraph author = new Paragraph(book.getAuthor());
        author.setClassName("itemAuthor");
        Div itemData = new Div(author, title);
        itemData.setClassName("itemData");
        Div itemImageBox = new Div(image);
        itemImageBox.setClassName("itemImageBox");
        Div item = new Div(itemImageBox, itemData);
        item.setClassName("item");
        if (contextMenuType.equals(ContextMenuType.MAIN))
            contextMenuPreparer.prepareMainContextMenu(item, book);
        else if (contextMenuType.equals(ContextMenuType.TO_READ))
        contextMenuPreparer.prepareToReadContextMenu(item, book);
        else if (contextMenuType.equals(ContextMenuType.DURING))
            contextMenuPreparer.prepareDuringContextMenu(item, book);
        else if (contextMenuType.equals(ContextMenuType.DONE))
            contextMenuPreparer.prepareDoneContextMenu(item, book);
        item.addClickListener(e -> {
            session.setBook(book);
            item.getUI().ifPresent(ui -> ui.navigate("book"));
        });
        return item;
    }
}
