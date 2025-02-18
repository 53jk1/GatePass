package com.sk.GatePass.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {



private  LoginForm login = new LoginForm();

    public LoginView() {
     addClassName("login-view");
     setSizeFull();
     setAlignItems(Alignment.CENTER);
     setJustifyContentMode(JustifyContentMode.CENTER);

     login.setAction("login");

     add(
             new H1("Parking Manager"),
             login
     );

    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent
                .getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            login.setError(true);
        }

    }
}
