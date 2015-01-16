package freha_tddd13.password_strength_meter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fredrik on 15-01-16.
 */
public class PasswordAlgorithm {

    private boolean isValid;
    private int minimumPasswordLength;


    /**
     * Returns a integer value representing the strength of the password.
     * The value can go from 0 to 4 or bigger but the component only changes on the values 0-4.
     * Values higher than 4 gives the same result as 4.
     * @param password Password you want to check strength of
     * @return integer values from 0 and bigger, 0 = too short password,
     * 1 = weak password, 2 = fair password, 3 = good password, 4 = strong password.
     */
    public int getStrength(String password) {

        isValid = false;
        int strength = 0;
        Pattern pattern1 = Pattern.compile("([A-Z])"); // Contains a uppercase letter
        Pattern pattern2 = Pattern.compile("([!#€%&/()=?)])"); // Contains a special character
        Pattern pattern3 = Pattern.compile("\\s"); // Contains blank space
        Matcher matcher1 = pattern1.matcher(password);
        Matcher matcher2 = pattern2.matcher(password);
        Matcher matcher3 = pattern3.matcher(password);

        /* Must always be at least longer than the minimum length and not contain
           a blank space to be a valid password
           For every criteria the password fulfills we increase the strength
        */
        if(matcher3.find()){
            return 5;
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

    public boolean isValid() {
        return isValid;
    }

    public void setMinimumPasswordLength(int minimumPasswordLength) {
        this.minimumPasswordLength = minimumPasswordLength;
    }


}
