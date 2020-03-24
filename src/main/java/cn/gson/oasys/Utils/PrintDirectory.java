package cn.gson.oasys.Utils;

import java.io.File;
import java.util.ArrayList;

public class PrintDirectory {
    File f = null;
    ArrayList<Boolean> arrayList = new ArrayList<>();//存每一层是否多个文件或目录，true为有多个文件或目录

    public PrintDirectory(String path) {//设置要输出的目录路径
        f = new File(path);
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


}
