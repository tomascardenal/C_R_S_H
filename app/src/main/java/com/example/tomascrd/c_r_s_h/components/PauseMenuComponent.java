package com.example.tomascrd.c_r_s_h.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.core.GameConstants;

/**
 * Represents the pause menu
 *
 * @author Tomás Cardenal López
 */
public class PauseMenuComponent extends DrawableComponent {
    /**
     * Button for options
     */
    private ButtonComponent btnOptions;
    /**
     * Button for unpausing
     */
    private ButtonComponent btnUnpause;
    /**
     * Button for going back to the newGameMenu
     */
    private ButtonComponent btnEndGame;
    /**
     * Button for confirming an action
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Button for unconfirming an action
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Button for playing again
     */
    private ButtonComponent btnPlayAgainYes;
    /**
     * Button for going back to home
     */
    private ButtonComponent btnPlayAgainNo;
    /**
     * Button for confirming on records keyboard
     */
    private ButtonComponent btnKeyConfirmYes;
    /**
     * The current state of the game to be saved if necessary
     */
    private SceneCrsh gameSceneState;
    /**
     * Rectangle for this border
     */
    private Rect borderRect;
    /**
     * Painter for background
     */
    private Paint backgroundPaint;
    /**
     * Painter for border
     */
    private Paint borderPaint;
    /**
     * Painter for info
     */
    private Paint infoPaint;
    /**
     * Indicates whether the user is being shown the confirm menu
     */
    private boolean isConfirming;
    /**
     * Indicates whether the user is on an endScreen
     */
    private boolean onEndScreen;
    /**
     * Indicates whether the user is on a keyboard prompt
     */
    private boolean onKeyboard;
    /**
     * Keyboard for records input
     */
    public KeyboardComponent keyboard;
    /**
     * The id of the winner
     */
    public int winnerID;
    /**
     * The string for the records prompt
     */
    private String recordString;

    /**
     * Initializes a new PauseMenu
     *
     * @param context        the current application context
     * @param xRight         the xRight coordinate
     * @param yTop           the yTop coordinate
     * @param width          the pauseMenu's width
     * @param height         the pauseMenu's height
     * @param gameSceneState the current GameScene state
     */
    public PauseMenuComponent(Context context, float xRight, float yTop, float width, float height, SceneCrsh gameSceneState) {
        //Initializing variables
        this.context = context;
        this.gameSceneState = gameSceneState;
        this.xPos = xRight;
        this.yPos = yTop;
        this.width = width;
        this.height = height;
        this.onEndScreen = false;
        this.onKeyboard = false;
        this.isConfirming = false;

        //Border rectangle
        borderRect = new Rect(
                (int) xPos + gameSceneState.tileSizeReference / 2,
                (int) yPos + gameSceneState.tileSizeReference / 2,
                (int) (xPos + width) - gameSceneState.tileSizeReference / 2,
                (int) (yPos + height) - gameSceneState.tileSizeReference / 2);

        //Background paint
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setShader(new LinearGradient(xPos + (borderRect.height() / 2), yPos + (borderRect.height() / 2), width - (borderRect.height() / 2), height - (borderRect.height() / 2), Color.GRAY, Color.DKGRAY, Shader.TileMode.CLAMP));

        //Border paint
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(gameSceneState.tileSizeReference);

        //Text painter
        pText = new Paint();
        pText.setTypeface(Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_HOMESPUN));
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
        pText.setTextSize(gameSceneState.tileSizeReference * 1.5f);

