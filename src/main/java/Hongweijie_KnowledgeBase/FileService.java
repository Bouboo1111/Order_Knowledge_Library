package Hongweijie_KnowledgeBase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String FILE_PATH = "notes.json";
    private Gson gson;

    public FileService() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()  // 美化打印
                .create();
    }

    public void saveToFile(List<Note> notes)  {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FILE_PATH);
            gson.toJson(notes, fileWriter);
            fileWriter.flush(); // 添加这一行，确保数据刷新到磁盘
            System.out.println("数据已保存到："+ FILE_PATH);
        } catch (IOException e) {
            System.out.println("保存文件失败！"+ e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close(); // 确保关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Note> loadFromFile(){
        File file = new File(FILE_PATH);
        if(!file.exists()){
            return new ArrayList<>();
        }
        if(file.length() == 0){
            return new ArrayList<>();
        }
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            Type type = new TypeToken<List<Note>>(){}.getType();// 获取 List<Note> 的类型
            System.out.println("数据已从："+ FILE_PATH+ " 读取！");
            return gson.fromJson(fileReader, type);// 將 JSON 轉換為 List<Note>
        } catch (FileNotFoundException e) {
            System.out.println("文件读取失败！"+ e.getMessage());
            return new ArrayList<>();

        }

    }
}