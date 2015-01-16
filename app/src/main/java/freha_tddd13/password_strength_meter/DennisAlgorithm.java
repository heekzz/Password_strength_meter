package freha_tddd13.password_strength_meter;


import java.util.HashSet;
import java.util.Set;

public class DennisAlgorithm extends PasswordAlgorithm {

    Set<String> invalidPWs = new HashSet<>();

    public DennisAlgorithm(){

        invalidPWs.add("12345");
        invalidPWs.add("qwerty");
        invalidPWs.add("abcde");
        invalidPWs.add("abc123");
        invalidPWs.add("banana");
        invalidPWs.add("hallonsorbet");
    }

    @Override
    public void setMinimumPasswordLength(int minimumPasswordLength) {
        super.setMinimumPasswordLength(minimumPasswordLength);
    }

    @Override
    public int getStrength(String password) {

        if (invalidPWs.contains(password)){
            return 5;
        }
        else return super.getStrength(password);
    }

}
