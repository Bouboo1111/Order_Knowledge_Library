package Hongweijie_KnowledgeBase.Proxy;

import Hongweijie_KnowledgeBase.Service.NoteService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

public class NoteServiceProxy implements InvocationHandler {
    private static final Logger logger = Logger.getLogger(NoteServiceProxy.class.getName());// 创建日志记录器
    private NoteService noteService;

    public NoteServiceProxy(NoteService noteService) {
        this.noteService = noteService;
    }

    public NoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        try {
            logger.info("【开始】"+startTime+" |方法："+method.getName()+" |参数："+ (args!=null?Arrays.toString(args):"无参数"));
            Object result = method.invoke(noteService, args);// 调用原始方法
            logger.info("【成功】 方法执行完成！");
            return result;
        }catch (InvocationTargetException e){
            logger.severe("【失败】 方法执行失败！");// 记录异常信息
            throw e.getTargetException();
        }finally {
            long duration = System.currentTimeMillis()-time;
            logger.info("【性能】"+method.getName()+" 方法执行完成！耗时："+duration+"毫秒");

        }
    }
}
