import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FixedLengthDocument extends PlainDocument {
    private final int maxLength;
    public FixedLengthDocument(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        if (str != null && str.length() + getLength() <= maxLength) {
            super.insertString(offset, str, a);
        }
    }
}
