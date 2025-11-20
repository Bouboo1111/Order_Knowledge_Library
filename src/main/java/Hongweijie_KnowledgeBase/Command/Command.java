package Hongweijie_KnowledgeBase.Command;

import java.util.Scanner;

public interface Command {
    void execute(String input, Scanner scanner);
    String getCommandName();
}