        infoPaint = new Paint();
        infoPaint.setTypeface(Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_HOMESPUN));
        infoPaint.setColor(Color.BLACK);
        infoPaint.setTextAlign(Paint.Align.CENTER);
        infoPaint.setTextSize(gameSceneState.tileSizeReference * 0.75f);

        //Buttons
        Typeface fontawesome = Typeface.createFromAsset(this.context.getAssets(), GameConstants.FONT_AWESOME);
        this.btnUnpause = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnUnpause),
                (int) (borderRect.exactCenterX() - gameSceneState.tileSizeReference * 2), (int) borderRect.exactCenterY(),
                (int) (borderRect.exactCenterX() + gameSceneState.tileSizeReference * 2), (int) (borderRect.exactCenterY() + gameSceneState.tileSizeReference * 2),
                Color.TRANSPARENT, 0,
                false, -1);

        this.btnOptions = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnOptionsOnPause),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(), btnUnpause.btnRect.top, btnUnpause.btnRect.left, btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);


        this.btnEndGame = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnEndGame),
                btnUnpause.btnRect.right, btnUnpause.btnRect.bottom - btnUnpause.btnRect.height(), btnUnpause.btnRect.right + btnUnpause.btnRect.width(), btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmYes = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnConfirmYes),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(), btnUnpause.btnRect.top, btnUnpause.btnRect.left, btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmNo = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnConfirmNo),
                btnUnpause.btnRect.right, btnUnpause.btnRect.bottom - btnUnpause.btnRect.height(), btnUnpause.btnRect.right + btnUnpause.btnRect.width(), btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

        this.btnPlayAgainYes = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnPlayAgain),
                btnUnpause.btnRect.left - btnUnpause.btnRect.width(), btnUnpause.btnRect.top, btnUnpause.btnRect.left, btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

        this.btnPlayAgainNo = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnNotPlayAgain),
                btnUnpause.btnRect.right, btnUnpause.btnRect.bottom - btnUnpause.btnRect.height(), btnUnpause.btnRect.right + btnUnpause.btnRect.width(), btnUnpause.btnRect.bottom, Color.TRANSPARENT, 0,
                false, -1);

        //KEYBOARD BUILDING
        int offset = (int) this.borderPaint.getStrokeWidth() / 4;
        Rect r = new Rect(this.borderRect.left + offset, this.borderRect.top + offset,
                this.borderRect.right - offset, this.borderRect.bottom - offset);
        keyboard = new KeyboardComponent(context, r, (int) this.borderPaint.getStrokeWidth() / 4);

        this.btnKeyConfirmYes = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmNameYes),
                r.left,
                r.centerY() - btnConfirmYes.btnRect.height() * 2 - (int) this.borderPaint.getStrokeWidth() / 4,
                r.left + btnConfirmYes.btnRect.width(),
                r.centerY() - btnConfirmYes.btnRect.height() - (int) this.borderPaint.getStrokeWidth() / 4,
                Color.TRANSPARENT, 0, false, -1);
    }

    private void resetShader() {
        if (!onEndScreen || winnerID == -1 || winnerID == 0) {
            this.backgroundPaint.setShader(
                    new LinearGradient(
                            xPos + (borderRect.height() / 2),
                            yPos + (borderRect.height() / 2),
                            width - (borderRect.height() / 2),
                            height - (borderRect.height() / 2),
                            Color.GRAY, Color.DKGRAY, Shader.TileMode.CLAMP));
        } else if (winnerID == 1 && onEndScreen) {
            this.backgroundPaint.setShader(
                    new LinearGradient(
                            xPos + (borderRect.height() / 2),
                            yPos + (borderRect.height() / 2),
                            width - (borderRect.height() / 2),
                            height - (borderRect.height() / 2),
                            Color.CYAN, Color.DKGRAY, Shader.TileMode.CLAMP));
        } else if (winnerID == 2 && onEndScreen) {
            this.backgroundPaint.setShader(
                    new LinearGradient(
                            xPos + (borderRect.height() / 2),
                            yPos + (borderRect.height() / 2),
                            width - (borderRect.height() / 2),
                            height - (borderRect.height() / 2),
                            Color.MAGENTA, Color.DKGRAY, Shader.TileMode.CLAMP));
        }
    }

    /**
     * Draws the pause menu on the canvas
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //Draw background and border
        resetShader();
        c.drawRect(borderRect, backgroundPaint);
        c.drawRect(borderRect, borderPaint);

        //Draw title pause text
        String pauseText;
        if (isConfirming) {
            pauseText = context.getString(R.string.confirmQuit);
        } else if (onEndScreen) {
            if (onKeyboard) {
                pauseText = context.getString(R.string.recordsName);
            } else {
                pauseText = context.getString(R.string.titleEndGame);
            }
        } else {
            pauseText = context.getString(R.string.pauseTitle);
        }
        if (!onKeyboard) {
            c.drawText(pauseText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 6, pText);
        } else {
            c.drawText(pauseText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 4, pText);
        }

        //Button
        if (isConfirming()) {
            btnConfirmYes.draw(c);
            btnConfirmNo.draw(c);
        } else if (onEndScreen) {
            if (onKeyboard) {
                c.drawText(recordString, gameSceneState.screenWidth / 2, btnKeyConfirmYes.btnRect.top + infoPaint.getTextSize(), infoPaint);
                btnKeyConfirmYes.draw(c);
                keyboard.draw(c);
            } else {
                String winnerText;
                if (winnerID > 0 && winnerID < 3) {
                    winnerText = String.format(context.getString(R.string.endGameWinner), winnerID);
                } else {
                    winnerText = context.getString(R.string.endGameLoseToCOM);
                }
                c.drawText(winnerText, borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 9, infoPaint);
                btnPlayAgainYes.draw(c);
                btnPlayAgainNo.draw(c);
            }
        } else {
            c.drawText(context.getString(R.string.infoPause), borderRect.exactCenterX(), this.borderRect.height() / GameConstants.GAMESCREEN_ROWS * 9, infoPaint);
            btnOptions.draw(c);
            btnUnpause.draw(c);
            btnEndGame.draw(c);
        }

    }

    /**
     * Returns btnOptions, for click events
     *
     * @return the options button
     */
    public ButtonComponent getBtnOptions() {
        return btnOptions;
    }

    /**
     * Returns btnUnpause, for click events
     *
     * @return the unpause button
     */
    public ButtonComponent getBtnUnpause() {
        return btnUnpause;
    }

    /**
     * Returns btnEndGame, for click events
     *
     * @return the end game button
     */
    public ButtonComponent getBtnEndGame() {
        return btnEndGame;
    }

    /**
     * Returns btnConfirmYes, for click events
     *
     * @return the confirmYes button
     */
    public ButtonComponent getBtnConfirmYes() {
        return btnConfirmYes;
    }

    /**
     * Returns btnConfirmNo, for click events
     *
     * @return the confirmNo button
     */
    public ButtonComponent getBtnConfirmNo() {
        return btnConfirmNo;
    }

    /**
     * Returns btnPlayAgainYes, for click events
     *
     * @return the confirmNo button
     */
    public ButtonComponent getBtnPlayAgainYes() {
        return btnPlayAgainYes;
    }

    /**
     * Returns btnPlayAgainNo, for click events
     *
     * @return the confirmNo button
     */
    public ButtonComponent getBtnPlayAgainNo() {
        return btnPlayAgainNo;
    }

    /**
     * Returns btnKeyConfirmYes, for click events
     *
     * @return the keyConfirmYes button
     */
    public ButtonComponent getBtnKeyConfirmYes() {
        return btnKeyConfirmYes;
    }

    /**
     * Indicates whether the user is being shown the confirm menu
     *
     * @return the current value
     */
    public boolean isConfirming() {
        return isConfirming;
    }

    /**
     * Sets the value that controls if the user is being shown the confirm menu
     *
     * @param confirming the new value of isConfirming
     */
    public void setConfirming(boolean confirming) {
        isConfirming = confirming;
    }

    /**
     * Gets the value indicating if the menu is on an endscreen
     *
     * @return the current value
     */
    public boolean isOnEndScreen() {
        return onEndScreen;
    }

    /**
     * Sets this pause menu to be on the end screen
     *
     * @param onEndScreen the new value
     * @param winnerID    the winner's ID, set to -1 when endScreen is false
     */
    public void setOnEndScreen(boolean onEndScreen, int winnerID) {
        this.onEndScreen = onEndScreen;
        this.winnerID = winnerID;
    }

    /**
     * Gets the value indicating if the menu is on a keyboard
     *
     * @return the current value
     */
    public boolean isOnKeyboard() {
        return onKeyboard;
    }

    /**
     * Sets this pause menu to be on a keyboard
     *
     * @param onKeyboard the new value
     */
    public void setOnKeyboard(boolean onKeyboard, int winner, int score) {
        if (onKeyboard) {
            recordString = String.format(context.getString(R.string.titleKeyboardRecords), winner, score);
        }
        this.onKeyboard = onKeyboard;
    }
}
