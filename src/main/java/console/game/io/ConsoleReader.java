package console.game.io;

import java.util.Scanner;

public class ConsoleReader {

    private final Scanner scanner;

    private ConsoleReader(Scanner s){
        this.scanner = s;
    }

    public static ConsoleReader getInstance(){
        return new ConsoleReader(new Scanner(System.in));
    }

    public String readInput(){
        return scanner.nextLine();
    }

    public void stopReading(){
        scanner.close();
    }
}
