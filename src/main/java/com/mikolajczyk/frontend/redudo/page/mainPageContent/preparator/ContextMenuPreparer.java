package com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.mapper.BookMapper;
import com.mikolajczyk.frontend.redudo.source.service.ListType;
import com.mikolajczyk.frontend.redudo.source.service.SourceAccountService;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContextMenuPreparer {

    private final SourceAccountService sourceAccountService;
    private final BookMapper bookMapper;

    public ContextMenu prepareMainContextMenu(Div item, Book book) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(item);
        contextMenu.addItem("I wanna read", e -> sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.TO_READ));
        contextMenu.addItem("I'm reading", e -> sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DURING));
        contextMenu.addItem("I've read", e -> sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DONE));
        return contextMenu;
    }

    public ContextMenu prepareToReadContextMenu(Div item, Book book) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(item);
        contextMenu.addItem("I'm reading", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DURING) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("I've read", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DONE) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("Remove", e -> {
            if (sourceAccountService.removeFromList(book.getGoogleId(), ListType.TO_READ) > 0)
                item.getClassNames().add("itemDeleted");
        });
        return contextMenu;
    }

    public ContextMenu prepareDuringContextMenu(Div item, Book book) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(item);
        contextMenu.addItem("I wanna read", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.TO_READ) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("I've read", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DONE) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("Remove", e -> {
            if (sourceAccountService.removeFromList(book.getGoogleId(), ListType.DURING) > 0)
                item.getClassNames().add("itemDeleted");
        });
        return contextMenu;
    }

    public ContextMenu prepareDoneContextMenu(Div item, Book book) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(item);
        contextMenu.addItem("I wanna read", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.TO_READ) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("I'm reading", e -> {
            if (sourceAccountService.addBookList(bookMapper.mapToBookDto(book), ListType.DURING) > 0)
                item.getClassNames().add("itemDeleted");
        });
        contextMenu.addItem("Remove", e -> {
            if (sourceAccountService.removeFromList(book.getGoogleId(), ListType.DONE) > 0)
                item.getClassNames().add("itemDeleted");
        });
        return contextMenu;
    }
}
