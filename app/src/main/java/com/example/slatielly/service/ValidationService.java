package com.example.slatielly.service;

import android.app.Activity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Pattern;

public class ValidationService {
    private AwesomeValidation awesomeValidation;
    private Activity activity;

    public ValidationService(Activity activity) {
        this.activity = activity;
        this.awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
    }

    public void addValidation(int id, Pattern pattern, int string) {
        this.awesomeValidation.addValidation(this.activity, id, pattern, string);
    }

    public void addValidation(int id, String pattern, int string) {
        this.awesomeValidation.addValidation(this.activity, id, pattern, string);
    }

    public boolean validate() {
        return this.awesomeValidation.validate();
    }
}
