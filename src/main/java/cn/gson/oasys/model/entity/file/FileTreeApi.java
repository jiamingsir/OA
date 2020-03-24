package cn.gson.oasys.model.entity.file;

import cn.gson.oasys.model.entity.user.DeptTreeSonApi;

import java.util.List;

public class FileTreeApi {

    private String id;

    private String text;

    private boolean checked;

    private List<FileTreeApi> children;

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

    public List<FileTreeApi> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeApi> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
