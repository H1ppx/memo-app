package com.willchu.memoapp.views.memo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.frontend.installer.DefaultFileDownloader;
import com.willchu.memoapp.data.entity.SamplePerson;
import com.willchu.memoapp.data.service.SamplePersonService;
import com.willchu.memoapp.views.MainLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@PageTitle("Memo")
@Route(value = "memo", layout = MainLayout.class)
@Uses(Icon.class)
public class MemoView extends Div {

    private MFR mfr = new MFR();

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");

    private DatePicker dateOfMemo = new DatePicker("Date of Memorandum");

    private TextField subject = new TextField("Subject");
    private TextArea p1 = new TextArea("Paragraph 1");
    private TextArea p2 = new TextArea("Paragraph 2");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    public MemoView(SamplePersonService personService) {
        addClassName("memo-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> {
            firstName.clear();
            lastName.clear();
            email.clear();
            phone.clear();
            dateOfMemo.clear();
            subject.clear();
            p1.clear();
            p2.clear();
        });

        save.addClickListener(e -> {

            try {
                File latexFile = new File("MFR.tex");
                if (latexFile.createNewFile()) {
                    Notification.show("File created: " + latexFile.getName());
                    FileWriter fileWriter = new FileWriter(latexFile.getName());
                    PrintWriter printWriter = new PrintWriter(fileWriter);
//                    mfr.addHeader(printWriter);
                    printWriter.close();

                } else {
                    Notification.show("File already exists.");
                }
            } catch (IOException ioException) {
                Notification.show("An error occurred.");
                ioException.printStackTrace();
            }

            firstName.clear();
            lastName.clear();
            email.clear();
            phone.clear();
            dateOfMemo.clear();
            subject.clear();
            p1.clear();
            p2.clear();
        });
    }

    private Component createTitle() {
        return new H3("Memorandum Creator");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, lastName, dateOfMemo, phone, email, subject, p1, p2);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
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
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(true);
            countryCode.setItems("+1", "+44", "+86", "+886", "+49");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);
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

    private class MFR{

        public void generateTex(PrintWriter printWriter, String author, String subject){
            printWriter.println("\\RequirePackage[l2tabu, orthodox]{nag}");
            printWriter.println("\\documentclass[12pt,letterpaper]{article}");
            printWriter.println("\\usepackage[pdftex, pdfusetitle,colorlinks=false,pdfborder={0 0 0}]{hyperref}");
            printWriter.println("\\usepackage{graphicx}");
            printWriter.println("\\usepackage[margin=1in]{geometry}");
            printWriter.println("\\usepackage{setspace}");
            printWriter.println("\\usepackage[T1]{fontenc}");
            printWriter.println("\\usepackage{tikz}");
            printWriter.println("\\usepackage{microtype}");
            printWriter.println("\\usepackage{enumitem}");
            printWriter.println("\\usepackage{scalefnt}");
            printWriter.println("\\usepackage[english]{babel}");
            printWriter.println("\\usepackage{blindtext}");
            printWriter.println("\\usepackage[scaled]{helvet}");
            printWriter.println("\\usepackage{times}");
            printWriter.println("\\usepackage{eforms}\n");
            printWriter.println("\\newcommand{\\daletterhead}[1]{\\fontsize{10pt}{10pt}\\selectfont \\textbf{\\textsc{#1}}\\\\}");
            printWriter.println("\\newcommand{\\letterhead}[1]{ \\fontsize{8pt}{5pt}\\selectfont \\textbf{\\textsc{#1}}\\\\}");
            printWriter.println("\\newcommand{\\st}{\\textsuperscript{st}}");
            printWriter.println("\\newcommand{\\nd}{\\textsuperscript{nd}}");
            printWriter.println("\\newcommand{\\rd}{\\textsuperscript{rd}}");
            printWriter.println("\\newcommand{\\thh}{\\textsuperscript{th}}");
            printWriter.println("\\newcommand{\\memotype}[1]{\\noindent\\textsc{#1}}");
            printWriter.println("\\newcommand{\\subject}[1]{\\noindent\\textsc{Subject}: #1}");
            printWriter.println("\\newcommand\\textbox[1]{\\parbox{.5\\textwidth}{#1}}"); // Line 60 on Army Latex File
            printWriter.println("\\usepackage{glossaries}");
            printWriter.println("\\usepackage{relsize}");
            printWriter.println("\\setacronymstyle{long-sm-short}");
            printWriter.println("\\renewcommand{\\theenumi}{\\arabic{enumi}}");
            printWriter.println("\\renewcommand{\\labelenumi}{\\theenumi.}");
            printWriter.println("\\renewcommand{\\theenumii}{\\alph{enumii}}");
            printWriter.println("\\renewcommand{\\labelenumii}{\\theenumii.}");
            printWriter.println("\\renewcommand{\\theenumiii}{\\arabic{enumiii}}");
            printWriter.println("\\renewcommand{\\labelenumiii}{(\\theenumiii)}");
            printWriter.println("\\renewcommand{\\theenumiv}{\\alph{enumiv}}");
            printWriter.println("\\renewcommand{\\labelenumiv}{(\\theenumiv)}");
            printWriter.println("\\usepackage{fancyhdr}");
            printWriter.println("\\renewcommand{\\headrulewidth}{0pt}");
            printWriter.println("\\pagestyle{fancy}");
            printWriter.println("\\fancypagestyle{firststyle} {\\fancyhf{} \\fancyfoot{}}");
            printWriter.println("\\lhead{\\scriptsize\\OfficeSymbol\\\\ \\footnotesize \\textsc{subject}: \\Subject}");
            printWriter.println("\\fancyfoot[C]{\\thepage}"); // Line 87 on Army Latex File
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
        };


    }

}
