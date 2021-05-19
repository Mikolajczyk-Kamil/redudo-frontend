package com.mikolajczyk.frontend.redudo.page.mainPageContent.preparator;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.domain.Rating;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemsPreparer {

    private final ContextMenuPreparer contextMenuPreparer;
    private final BookDialogPreparer bookDialogPreparer;

    public List<Div> prepareItems(List<Book> bookList, ContextMenuType contextMenuType) {
        List<Div> itemsLists = new ArrayList<>();
        List<Div> twoItemsContainersList = new ArrayList<>();
        List<Div> resultList = new ArrayList<>();

        for (Book book : bookList)
            itemsLists.add(prepareItem(book, contextMenuType));

        for (int i=0; i<itemsLists.size(); i++) {
            if (itemsLists.size() % 2 == 1) {
                if (i == 0) {
                    Div twoItemsContainer = new Div();
                    twoItemsContainer.setClassName("singleItemContainer");
                    itemsLists.get(0).getClassNames().remove("item");
                    itemsLists.get(0).getClassNames().add("singleItem");
                    twoItemsContainer.add(itemsLists.get(0));
                    twoItemsContainersList.add(twoItemsContainer);
                } else if (i%2==1){
                    Div twoItemsContainer = new Div();
                    if ((i+1) < itemsLists.size())
                        twoItemsContainer.add(itemsLists.get(i), itemsLists.get(i+1));
                    else
                        twoItemsContainer.add(itemsLists.get(i));
                    twoItemsContainer.setClassName("twoItemsContainer");
                    twoItemsContainersList.add(twoItemsContainer);
                }
            } else if (i%2==0) {
                Div twoItemsContainer = new Div();
                if ((i+1) < itemsLists.size())
                    twoItemsContainer.add(itemsLists.get(i), itemsLists.get(i+1));
                else
                    twoItemsContainer.add(itemsLists.get(i));
                twoItemsContainer.setClassName("twoItemsContainer");
                twoItemsContainersList.add(twoItemsContainer);
            }
        }

        for (int i=0; i<twoItemsContainersList.size(); i++) {
            if (i%3==0){
                Div rowContainer = new Div();
                if ((i+2) < twoItemsContainersList.size())
                    rowContainer.add(twoItemsContainersList.get(i), twoItemsContainersList.get(i+1), twoItemsContainersList.get(i+2));
                else if ((i+1) < twoItemsContainersList.size())
                    rowContainer.add(twoItemsContainersList.get(i), twoItemsContainersList.get(i+1));
                else
                    rowContainer.add(twoItemsContainersList.get(i));
                rowContainer.setClassName("row");
                resultList.add(rowContainer);
            }
        }
        return resultList;
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
        Div item = new Div(image, itemData);
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
            Dialog dialog = new Dialog();
            Div box = bookDialogPreparer.prepareDialog(book);
            Button closeDialog = new Button(new Icon(VaadinIcon.CHEVRON_LEFT));
            closeDialog.addClickListener(c -> dialog.close());
            closeDialog.setClassName("closeDialogButton");
            dialog.add(closeDialog, box);
            dialog.open();
        });
        return item;
    }
}
