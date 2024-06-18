package com.sushkomihail.ui;

import javax.swing.*;

public class MaskedTextField extends JTextField {
    public MaskedTextField(int maxLength, String defaultText, String mask) {
        setDocument(new MaskedDocument(maxLength, mask));
        setText(defaultText);
    }
}
