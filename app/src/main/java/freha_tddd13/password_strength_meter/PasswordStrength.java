package freha_tddd13.password_strength_meter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PasswordStrength extends LinearLayout {

    private EditText textField;
    private ProgressBar progressBar;
    private TextView strengthText; // Prints "Password strength"
    private TextView strengthTextHint; // Prints how strong the password is
    private ColorFilter defaultProgBarColor;
    private CheckBox checkBox;
    Context context;

    private PasswordAlgorithm algorithm;

    // Minimum length of password
    private int minimumPasswordLength = 6;

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

        // Create a horizontal thin progressbar
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        progressBar.setMax(4);
        textField.setHint("Minimum of " + minimumPasswordLength + " chars in length");

        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(HORIZONTAL);
        strengthTextHint = new TextView(context);
        strengthTextHint.setText("TOO SHORT");
        strengthText = new TextView(context);
        strengthText.setText("Password Strength: ");

        textLayout.addView(strengthText);
        textLayout.addView(strengthTextHint);

        algorithm = new PasswordAlgorithm();

        // Saves the default color of the progressbar so w
        defaultProgBarColor = progressBar.getProgressDrawable().getColorFilter();

        textField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x = algorithm.getStrength(s.toString());
                setStrength(x);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        progressBar.setLayoutParams(params);
        textField.setLayoutParams(params);



        /**
         * Checkbox that gives the option to see the password
         */
        checkBox = new CheckBox(context);
        checkBox.setText("Show password");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    textField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    textField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                textField.setSelection(textField.getText().length());
            }
        });


        addView(textField);
        addView(textLayout);
        addView(progressBar);
        addView(checkBox);
    }

    /**
     * Sets minimum length of the password
     * @param minimumLength minimum length of password
     */
    public void setMinimumLength(int minimumLength) {
        this.minimumPasswordLength = minimumLength;
        algorithm.setMinimumPasswordLength(minimumLength);
        textField.setHint("Minimum of " + minimumPasswordLength + " chars in length");
    }


    /**
     * Method that handles all the graphic parts. Changing the color of the progressbar
     * depending on what value is received. Values between 0-4 are handled.
     * Values can be bigger than 4 but will generate the same result as the value 4.
     * 0 prints too short pw depending on the minimum length set for the field
     * 1 prints status of a weak password
     * 2 prints status of a fair password
     * 3 prints status of a good password
     * 4 prints status of a strong password
     * @param strength 0-4 prints messages of strength
     */
    protected void setStrength(int strength) {
        progressBar.setProgress(strength);
        switch (strength) {

            case 0:
                strengthTextHint.setText("TOO SHORT");
                strengthTextHint.setTextColor(Color.GRAY);
                progressBar.getProgressDrawable().setColorFilter(defaultProgBarColor);
                break;
            case 1:
                strengthTextHint.setText("WEAK");
                strengthTextHint.setTextColor(Color.parseColor("#FFA500"));
                progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFA500"), PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                strengthTextHint.setText("FAIR");
                strengthTextHint.setTextColor(Color.parseColor("#CCCC00"));
                progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#CCCC00"), PorterDuff.Mode.SRC_IN);
                break;
            case 3:
                strengthTextHint.setText("GOOD");
                strengthTextHint.setTextColor(Color.parseColor("#3399FF"));
                progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#3399FF"), PorterDuff.Mode.SRC_IN);
                break;
            case 4:
                strengthTextHint.setText("STRONG");
                strengthTextHint.setTextColor(Color.parseColor("#66CC00"));
                progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#66CC00"), PorterDuff.Mode.SRC_IN);
                break;
            case 5:
                strengthTextHint.setText("NOT VALID");
                strengthTextHint.setTextColor(Color.RED);
                progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                break;
        }

    }


    public void setAlgotrithm(PasswordAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isValid() {
        return algorithm.isValid();
    }

    public String getPasswordStatus() {
        return strengthTextHint.getText().toString();
    }
}
