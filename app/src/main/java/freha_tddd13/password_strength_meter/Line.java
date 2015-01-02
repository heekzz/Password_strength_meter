package freha_tddd13.password_strength_meter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Line extends View {

    // Different Paints for our coloured lines
    private Paint textPaint;
    private Paint green;
    private Paint yellow;
    private Paint red;
    private Paint grey;

    private float textSize = 25f;
    private int securityLevel;
    private int textStartHeight;

    // Initial messages printet depending on password strength
    private String message_one = "Password too weak!";
    private String message_two = "Fair password, you can do better!";
    private String message_three = "Your password is strong like the Hulk!";

    // Width of the coloured lines
    private int lineWidth;

    // Minimum securityLevel of the password, can be modified
    private int minimumTextLength = 6;
    private final int LINE_MARGIN = 10;

    // Float which controls the hight of the coloured lines
    private float lineHeight = 30f;

    public Line(Context context) {
        super(context);
        launchNuclearWarAgainstUSA(); // Shit is about to go down....
    }

    public Line(Context context, AttributeSet attr) {
        super(context, attr);
        launchNuclearWarAgainstUSA(); // NOT AGAIN!!! :-O
    }

    // Holy Crap!! :O
    private void launchNuclearWarAgainstUSA() {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);

        // Initiate
        green = new  Paint();
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.STROKE);
        green.setStrokeWidth(lineHeight);

        red = new Paint();
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.STROKE);
        red.setStrokeWidth(lineHeight);

        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.STROKE);
        yellow.setStrokeWidth(lineHeight);

        grey = new Paint();
        grey.setColor(Color.LTGRAY);
        grey.setStyle(Paint.Style.STROKE);
        grey.setStrokeWidth(lineHeight);

        textStartHeight = (int) (lineHeight + 1 + textPaint.getTextSize());

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
        invalidate();
    }
    public void setLineHeight(float height) {
        lineHeight = height;
    }
    public void setMinimumTextLength(int minimumTextLength) {
        this.minimumTextLength = minimumTextLength;
    }
    public int getMinimumTextLength() {
        return minimumTextLength;
    }

    public void setMessageLevelOne(String message) {
        this.message_one = message;
    }
    public void setMessageLevelTwo(String message) {
        this.message_two = message;
    }
    public void setMessageLevelThree(String message) {
        this.message_three = message;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        lineWidth = width / 3 - ((2 * LINE_MARGIN) / 3);

        // Writes different colors depending on the level of
        // security the password contains
        switch (securityLevel) {
            case 1:
                paintRed(canvas, width, height);
                break;
            case 2:
                paintYellow(canvas, width, height);
                break;
            case 3:
                paintGreen(canvas, width, height);
                break;
            default:
                paintGrey(canvas, width, height);
                break;
        }
    }

    /*
     * Paints 3 grey lines. This indicates the password length is too short
     * depending on the chosen minimum length.
     */
    private void paintGrey(Canvas canvas, int width, int height) {
        int secondStart = lineWidth + LINE_MARGIN;
        canvas.drawLine(0, 0, lineWidth, 0, grey);
        canvas.drawLine(secondStart, 0, secondStart + lineWidth,0, grey);
        canvas.drawLine(width, 0, width - lineWidth, 0, grey);
        String text = "Too short password, write minimum " + minimumTextLength + " characters!";
        float centerText = (width/2) - textPaint.measureText(text)/2;
        canvas.drawText(text, centerText, textStartHeight,textPaint);
    }

    /*
     * Paints 1 red line and 2 grey lines. This indicates the password is too weak to be accepted.
     */
    private void paintRed(Canvas canvas, int width, int height) {
        int secondStart = lineWidth + LINE_MARGIN;
        canvas.drawLine(0, 0, lineWidth, 0, red);
        canvas.drawLine(secondStart, 0, secondStart + lineWidth,0, grey);
        canvas.drawLine(width, 0, width - lineWidth, 0, grey);
        String text = message_one;
        canvas.drawText(text, (width/2) - textPaint.measureText(text)/2, textStartHeight, textPaint);
    }
    /*
     * Paints 2 yellow lines and 1 grey line. This indicates the password
     * is good enough to pass but recommends to be stronger.
     */
    private void paintYellow(Canvas canvas, int width, int height) {
        int secondStart = lineWidth + LINE_MARGIN;
        canvas.drawLine(0, 0, lineWidth, 0, yellow);
        canvas.drawLine(secondStart, 0, secondStart + lineWidth,0, yellow);
        canvas.drawLine(width, 0, width - lineWidth, 0, grey);
        String text = message_two;
        float centerText = (width/2) - textPaint.measureText(text)/2;
        canvas.drawText(text, centerText, textStartHeight,textPaint);
    }

    /*
     * Paints 3 green lines. This indicates the password is strong.
     */
    private void paintGreen(Canvas canvas, int width, int height) {
        int secondStart = lineWidth + LINE_MARGIN;
        canvas.drawLine(0, 0, lineWidth, 0, green);
        canvas.drawLine(secondStart, 0, secondStart + lineWidth,0, green);
        canvas.drawLine(width, 0, width - lineWidth, 0, green);
        String text = message_three;
        float centerText = (width/2) - textPaint.measureText(text)/2;
        canvas.drawText(text, centerText, textStartHeight,textPaint);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredHeight = (int) (lineHeight + 10 + textPaint.getTextSize());
        int desiredWidth = widthSize;

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            // Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            // Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            // Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            // Be whatever you want
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

}
