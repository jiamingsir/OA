package cn.gson.oasys.model.entity.user;

import java.util.List;

public class DeptTreeSonApi {

    private String id;

    private String text;

    private boolean checked;

    //private String children;

    //private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "DeptTreeSonApi{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
