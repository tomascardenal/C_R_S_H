package com.example.tomascrd.c_r_s_h.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tomascrd.c_r_s_h.R;
import com.example.tomascrd.c_r_s_h.components.ButtonComponent;
import com.example.tomascrd.c_r_s_h.components.SceneCrsh;
import com.example.tomascrd.c_r_s_h.core.GameConstants;
import com.example.tomascrd.c_r_s_h.core.GameEngine;
import com.example.tomascrd.c_r_s_h.structs.RecordReference;

import java.util.ArrayList;

/**
 * Represents the records scene
 *
 * @author Tomás Cardenal López
 */
public class RecordsScene extends SceneCrsh {

    /**
     * Constant id for RecordsScene
     */
    public static final int RECORDS_ID = 4;
    /**
     * Maximum number of cycles to stay with text at max alpha
     */
    private static final int MAX_ALPHA_CYCLES = 150;
    /**
     * Alpha incremental and decremental
     */
    private static final int ALPHA_SHIFT = 5;
    /**
     * Dynamic alpha value for the credit strings
     */
    private int textAlpha;
    /**
     * Whether we are on page one or two of the records
     */
    private boolean recordPageOne;
    /**
     * Alpha counter
     */
    private int maxAlphaCounter;
    /**
     * Whether alpha is incrementing or decrementing
     */
    private boolean incrementAlpha;
    /**
     * Whether the next records should be shown
     */
    private boolean nextRecords;
    /**
     * The painter for the records' text
     */
    private Paint pRecordsText;
    /**
     * Button that empties the records
     */
    private ButtonComponent btnEmptyRecords;
    /**
     * Button for confirming
     */
    private ButtonComponent btnConfirmYes;
    /**
     * Button for unconfirming
     */
    private ButtonComponent btnConfirmNo;
    /**
     * Controls whether the scene is showing the records or confirming their deletion
     */
    private boolean onConfirmDelete;
    /**
     * Strings for the records
     */
    private ArrayList<String> recordStrings;

    /**
     * Starts a records screen
     *
     * @param context        the application context
     * @param screenWidth    this screen's width
     * @param screenHeight   this screen's height
     * @param engineCallback callback to access gameEngine data
     */
    public RecordsScene(Context context, int screenWidth, int screenHeight, GameEngine engineCallback) {
        super(context, RECORDS_ID, screenWidth, screenHeight);
        this.engineCallback = engineCallback;
        //Title text
        pTitleText = new Paint();
        pTitleText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pTitleText.setColor(Color.BLACK);
        pTitleText.setTextAlign(Paint.Align.CENTER);
        pTitleText.setTextSize((float) ((screenHeight / GameConstants.MENUSCREEN_COLUMNS) * 2));

        //Alpha effect variables
        this.textAlpha = 0;
        this.recordPageOne = true;
        this.maxAlphaCounter = 0;
        this.incrementAlpha = true;
        this.nextRecords = false;

        generateRecordStrings();


        Typeface fontawesome = Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_AWESOME);
        this.btnEmptyRecords = new ButtonComponent(context,
                fontawesome, context.getString(R.string.btnEmptyRecords),
                0, 0, screenWidth / 16, screenWidth / 16, Color.TRANSPARENT, 0, false, -1);

        this.btnConfirmYes = new ButtonComponent(context, fontawesome, context.getString(R.string.btnConfirmYes),
                (int) (screenWidth / 2 - btnEmptyRecords.width * 4),
                (int) screenHeight / 2,
                (int) (screenWidth / 2 - btnEmptyRecords.width * 3),
                (int) (screenHeight / 2 + btnEmptyRecords.height),
                Color.TRANSPARENT, 0,
                false, -1);

        this.btnConfirmNo = new ButtonComponent(context, fontawesome,
                context.getString(R.string.btnConfirmNo),
                (int) (screenWidth / 2 + btnEmptyRecords.width * 3),
                (int) screenHeight / 2,
                (int) (screenWidth / 2 + btnEmptyRecords.width * 4),
                (int) (screenHeight / 2 + btnEmptyRecords.height),
                Color.TRANSPARENT, 0,
                false, -1);

