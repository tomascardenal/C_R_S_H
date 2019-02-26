package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Represents a keyboard for user input
 *
 * @author Tomás Cardenal López
 */
public class KeyboardComponent extends DrawableComponent {

    /**
     * Arrays for keyboard
     */
    private static String[][] keyboardKeys = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"Z", "X", "C", "V", "B", "N", "M"}
    };
    /**
     * Arrays for keyboard Buttons
     */
    private static ButtonComponent[][] keyboardButtons;
    /**
     * Button for deleting
     */
    private static ButtonComponent btnBackSpace;
    /**
     * Rectangle for this keyboard's area calculations
     */
    private Rect areaRect;
    /**
     * Rectangle for this keyboard's userInput area
     */
    private Rect textRect;
    /**
     * Painter for textRect
     */
    private Paint textRectBorderPaint;
    /**
     * Input from user
     */
    private String userInput;
    /**
     * Maximum char count;
     */
    private static final int MAX_CHAR = 25;

    /**
     * Represents a keyboard on the screen
     *
     * @param context       The context for this keyboard
     * @param areaRect      The area containing this keyboard (space will be left on the upper side
     * @param bottomPadding The padding from the bottom
     */
    public KeyboardComponent(Context context, Rect areaRect, int bottomPadding) {
        this.context = context;
        this.areaRect = areaRect;
        this.userInput = "";

        //KEYBOARD BUILDING
        keyboardButtons = new ButtonComponent[3][];

        int keyboardReference = (areaRect.width()) / keyboardKeys[0].length;//(innerWidth/10)
        int startXLeft = areaRect.left;
        int startYTop = areaRect.centerY() - bottomPadding;

        for (int i = 0; i < keyboardKeys.length; i++) {
            startXLeft += keyboardReference * i / 2;
            if (i == 2) {
                startXLeft -= keyboardReference / 2;
            }
            for (int j = 0; j < keyboardKeys[i].length; j++) {
                if (keyboardButtons[i] == null) {
                    keyboardButtons[i] = new ButtonComponent[keyboardKeys[i].length];
                }
                keyboardButtons[i][j] = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN),
                        keyboardKeys[i][j],
                        startXLeft + keyboardReference * j,
                        startYTop + keyboardReference * i,
                        startXLeft + keyboardReference * (j + 1),
                        startYTop + keyboardReference * (i + 1),
                        Color.BLACK, 100, true, -1
                );
                keyboardButtons[i][j].drawOnlyBorder(true, Color.BLACK);
            }
        }
        Rect lastKeyRect = keyboardButtons[keyboardButtons.length - 1][keyboardButtons[keyboardButtons.length - 1].length - 1].getBtnRect();
        this.btnBackSpace = new ButtonComponent(context, Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME), context.getString(R.string.btnBackspace),
                lastKeyRect.left + lastKeyRect.width(), lastKeyRect.top, lastKeyRect.right + lastKeyRect.width(), lastKeyRect.bottom,
                Color.BLACK, 100, true, -1);
        this.btnBackSpace.drawOnlyBorder(true, Color.BLACK);

        int left = keyboardButtons[0][0].btnRect.left;
        int top = keyboardButtons[0][0].btnRect.top - keyboardButtons[0][0].btnRect.height();
        int right = keyboardButtons[0][keyboardButtons[0].length - 1].btnRect.right;
        int bottom = keyboardButtons[0][keyboardButtons[0].length - 1].btnRect.bottom - keyboardButtons[0][keyboardButtons[0].length - 1].btnRect.height();
        this.textRect = new Rect(left, top, right, bottom);

        this.textRectBorderPaint = new Paint();
        this.textRectBorderPaint.setStyle(Paint.Style.STROKE);
        this.textRectBorderPaint.setStrokeWidth(this.textRect.height() / 20);
        this.pText = new Paint();
        this.pText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        this.pText.setTextSize(this.textRect.height() / 2.5f);
        this.pText.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Draws the keyboard
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {

        c.drawRect(textRect, textRectBorderPaint);
        if (userInput != null && !userInput.equals("")) {
            c.drawText(userInput, textRect.centerX(), textRect.centerY() + pText.getTextSize() / 2, pText);
        }
        for (ButtonComponent[] row : keyboardButtons) {
            for (ButtonComponent key : row) {
                key.draw(c);
            }
        }
        btnBackSpace.draw(c);
    }

    /**
     * Resets the user input
     */
    public void resetInput() {
        this.userInput = "";
    }

    /**
     * Gets the string input by the user on this keyboard
     *
     * @return the string input by the user
     */
    public String getUserInput() {
        return this.userInput;
    }

    /**
     * Controls the click events on the keyboard
     *
     * @param event the event to control
     */
    public void onClickEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                for (ButtonComponent[] row : keyboardButtons) {
                    for (ButtonComponent key : row) {
                        if (isClick(key, event)) {
                            key.setKeyboardEffect(true);
                            key.pointerId = event.getPointerId(event.getActionIndex());
                        }
                    }
                }
                if (isClick(btnBackSpace, event)) {
                    btnBackSpace.setKeyboardEffect(true);
                    btnBackSpace.pointerId = event.getPointerId(event.getActionIndex());
                }
                break;
            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                for (ButtonComponent[] row : keyboardButtons) {
                    for (ButtonComponent key : row) {
                        if (isClick(key, event)) {
                            if (userInput.length() <= MAX_CHAR) {
                                userInput += key.getText();
                            }
                            key.setKeyboardEffect(false);
                        }
                        for (int i = 0; i < event.getPointerCount(); i++) {
                            if (event.getPointerId(i) == key.pointerId) {
                                key.setKeyboardEffect(false);
                            }
                        }
                    }
                }
                if (isClick(btnBackSpace, event)) {
                    btnBackSpace.setKeyboardEffect(false);
                    if (userInput.length() > 0) {
                        userInput = userInput.substring(0, userInput.length() - 1);
                    }
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == btnBackSpace.pointerId) {
                            btnBackSpace.setKeyboardEffect(false);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE: // Any finger moves
                for (ButtonComponent[] row : keyboardButtons) {
                    for (ButtonComponent key : row) {
                        if (!isClickByAny(key, event)) {
                            key.setKeyboardEffect(false);
                        }
                        if (isClickByAny(key, event)) {
                            key.setKeyboardEffect(true);
                        }
                    }
                }
                if (!isClickByAny(btnBackSpace, event)) {
                    btnBackSpace.setKeyboardEffect(false);
                }
                if (isClickByAny(btnBackSpace, event)) {
                    btnBackSpace.setKeyboardEffect(true);
                }

                break;
        }
    }

    /**
     * Determines if a rectangle on a button is clicked by the ActionIndex of the event
     *
     * @param btn   the button
     * @param event the motion event detected
     * @return true if the button was clicked
     */
    public boolean isClick(ButtonComponent btn, MotionEvent event) {
        return btn.btnRect.contains((int) event.getX(event.getActionIndex()), (int) event.getY(event.getActionIndex()));
    }

    /**
     * Determines if a rectangle on a button is clicked by any of the pointers acting on the event
     *
     * @param btn   the button
     * @param event the event
     * @return true if the button was clicked or touched by any of the pointers
     */
    public boolean isClickByAny(ButtonComponent btn, MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            if (btn.btnRect.contains((int) event.getX(i), (int) event.getY(i))) {
                return true;
            }
        }
        return false;
    }
}
