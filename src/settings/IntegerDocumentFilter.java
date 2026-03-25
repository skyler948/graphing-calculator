package settings;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerDocumentFilter extends DocumentFilter {

    protected static char[] ALLOWED_CHARACTERS = {'-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) {
        StringBuilder buffer = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            for (Character allowed : ALLOWED_CHARACTERS) {
                if (ch == allowed) {
                    buffer.append(ch);
                    break;
                }
            }
        }

        try {
            super.insertString(fb, offset, buffer.toString(), attr);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) {
        try {
            if (length > 0) {
                fb.remove(offset, length);
            }
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        insertString(fb, offset, string, attr);
    }

}
