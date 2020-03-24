package cn.gson.oasys.services.task;

import cn.gson.oasys.model.dao.filedao.FileListdao;
import cn.gson.oasys.model.dao.filedao.FilePathdao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.file.FilePath;
import cn.gson.oasys.model.entity.file.FileTreeApi;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileCatalogService {

    @Autowired
    private FileListdao fileListdao;
    @Autowired
    private FilePathdao filePathdao;
    @Autowired
    private UserDao userDao;

    @RequestMapping("fileste2")
    @ResponseBody
    public List<FileTreeApi> deptTreeLists (HttpServletRequest req, String fileIdS) {
        HttpSession session = req.getSession();
        Long userId = Long.parseLong(session.getAttribute("userId") + "");
        User user = userDao.findOne(userId);
        //[{"id":3,"text":"系统管理","children":[{"id":5,"text":"用户管理","children":[{"id":6,"text":"用户新增","children":null},{"id":7,"text":"用户查询","children":null},{"id":10,"text":"用户删除","children":null},{"id":11,"text":"用户修改","children":null}]},{"id":12,"text":"机构管理","children":[{"id":13,"text":"机构新增","children":null},{"id":14,"text":"机构查询","children":null},{"id":16,"text":"机构删除","children":null},{"id":17,"text":"机构修改","children":null}]}]
		/*这些都完成之后，可以根据
		var pids = $('#rolePer').combotree('getValues');
		来获取选择的值*/
        List<FileTreeApi> fileTrees = new ArrayList<>();
        if (fileIdS.equals("")) {
            //查询父级部门
            FilePath filePath = filePathdao.findByPathName(user.getUserName());
            List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
            for (int f = 0; f < fileFatherList.size(); f++) {
                FileTreeApi filetree = new FileTreeApi();
                filetree.setId(String.valueOf(f));
                filetree.setText(fileFatherList.get(f).getPathName());
                filetree.setChecked(false);

                //根据父级id查子级部门
                List<FilePath> fileListf = filePathdao.findByParentId(fileFatherList.get(f).getParentId());
               /* List<DeptTreeSonApi> depttreezList = new ArrayList<DeptTreeSonApi>();
                for (int z = 0; z < deptListf.size(); z++) {
                    DeptTreeSonApi depttreez = new DeptTreeSonApi();
                    depttreez.setId(String.valueOf(f) + String.valueOf(z));
                    depttreez.setText(deptListf.get(z).getDeptName());
                    depttreez.setChecked(false);
                    //depttreez.setChildren(null);
                    //depttreez.setState("closed");
                    depttreezList.add(depttreez);
                }*/
                List<FileTreeApi> filetreezList = new ArrayList<>();
                for (int z = 0; z < fileListf.size(); z++) {
                    FileTreeApi filetreez = new FileTreeApi();
                    filetreez.setId(String.valueOf(f) + String.valueOf(z));
                    filetreez.setText(fileListf.get(z).getPathName());
                    filetreez.setChecked(false);
                    //depttreez.setChildren(null);
                    //depttreez.setState("closed");
                    filetreezList.add(filetreez);
                }

                filetree.setChildren(filetreezList);
                filetree.setState("open");
                fileTrees.add(filetree);
            }
        }else {
            //带默认选中
            String[] fileidsArr = fileIdS.split(",");

            //查询父级部门
            //List<DeptFather> deptFatherList = deptFatherDao.findALLList();
            List<FilePath> fileFatherList = filePathdao.findAlls();
            for (int f = 0; f < fileFatherList.size(); f++) {
                FileTreeApi filetree = new FileTreeApi();
                filetree.setId(String.valueOf(f));
                filetree.setText(fileFatherList.get(f).getPathName());
                filetree.setChecked(false);

                //根据父级id查子级部门
                List<FilePath> fileListf = filePathdao.findByParentId(fileFatherList.get(f).getParentId());
                List<FileTreeApi> flietreezList = new ArrayList<>();
                for (int z = 0; z < fileListf.size(); z++) {
                    int falgx = 0;
                    for (int x =0;x <fileidsArr.length;x++){
                        long fileidl = Long.parseLong(fileidsArr[x]);
                        if (fileListf.get(z).getId() == fileidl){
                            falgx = 1;
                        }
                    }
                    FileTreeApi filetreez = new FileTreeApi();
                    if (falgx == 0){
                        filetreez.setId(String.valueOf(f) + String.valueOf(z));
                        filetreez.setText(fileListf.get(z).getPathName());
                        filetreez.setChecked(false);
                        filetreez.setChildren(null);
                        filetreez.setState("closed");
                    }else {
                        filetreez.setId(String.valueOf(f) + String.valueOf(z));
                        filetreez.setText(fileListf.get(z).getPathName());
                        filetreez.setChecked(true);
                        filetreez.setChildren(null);
                        filetreez.setState("closed");
                    }
                    flietreezList.add(filetreez);
                }
                filetree.setChildren(flietreezList);
                filetree.setState("open");
                fileTrees.add(filetree);
            }

        }
        return fileTrees;

    }


}
