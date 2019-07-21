package theswiftbaker.com.testnotes;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class TodoItem {
    private String text;

    private EditText et;
    private ImageButton delete;
    private CheckBox done;
    public TodoItem(String msg, ImageButton btn, CheckBox cb, EditText edit)
    {
        text=msg;
        delete=btn;
        done = cb;
        et = edit;
    }

    public EditText getEt() {
        return et;
    }

    public void setEt(EditText et) {
        this.et = et;
    }

    public String getImageid() {
        return text;
    }
    public ImageButton getImageheading() {
        return delete;
    }
    public void setImageheading(ImageButton btn) {
        this.delete = btn;
    }
    public void setText(String msg) {
        this.text = msg;
    }

    public CheckBox getDone() {
        return done;
    }

    public void setDone(CheckBox done) {
        this.done = done;
    }
}
