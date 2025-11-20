package Hongweijie_KnowledgeBase;

public class NoteException extends Exception{
    public NoteException(String message){
        super(message);// 调用父类的构造方法
    }
    public NoteException(String message, Throwable cause){
        super(message, cause);// 调用父类的构造方法
    }

}
