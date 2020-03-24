package cn.gson.oasys.Utils;

import cn.gson.oasys.model.dao.filedao.FileListdao;
import cn.gson.oasys.model.dao.filedao.FilePathdao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.file.FileList;
import cn.gson.oasys.model.entity.file.FilePath;
import cn.gson.oasys.model.entity.file.FileTreeApi;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/")
public class PrintDirectoryBase {

    @Autowired
    private UserDao udao;
    @Autowired
    private FilePathdao filePathdao;
    @Autowired
    private FileListdao fileListdao;

    File f = null;
    String fatherpath="";
    ArrayList<Boolean> arrayList = new ArrayList<>();//存每一层是否多个文件或目录，true为有多个文件或目录

    public void PrintDirectoryBase(@SessionAttribute("userId") Long userId, String path) {//设置要输出的目录路径
        f = new File(path);
        // 通过发布人id找用户
        User user = udao.findOne(userId);
        List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
        fatherpath = fileFatherList.get(0).getPathName();
    }

    public void doThings() {//入口
        arrayList.add(false);//刚开始是单个目录
        printFile(f);
    }

    public void printFile(File dirc) {
        printChars(dirc);//先打印前面的字符
        if (testDir()) {//是不是个目录？
            File[] list = dirc.listFiles();//列出子文件
            if(list.length==1){//只有一个子文件
                arrayList.add(false);//false为单个文件或目录
                f = list[0];
                printFile(f);
                arrayList.remove(arrayList.size()-1);//为了省略多余字符
            }
            else{
                arrayList.add(true);
                for (File file : list) {
                    f = file;
                    if(f == list[list.length-1]){
                        arrayList.set(arrayList.size()-1, false);//为了省略多余字符
                    }
                    printFile(f);
                }
                arrayList.remove(arrayList.size()-1);//为了省略多余字符
            }
        }
    }

    public void printChars(File dirc) {//让输出形象一些
        for(Boolean arr:arrayList){//根据集合打印，省略多余的符号
            System.out.print("\t");
            if(arr){//如果是多个文件或目录
                System.out.print("|");
            }
        }
        if(!arrayList.get(arrayList.size()-1))//最里面的文件也要|的符号
            System.out.print("|");
        System.out.print("-->");
        System.out.println(dirc.getName());
    }

    public boolean testDir() {//是不是个目录？
        return f.isDirectory();
    }

    @RequestMapping("fileste1")
    @ResponseBody
    public List<FileTreeApi> deptTreeLists (HttpServletRequest req, String fileIdS) {
        HttpSession session = req.getSession();
        Long userId = Long.parseLong(session.getAttribute("userId") + "");
        User user = udao.findOne(userId);
        //[{"id":3,"text":"系统管理","children":[{"id":5,"text":"用户管理","children":[{"id":6,"text":"用户新增","children":null},{"id":7,"text":"用户查询","children":null},{"id":10,"text":"用户删除","children":null},{"id":11,"text":"用户修改","children":null}]},{"id":12,"text":"机构管理","children":[{"id":13,"text":"机构新增","children":null},{"id":14,"text":"机构查询","children":null},{"id":16,"text":"机构删除","children":null},{"id":17,"text":"机构修改","children":null}]}]
		/*这些都完成之后，可以根据
		var pids = $('#rolePer').combotree('getValues');
		来获取选择的值*/
		Long pathid = (long)0;
        List<FileTreeApi> fileTrees = new ArrayList<>();
        if (fileIdS.equals("")) {
            //查询父级部门
            FilePath filePath = filePathdao.findByPathName(user.getUserName());
            List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
            for (int f = 0; f < fileFatherList.size(); f++) {
                FileTreeApi filetree = new FileTreeApi();
                //filetree.setId(String.valueOf(f));
                filetree.setId("w"+String.valueOf(fileFatherList.get(f).getId()));
                filetree.setText(fileFatherList.get(f).getPathName());
                filetree.setChecked(false);
                pathid = fileFatherList.get(f).getId();
                //根据父级id查子级部门
                List<FileTreeApi> filetreezList = new ArrayList<>();
                filetreezList =ccc(pathid,f);

                filetree.setChildren(filetreezList);
                filetree.setState("open");
                fileTrees.add(filetree);
            }
        }else {
            //带默认选中
            String[] fileidsArr = fileIdS.split(",");

            //查询父级部门
            List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
            for (int f = 0; f < fileFatherList.size(); f++) {
                FileTreeApi filetree = new FileTreeApi();
                //filetree.setId(String.valueOf(f));
                filetree.setId("w"+String.valueOf(fileFatherList.get(f).getId()));
                filetree.setText(fileFatherList.get(f).getPathName());
                filetree.setChecked(false);
                pathid = fileFatherList.get(f).getId();
                //查下级目录
                List<FileTreeApi> filetreezList = new ArrayList<>();
                filetreezList =xuan(pathid,f,fileidsArr);

                filetree.setChildren(filetreezList);
                filetree.setState("open");
                fileTrees.add(filetree);
            }

        }
        return fileTrees;

    }

