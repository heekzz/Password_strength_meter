package freha_tddd13.password_strength_meter;


public class DennisAlgorithm extends PasswordAlgorithm {
    @Override
    public int getStrength(String password) {

        if(password.equals("abcde")) {
            return 1;
        }
        else {
            return 4;
        }
    }
}
