package com.sushkomihail.ui;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskedTextField extends JTextField {
    public MaskedTextField(int maxLength, String defaultText, String textPattern) {
        setDocument(new FixedLengthDocument(maxLength));
        setText(defaultText);
        addActionListener(e -> {
            if (isValueCorrect(getText(), textPattern)) {
                System.out.println("correct");
            }
            else {
                System.out.println("incorrect");
            }
        });
    }

    private boolean isValueCorrect(String value, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(value);
        return matcher.find();
    }
}
