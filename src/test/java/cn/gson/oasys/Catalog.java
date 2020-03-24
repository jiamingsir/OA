package cn.gson.oasys;

import cn.gson.oasys.Utils.PrintDirectory;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Catalog {

    @Test
    public void printCatalog(){
        PrintDirectory pd = new PrintDirectory("D:\\oasys\\resources\\static");
        pd.doThings();

    }

    public static void main(String[] args) {
        File dir = new File("d:/a");
        System.out.println("dir------>"+dir.getAbsolutePath());
        listAll();
    }

    public static void listAll() {
        File dir = new File("D:\\oasys\\resources\\static");
        File[] file_list = dir.listFiles();
        for (File file : file_list) {
            if (file.isDirectory()) {
                System.out.println("dir------>"+file.getAbsolutePath());
                listAll();
            } else {
                System.out.println("file------>"+file.getAbsolutePath());
            }
        }
    }


}
