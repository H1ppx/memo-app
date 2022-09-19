package com.willchu.memoapp.views.about;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.willchu.memoapp.views.MainLayout;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("About/Info"));
        add(new Paragraph("This project is made to be easily accessible for those who have trouble formatting " +
                "memorandums. They can usually be pretty tricky to manipulate in Word, and not many people have learned" +
                "to use LaTeX therefore this website project is meant to bridge the gap"));
        add(new Paragraph("This is currently still under development and meant primarily for use by those in the Rutgers" +
                " ROTC Program. If you would like to contribute feel free to submit a PR, and if there are any bugs " +
                "please submit them as an issue and I will try to address it. TY"));


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