        //Records text
        pRecordsText = new Paint();
        pRecordsText.setTypeface(Typeface.createFromAsset(context.getAssets(), GameConstants.FONT_HOMESPUN));
        pRecordsText.setColor(Color.BLACK);
        pRecordsText.setTextAlign(Paint.Align.CENTER);
        pRecordsText.setTextSize((float) (screenHeight / GameConstants.MENUSCREEN_COLUMNS));
        pRecordsText.setAlpha(255);
    }

    private void generateRecordStrings() {
        if (recordStrings != null) {
            recordStrings.clear();
            recordStrings = null;
        }
        if (engineCallback.optionsManager.getRecordReferences().size() > 0) {
            recordStrings = new ArrayList<>();
            for (RecordReference ref : engineCallback.optionsManager.getRecordReferences()) {
                recordStrings.add(String.format("#%02d %15s > %04d pts", engineCallback.optionsManager.getRecordReferences().indexOf(ref) + 1, ref.playerName, ref.highScore));
            }
        } else {
            recordStrings = new ArrayList<>();
        }
    }

    /**
     * Updates the physics of the elements on the screen
     */
    @Override
    public void updatePhysics() {
        super.updatePhysics();

        //Credit variables
        if (nextRecords) {
            nextRecords = false;
            recordPageOne = !recordPageOne;
        }
        //Alpha variables
        if (incrementAlpha) {
            if (textAlpha < 255) {
                textAlpha += ALPHA_SHIFT;
            } else {
                if (maxAlphaCounter < MAX_ALPHA_CYCLES) {
                    textAlpha = 255;
                    maxAlphaCounter++;
                } else {
                    maxAlphaCounter = 0;
                    incrementAlpha = false;
                    textAlpha = 250;
                }
            }
        } else {
            if (textAlpha > 0) {
                textAlpha -= ALPHA_SHIFT;
            } else {
                textAlpha = 5;
                incrementAlpha = true;
                nextRecords = true;
            }
        }
    }

    /**
     * Draws the records
     *
     * @param c the canvas to draw
     */
    @Override
    public void draw(Canvas c) {
        //General background
        super.draw(c);
        //Title text
        String title;
        if (onConfirmDelete) {
            title = context.getString(R.string.titleRecordsDelete);
            btnConfirmNo.draw(c);
            btnConfirmYes.draw(c);
        } else {
            title = context.getString(R.string.btnRecords);
            backBtn.draw(c);
            btnEmptyRecords.draw(c);
        }
        c.drawText(title, screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS, pTitleText);

        //Records
        if (onConfirmDelete) {

        } else {
            if (engineCallback.optionsManager.getRecordReferences().size() == 0) {
                Log.i("CrshDebug", "drawin norecords");
                c.drawText(context.getString(R.string.infoNoRecords), screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9, screenHeight / GameConstants.MENUSCREEN_ROWS * 3, pRecordsText);
            } else if (recordStrings.size() < 6) {
                Log.i("CrshDebug", "drawin size <6");
                int row = 2;
                for (String line : recordStrings) {
                    float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
                    float y = screenHeight / GameConstants.MENUSCREEN_ROWS * row;
                    c.drawText(line, x, y, pRecordsText);
                    row++;
                }
            } else {
                Log.i("CrshDebug", "drawin size big");
                pRecordsText.setAlpha(textAlpha);
                int row = 2;
                if (recordPageOne) {
                    for (int i = 0; i < 5; i++) {
                        float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
                        float y = screenHeight / GameConstants.MENUSCREEN_ROWS * row;
                        c.drawText(recordStrings.get(i), x, y, pRecordsText);
                        row++;
                    }
                } else {
                    for (int i = 5; i < recordStrings.size(); i++) {
                        float x = screenWidth / GameConstants.MENUSCREEN_COLUMNS * 9;
                        float y = screenHeight / GameConstants.MENUSCREEN_ROWS * row;
                        c.drawText(recordStrings.get(i), x, y, pRecordsText);
                        row++;
                    }
                }
            }
        }

    }

    /**
     * Controls the events on the touchscreen
     *
     * @param event the touch event
     * @return a new sceneId if it changed, or this id if it didn't change
     */
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:           // First finger
            case MotionEvent.ACTION_POINTER_DOWN:  // Second finger and so on
                break;

            case MotionEvent.ACTION_UP:                     // Last finger up
            case MotionEvent.ACTION_POINTER_UP:  // Any other finger up
                if (onConfirmDelete) {
                    if (isClick(btnConfirmNo, event)) {
                        onConfirmDelete = false;
                    }
                    if (isClick(btnConfirmYes, event)) {
                        engineCallback.optionsManager.emptyRecords();
                        generateRecordStrings();
                        onConfirmDelete = false;
                    }
                } else {
                    if (isClick(backBtn, event)) {
                        return 0;
                    }
                    if (isClick(btnEmptyRecords, event)) {
                        onConfirmDelete = true;
                    }
                }
            case MotionEvent.ACTION_MOVE: // Any finger moves
                break;
        }
        return this.id;
    }
}

