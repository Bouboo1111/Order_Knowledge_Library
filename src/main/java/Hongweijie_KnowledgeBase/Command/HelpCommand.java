package Hongweijie_KnowledgeBase.Command;

import java.util.Scanner;

public class HelpCommand implements Command{
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println("""
        使用帮助：
        
        添加笔记：
          快速添加：输入：标题 | 内容
          正常添加：输入关键词：添加、新增、创建或add
          
        搜索笔记：
          输入笔记关键词即可
        
        查看笔记：
          输入关键词：查看、展示、显示或display
          直接输入数字ID：比如1、2、3...
        
        修改笔记:
          输入关键词：修改、更改或update  
          
        删除笔记：
          输入关键词：删除、删掉、去除或delete
          
        帮助: 
          输入关键词：帮助、使用、操作、帮忙或help
          
        退出： 
          退出程序或quit
        """);//文本块

    }

    @Override
    public String getCommandName() {
        return "帮助";
    }
}