    public List<FileTreeApi> ccc(Long pathid,int z){
        List<FileTreeApi> fileTreeApi =new ArrayList<>();
        FilePath filePath = filePathdao.findOne(pathid);
        z++;
        String idstr = "";
        for (int i = 0; i < z; i++) {
            idstr = idstr + "_" + i;
        }
        List<FilePath> fileListf = filePathdao.findByParentId(pathid);
        if (fileListf.size()>0){
            int y = 0;

            for (FilePath filePath1:fileListf) {
                //String idstrsun = "";
                FileTreeApi filetreez = new FileTreeApi();
                y++;
                idstr = idstr + "_" + z + "_" + y;
                filetreez.setId("w"+String.valueOf(filePath1.getId()));

                filetreez.setText(filePath1.getPathName());
                filetreez.setChecked(false);
                List<FileTreeApi> fileTreeApis = ccc(filePath1.getId(),z);
                filetreez.setChildren(fileTreeApis);
                filetreez.setState("open");
                fileTreeApi.add(filetreez);
            }
        }
        List<FileList> fileListfile = fileListdao.findByFpath(filePath);
        if (fileListfile.size()>0){
            int y = 0;

            for (FileList filePath1:fileListfile) {
                FileTreeApi filetreez = new FileTreeApi();
                y++;
                idstr = idstr + "_" + z + "_" + y;
                filetreez.setId("m"+String.valueOf(filePath1.getFileId()));

                filetreez.setText(filePath1.getFileName());
                filetreez.setChecked(false);
                filetreez.setState("open");
                fileTreeApi.add(filetreez);
            }
        }


        return fileTreeApi;
    }

    public List<FileTreeApi> xuan(Long pathid,int z,String[] fileidsArr){
        List<FileTreeApi> fileTreeApi =new ArrayList<>();
        FilePath filePath = filePathdao.findOne(pathid);
        z++;
        String idstr = "";
        for (int i = 0; i < z; i++) {
            idstr = idstr + "_" + i;
        }
        List<FilePath> fileListf = filePathdao.findByParentId(pathid);
        if (fileListf.size()>0){
            int y = 0;

            for (FilePath filePath1:fileListf) {
                FileTreeApi filetreez = new FileTreeApi();
                y++;
                idstr = idstr + "_" + z + "_" + y;
                filetreez.setId("w"+String.valueOf(filePath1.getId()));
                filetreez.setText(filePath1.getPathName());
                filetreez.setChecked(false);
                List<FileTreeApi> fileTreeApis = xuan(filePath1.getId(),z,fileidsArr);
                filetreez.setChildren(fileTreeApis);
                filetreez.setState("open");
                fileTreeApi.add(filetreez);
            }
        }
        List<FileList> fileListfile = fileListdao.findByFpath(filePath);
        if (fileListfile.size()>0){
            int y = 0;

            for (FileList filePath1:fileListfile) {
                int falgx = 0;
                for (int x =0;x <fileidsArr.length;x++){
                    String filedstr = fileidsArr[x];
                    String filestr = filedstr.substring(1,filedstr.length());
                    long fileidl = Long.parseLong(filestr);
                    if (filePath1.getFileId() == fileidl){
                        falgx = 1;
                    }
                }
                FileTreeApi filetreez = new FileTreeApi();
                y++;
                idstr = idstr + "_" + z + "_" + y;
                if (falgx == 0){
                    filetreez.setId("m"+String.valueOf(filePath1.getFileId()));
                    filetreez.setText(filePath1.getFileName());
                    filetreez.setChecked(false);
                    filetreez.setChildren(null);
                    filetreez.setState("open");
                }else {
                    filetreez.setId("m"+String.valueOf(filePath1.getFileId()));
                    filetreez.setText(filePath1.getFileName());
                    filetreez.setChecked(true);
                    filetreez.setChildren(null);
                    filetreez.setState("open");
                }

                fileTreeApi.add(filetreez);
            }
        }


        return fileTreeApi;
    }


}
