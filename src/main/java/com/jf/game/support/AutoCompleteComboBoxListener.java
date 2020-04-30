package com.jf.game.support;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.jf.game.pojo.Game;

/**
 *
 * @author
 * 监听ComboBox，自动完成搜索功能
 * @param <T>
 */
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private ObservableList<Game> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
        this.comboBox = comboBox;
        data = comboBox.getItems();
        // 支持自定义输入
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println("event.getCode()  ---> "+event.getCode());
        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }


        //System.out.println(" 输入的数据 "+AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toUpperCase());

        String customInput = comboBox.getEditor().getText();

        ObservableList list = FXCollections.observableArrayList();
        for (int i=0; i<data.size(); i++) {
            // 名称是否包含
            if(data.get(i).getGameName().contains(customInput)) {
                list.add(data.get(i));
            }
        }

        comboBox.setItems(list);

        comboBox.getEditor().setText(customInput);

        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(customInput.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        System.out.println("moveCaret  ---> "+textLength);
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
            //System.out.println(comboBox.getValue().toString());
        }
        moveCaretToPos = false;
        comboBox.setVisibleRowCount(20);
    }

}