package com.sk.GatePass.view;


import com.sk.GatePass.controller.EmployeeController;
import com.sk.GatePass.dto.EmployeeDto;
import com.sk.GatePass.model.Employee;
import com.sk.GatePass.model.Role;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;




@PageTitle("Create Account")
@Route(value = "create-account")
@Uses(Icon.class)
public class CreateAccountView extends Div {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField mail = new EmailField("Email address");
    private PasswordField password = new PasswordField("password");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");

    private Button cancel = new Button("Cancel");
    private Button create = new Button("Create");

    private Binder<Employee> binder = new Binder<>(Employee.class);

    public CreateAccountView(EmployeeController employeeController) {
        addClassName("create-account-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        create.addClickListener(e -> {
            System.out.println(binder.getBean());
            System.out.println(getBuild());
            employeeController.addEmployee(getBuild());

            Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
            clearForm();
        });
    }

    private EmployeeDto getBuild() {
        return EmployeeDto.builder()
                .firstName(binder.getBean().getFirstName())
                .lastName(binder.getBean().getLastName())
                .mail(binder.getBean().getMail())
                .password(binder.getBean().getPassword())
                .phone(binder.getBean().getPhone())
                .company(2L)
                .role(binder.getBean().getRole() != null ? binder.getBean().getRole() : Role.USER)
                .build();
    }

    private void clearForm() {
        binder.setBean(new Employee());
    }

    private Component createTitle() {
        return new H3("Create account");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        mail.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, lastName, password, phone, mail);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(create);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setAllowedCharPattern("[\\+\\d]");
            countryCode.setItems("+48", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setAllowedCharPattern("\\d");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}