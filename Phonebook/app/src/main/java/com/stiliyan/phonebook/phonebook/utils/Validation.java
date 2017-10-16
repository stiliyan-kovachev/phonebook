package com.stiliyan.phonebook.phonebook.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty( target ) && android.util.Patterns.EMAIL_ADDRESS.matcher( target ).matches();
    }

    public static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty( target ) && Patterns.PHONE.matcher( target ).matches();
    }
}
