package freha_tddd13.password_strength_meter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordStrength extends LinearLayout {

    private EditText textField;
    private ProgressBar progressBar;
    private TextView strengthText; // Prints "Password strength"
    private TextView strengthTextHint; // Prints how strong the password is
    private ColorFilter defaultProgBarColor;
    private Button button;
    private CheckBox checkBox;
    private boolean isValid = false;
    Context context;

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

        // Saves the default color of the progressbar so w
        defaultProgBarColor = progressBar.getProgressDrawable().getColorFilter();

        textField.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x = getStrength(s.toString());
                setStrength(x);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        progressBar.setLayoutParams(params);
        textField.setLayoutParams(params);

        /**
         * Button to 'Enter Password'
         * When pressed it clears the textField
         */
        button = new Button(context);
        button.setText("Enter password");
        button.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            // Let's the user know what error was made
                if (isValid) {
                    Toast.makeText(context, "All done!", Toast.LENGTH_SHORT).show();
                    textField.setText("");
                } else {
                    Toast.makeText(context, "Password is " + strengthTextHint.getText().toString().toLowerCase() +
                            ", try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        addView(button);
    }

    /**
     * Sets minimum length of the password
     * @param minimumLength minimum length of password
     */
    public void setMinimumLength(int minimumLength) {
        this.minimumPasswordLength = minimumLength;
        textField.setHint("Minimum of " + minimumPasswordLength + " chars in length");
    }


    /**
     * Returns a integer value representing the strength of the password.
     * The value can go from 0 to 4 or bigger but the component only changes on the values 0-4.
     * Values higher than 4 gives the same result as 4.
     * @param password Password you want to check strength of
     * @return integer values from 0 and bigger, 0 = too short password,
     * 1 = weak password, 2 = fair password, 3 = good password, 4 = strong password.
     */
    protected int getStrength(String password) {
        int strength = 0;
        Pattern pattern1 = Pattern.compile("([A-Z])"); // Contains a uppercase letter
        Pattern pattern2 = Pattern.compile("([!#€%&/()=?)])"); // Contains a special character
        Pattern pattern3 = Pattern.compile("\\s"); // Contains blank space
        Matcher matcher1 = pattern1.matcher(password);
        Matcher matcher2 = pattern2.matcher(password);
        Matcher matcher3 = pattern3.matcher(password);

        /* Must always be at least longer than the minimum length
           For every criteria the password fulfills we increase the strength
            If the password contains a blank space, it is invalid*/
        if(matcher3.find()){
            isValid = false;
            return 5;
        }
        if(password.length() < minimumPasswordLength){
            isValid = false;
            strength = 0;
        }
        else if (password.length() >= minimumPasswordLength) {
            isValid = true;
            strength++;
            if (password.length() >= 12) {
                strength++;
            }
            if (matcher1.find()) {
                strength++;
            }
            if (matcher2.find()) {
                strength++;
            }
        }
        return strength;

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



}
