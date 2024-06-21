package com.sushkomihail.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskedDocument extends PlainDocument {
    private final int maxLength;
    private final String mask;

    public MaskedDocument(int maxLength, String mask) {
        this.maxLength = maxLength;
        this.mask = mask;
    }

    private boolean isStringCorrect(String str) {
        Pattern compiledPattern = Pattern.compile(mask);
        Matcher matcher = compiledPattern.matcher(str);
        return matcher.find();
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        String newStr = getText(0, getLength()) + str;
        if (str != null && str.length() + getLength() <= maxLength && isStringCorrect(newStr)) {
            super.insertString(offset, str, a);
        }
    }
}
