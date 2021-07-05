package com.mikolajczyk.frontend.redudo.page;

import com.mikolajczyk.frontend.redudo.page.mainPageContent.PagesContentManager;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.mikolajczyk.frontend.redudo.source.service.SourceAccountService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.lumo.Lumo;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Route("")
@PageTitle("Redudo")
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
@PWA(name = "Redudo book manager", shortName = "Redudo")
@CssImport(value = "./styles/max-800px-styles.css")
@CssImport(value = "./styles/min-801px-styles.css")
@CssImport(value = "./styles/main-styles.css")
@JsModule("./scripts/script.js")
public class MainPage extends AppLayout {

    private final Session session;
    private final SourceAccountService sourceAccountService;
    private final PagesContentManager contentManager;
    private Div pagesInContainer;
    private Div customNavbar;
    private Div contentBox;
    private Div menuDiv;
    private Tabs tabs;
    private Button themeSwitch;
    private ThemeList themeList;
    private Div drawerContainer;

    public MainPage(Session session, SourceAccountService sourceAccountService, PagesContentManager contentManager) {
        this.session = session;
        this.sourceAccountService = sourceAccountService;
        this.contentManager = contentManager;
    }

    @PostConstruct
    public void init() {
        if (!session.isSignedIn()) {
            sourceAccountService.singIn();
        }
        session.setSignedIn(true);
        closeDrawer();
        addToDrawer(prepareMenu());
        VerticalLayout contentLayout = prepareContent();
        prepareTabsToPages(pagesInContainer);
        Div circle1 = new Div();
        circle1.setClassName("circle1");
        Div circle2 = new Div();
        circle2.setClassName("circle2");
        contentBox = new Div();
        contentBox.setClassName("contentBox");
        menuDiv = new Div();
        menuDiv.setClassName("myDrawer");
        drawerContainer = prepareMenu();
        menuDiv.add(drawerContainer);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setClassName("horizontalLayout");
        horizontalLayout.add(menuDiv, contentLayout);
        contentBox.add(horizontalLayout, circle1, circle2);
        setContent(contentBox);

        themeList = UI.getCurrent().getElement().getThemeList();
        if (session.isDarkMode())
            setDarkMode();
    }

    public Div prepareMenu() {
        Image userImage = new Image(session.getUser().getPictureUrl(), "Redudo");
        Header userName = new Header();
        Div menuTop = new Div(userImage, userName);
        menuTop.setClassName("menuTop");
        userName.setText(session.getUser().getName());
        Tab menuMain = new Tab("Main");
        Tab menuToRead = new Tab("I want to read");
        Tab menuDuring = new Tab("I am reading");
        Tab menuDone = new Tab("I have read");
        Anchor anchor = new Anchor("/logout", "Sign out");
        anchor.getElement().setAttribute("router-ignore", true);
        Tab menuSignOut = new Tab(anchor);
        tabs = new Tabs(menuMain, menuToRead, menuDuring, menuDone, menuSignOut);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.setClassName("vaadinDrawer");
        Div drawerContainer = new Div();
        drawerContainer.setClassName("drawer");
        themeSwitch = new Button("Dark mode");
        themeSwitch.setClassName("themeSwitchButton");
        themeSwitch.addClickListener(e -> {
            if (themeList.contains(Lumo.DARK))
                session.setDarkMode(false);
            else
                session.setDarkMode(true);
            setDarkMode();
        });
        Paragraph paragraph = new Paragraph("Redudo™");
        paragraph.setClassName("menuFooterParagraph");
        Div menuFooter = new Div(paragraph, themeSwitch);
        menuFooter.setClassName("menuFooter");
        drawerContainer.add(menuTop, tabs, menuFooter);
        customNavbar = new Div(drawerToggle, prepareSearchField());
        customNavbar.setClassName("customNavbar");
        return drawerContainer;
    }

    private TextField prepareSearchField() {
        TextField searchField = new TextField();
        searchField.setClassName("searchField");
        searchField.setPlaceholder("Search...");
        searchField.setClearButtonVisible(true);
        searchField.addKeyDownListener(Key.ENTER, e -> {
            if (tabs.getSelectedTab().equals(tabs.getComponentAt(0)))
                contentManager.mainSearchField(searchField, (Div) pagesInContainer.getComponentAt(0));
        });
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> {
            if (
                    tabs.getSelectedTab().equals(tabs.getComponentAt(1)) ||
                    tabs.getSelectedTab().equals(tabs.getComponentAt(2)) ||
                    tabs.getSelectedTab().equals(tabs.getComponentAt(3))
            )
                filter(searchField.getValue());
        });
        return searchField;
    }

    private void filter(String filter) {
        getElement().executeJs("search($0)", filter);
    }

    public VerticalLayout prepareContent() {
        Div pageMain = contentManager.prepareMainPage();
        Div pageToRead = contentManager.prepareToReadPage();
        Div pageDuring = contentManager.prepareDuringPage();
        Div pageDone = contentManager.prepareDonePage();
        Div pages = new Div(pageMain, pageToRead, pageDuring, pageDone);
        pages.setClassName("pages");
        pages.setSizeFull();
        pagesInContainer = pages;
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setClassName("contentLayout");
        menuDiv = new Div();
        menuDiv.setClassName("myDrawer");
        menuDiv.add(prepareMenu());
        contentLayout.add(customNavbar, pages);
        return contentLayout;
    }


    public Map<Tab, Div> prepareTabsToPages(Div pages) {
        Map<Tab, Div> tabsToPages = new HashMap<>();
        int menuQuantity = 4;
        for (int i = 0; i < menuQuantity; i++)
            tabsToPages.put((Tab) tabs.getComponentAt(i), (Div) pages.getComponentAt(i));
        tabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(p -> p.setVisible(false));
            tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
            if (tabs.getSelectedTab().equals(tabs.getComponentAt(1)))
                contentManager.loadToReadPage((Div) pages.getComponentAt(1));
            else if (tabs.getSelectedTab().equals(tabs.getComponentAt(2)))
                contentManager.loadDuringPage((Div) pages.getComponentAt(2));
            else if (tabs.getSelectedTab().equals(tabs.getComponentAt(3)))
                contentManager.loadDonePage((Div) pages.getComponentAt(3));
        });
        return tabsToPages;
    }

    public void setDarkMode() {
        if (session.isDarkMode()) {
            themeSwitch.setText("Light mode");
            drawerContainer.addClassName("darkMode");
            pagesInContainer.addClassName("darkMode");
            customNavbar.addClassName("darkMode");
            contentBox.addClassName("darkMode");
            UI.getCurrent().getElement().getStyle().set("background", "linear-gradient(264deg, #286645, #1f2e63)");
            themeList.add(Lumo.DARK);
        } else {
            themeSwitch.setText("Dark mode");
            drawerContainer.removeClassName("darkMode");
            pagesInContainer.removeClassName("darkMode");
            customNavbar.removeClassName("darkMode");
            contentBox.removeClassName("darkMode");
            UI.getCurrent().getElement().getStyle().set("background", "linear-gradient(264deg, #40a36e, #405fcf)");
            themeList.remove(Lumo.DARK);
        }
    }

    public void closeDrawer() {
        setDrawerOpened(false);
        Page page = UI.getCurrent().getPage();
        page.addBrowserWindowResizeListener(e -> setDrawerOpened(false));
    }
}
