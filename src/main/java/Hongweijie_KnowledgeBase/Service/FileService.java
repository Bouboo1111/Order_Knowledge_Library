package Hongweijie_KnowledgeBase.Service;

import Hongweijie_KnowledgeBase.Config;
import Hongweijie_KnowledgeBase.IdContainer;
import Hongweijie_KnowledgeBase.Note;
import Hongweijie_KnowledgeBase.NoteException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class FileService {
    private Gson gson;
    private static String FILE_PATH;
    private static String ID_FILE_PATH;
    
    public FileService() {
        FILE_PATH = Config.getProperties("data.file.path");
        ID_FILE_PATH = Config.getProperties("data.file.path").replace(".json", "_ids.json");
        this.gson = new GsonBuilder()
                .setPrettyPrinting()  // 美化打印
                .create();
    }

    public void saveToFile(List<Note> notes, Set<Long> reusableIds) throws NoteException {
        if (FILE_PATH == null) {
            throw new NoteException("未找到要保存的笔记文件！");
        }

        // 保存笔记数据
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            gson.toJson(notes, fileWriter);
            fileWriter.flush();

            try {
                int time = Config.getIntProperty("data.save.time");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            System.out.println("笔记数据已保存到：" + FILE_PATH);
        } catch (IOException e) {
            throw new NoteException("保存笔记数据时出错：" + e.getMessage(), e);
        }
        
        // 保存可重用ID数据
        if (ID_FILE_PATH != null) {
            try (FileWriter idFileWriter = new FileWriter(ID_FILE_PATH)) {
                IdContainer idContainer = new IdContainer(reusableIds);
                gson.toJson(idContainer, idFileWriter);
                idFileWriter.flush();
                System.out.println("可重用ID数据已保存到：" + ID_FILE_PATH);
            } catch (IOException e) {
                throw new NoteException("保存可重用ID数据时出错：" + e.getMessage(), e);
            }
        }
    }

    @Deprecated
    public void saveToFile(List<Note> notes) throws NoteException {
        saveToFile(notes, new HashSet<>());
    }


    public List<Note> loadFromFile() throws NoteException {
        List<Note> notes = new ArrayList<>();
        
        // 加载笔记数据
        if(FILE_PATH == null){
            throw new NoteException("未找到要加载的笔记文件！");
        }

        File file = new File(FILE_PATH);
        if(file.exists() && file.length() > 0){
            try (FileReader fileReader = new FileReader(FILE_PATH)) {
                Type notesType = new TypeToken<List<Note>>(){}.getType();
                notes = gson.fromJson(fileReader, notesType);
                System.out.println("笔记数据已从：" + FILE_PATH + " 读取！");
            } catch (FileNotFoundException e) {
                throw new NoteException("笔记文件未找到：" + e.getMessage(), e);
            } catch (Exception e) {
                throw new NoteException("读取笔记文件时发生错误：" + e.getMessage(), e);
            }
        }
        
        return notes;
    }
    
    public Set<Long> loadReusableIds() throws NoteException {
        Set<Long> reusableIds = new HashSet<>();
        
        // 加载可重用ID数据
        if (ID_FILE_PATH != null) {
            File idFile = new File(ID_FILE_PATH);
            if(idFile.exists() && idFile.length() > 0){
                try (FileReader idFileReader = new FileReader(ID_FILE_PATH)) {
                    Type idType = new TypeToken<IdContainer>(){}.getType();
                    IdContainer idContainer = gson.fromJson(idFileReader, idType);
                    reusableIds = idContainer.getReusableIds();
                    System.out.println("可重用ID数据已从：" + ID_FILE_PATH + " 读取！");
                } catch (FileNotFoundException e) {
                    System.out.println("可重用ID文件未找到，将创建新文件");
                } catch (Exception e) {
                    System.out.println("读取可重用ID文件时发生错误：" + e.getMessage());
                }
            }
        }
        
        return reusableIds;
    }

}