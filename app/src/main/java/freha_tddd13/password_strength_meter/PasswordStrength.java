package freha_tddd13.password_strength_meter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PasswordStrength extends LinearLayout {

    private EditText textField;
    private Line lines;
    Context context;

    public PasswordStrength(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PasswordStrength(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        textField = new EditText(context);
        lines = new Line(context);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        textField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x = getDifficulty(s.toString());
                lines.setSecurityLevel(x);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textField.setLayoutParams(params);
        lines.setLayoutParams(params);
        addView(textField);
        addView(lines);
    }

    public void setLineHeight(float lineHeight) {
        lines.setLineHeight(lineHeight);
    }
    public void setMinimumLength(int length) {
        lines.setMinimumTextLength(length);
    }
    public void setMessageLevel1(String message) {

    }
    public void setMessageLevel2(String message) {

    }
    public void setMessageLevel3(String message) {

    }

    /**
     * Get the difficulty level for the password typed in the text field
     * Override this to change the algorithm for your Password Strength Meter
     * @param password String which is your typed password
     * @return Int between 0-3 depending on difficulty on input
     */
    protected int getDifficulty(String password) {
        int min = lines.getMinimumTextLength();
        if(password.length() < min){
            return 0;
        } else if (password.length() == min) {
            return 1;
        } else if (password.length() > min && password.length() < min + 3) {
            return 2;
        } else if(password.length() >= min + 3){
            return 3;
        } else
            return  3;
    }



}

