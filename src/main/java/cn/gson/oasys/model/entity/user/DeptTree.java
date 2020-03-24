package cn.gson.oasys.model.entity.user;

public class DeptTree {

    private String DeptTreeId;

    private String DeptTreeNmae;

    private String DeptTreeRemake;

    private boolean DeptSelect;

    public String getDeptTreeId() {
        return DeptTreeId;
    }

    public void setDeptTreeId(String deptTreeId) {
        DeptTreeId = deptTreeId;
    }

    public String getDeptTreeNmae() {
        return DeptTreeNmae;
    }

    public void setDeptTreeNmae(String deptTreeNmae) {
        DeptTreeNmae = deptTreeNmae;
    }

    public String getDeptTreeRemake() {
        return DeptTreeRemake;
    }

    public void setDeptTreeRemake(String deptTreeRemake) {
        DeptTreeRemake = deptTreeRemake;
    }

    public boolean isDeptSelect() {
        return DeptSelect;
    }

    public void setDeptSelect(boolean deptSelect) {
        DeptSelect = deptSelect;
    }

    @Override
    public String toString() {
        return "DeptTree{" +
                "DeptTreeId='" + DeptTreeId + '\'' +
                ", DeptTreeNmae='" + DeptTreeNmae + '\'' +
                ", DeptTreeRemake='" + DeptTreeRemake + '\'' +
                ", DeptSelect=" + DeptSelect +
                '}';
    }
}
