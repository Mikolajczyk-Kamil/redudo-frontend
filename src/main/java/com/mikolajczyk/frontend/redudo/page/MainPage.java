package com.mikolajczyk.frontend.redudo.page;

import com.mikolajczyk.frontend.redudo.page.mainPageContent.PagesContentManager;
import com.mikolajczyk.frontend.redudo.session.Session;
import com.mikolajczyk.frontend.redudo.source.service.SourceAccountService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Route("")
@PageTitle("Redudo")
@Viewport("width=device-width, initial-scale=1.0")
@Meta(name = "mobile-web-app-capable", content = "yes")
@Meta(name = "apple-mobile-web-app-capable", content = "yes")
@Meta(name = "apple-mobile-web-app-status-bar-style", content = "black-translucent")
@PWA(name = "Redudo", shortName = "Redudo", iconPath = "icons/logo-128x128.png")
@CssImport(value = "./styles/app-layout-styles.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/book-page-styles.css")
@CssImport(value = "./styles/styles.css")
@JsModule("./scripts/script.js")
public class MainPage extends AppLayout {

    private final Session session;
    private final SourceAccountService sourceAccountService;
    private final PagesContentManager contentManager;
    private Div pages;

    public MainPage(Session session, SourceAccountService sourceAccountService, PagesContentManager contentManager) {
        this.session = session;
        this.sourceAccountService = sourceAccountService;
        this.contentManager = contentManager;
        sourceAccountService.singIn();
    }

    @PostConstruct
    public void init() {
        Tabs tabs = prepareMenu();
        pages = prepareContent();
        prepareTabsToPages(tabs, pages);
        setContent(pages);
    }

    public Tabs prepareMenu() {
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
        Tabs tabs = new Tabs(menuMain, menuToRead, menuDuring, menuDone, menuSignOut);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, new DrawerToggle(), prepareSearchField(tabs));
        addToDrawer(menuTop, tabs);
        return tabs;
    }

    private TextField prepareSearchField(Tabs tabs) {
        TextField searchField = new TextField();
        searchField.setClassName("searchField");
        searchField.setPlaceholder("Search books...");
        searchField.setClearButtonVisible(true);
        searchField.addKeyDownListener(Key.ENTER, e -> {
            if (tabs.getSelectedTab().equals(tabs.getComponentAt(0)))
                contentManager.mainSearchField(searchField, (Div) pages.getComponentAt(0));
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

    public Div prepareContent() {
        Div pageMain = contentManager.prepareMainPage();
        Div pageToRead = contentManager.prepareToReadPage();
        Div pageDuring = contentManager.prepareDuringPage();
        Div pageDone = contentManager.prepareDonePage();
        Div pages = new Div(pageMain, pageToRead, pageDuring, pageDone);
        pages.setClassName("pages");
        return pages;
    }


    public Map<Tab, Div> prepareTabsToPages(Tabs tabs, Div pages) {
        Map<Tab, Div> tabsToPages = new HashMap<>();
        int menuQuantity = 4;
        for (int i = 0; i < menuQuantity; i++)
            tabsToPages.put((Tab) tabs.getComponentAt(i), (Div) pages.getComponentAt(i));
        tabs.addSelectedChangeListener(e -> {
            if (isMobileDevice())
                setDrawerOpened(false);
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

    public boolean isMobileDevice() {
        WebBrowser webBrowser = VaadinSession.getCurrent().getBrowser();
        return webBrowser.isAndroid() || webBrowser.isIPhone() || webBrowser.isWindowsPhone();
    }
}
