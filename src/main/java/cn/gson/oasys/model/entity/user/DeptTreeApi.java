package cn.gson.oasys.model.entity.user;

import java.util.List;

public class DeptTreeApi {

    private String id;

    private String text;

    private boolean checked;

    private List<DeptTreeSonApi> children;

    private String state;

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

    public List<DeptTreeSonApi> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTreeSonApi> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DeptTreeApi{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                ", state='" + state + '\'' +
                '}';
    }
}
