package BaseFunction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxTData {

    public String getPath(String fileName)   {
        String sourceFile="";
        try {
            File directory = new File(".");
            sourceFile = directory.getCanonicalPath() + "/src/testData/"
                    + fileName +".txt";
        }catch (IOException e){
            e.getStackTrace();
        }finally {
            return sourceFile;
        }

    }
    public List<String> readTxtData(String fileName){
        List<String> list = new ArrayList<String>();

        try {
            String pathname =getPath(fileName); // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";

            while ((line = br.readLine())!=null) {
                list.add(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;


    }

    public void  writeToFile(String fileName,String contenxt){
        try {
            File writename = new File(getPath(fileName)); // 相对路径，如果没有则要建立一个新的output。txt文件
//            writename.createNewFile(); // 创建新文件
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(writename, true)));;
            out.write(contenxt+"\r\n"); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
