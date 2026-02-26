package my.learn;

import my.learn.console.AppExecutor;
import my.learn.console.ConsoleSender;
import my.learn.service.FileService;
import my.learn.service.OperationsService;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FileService fs = new FileService();
        OperationsService os = new OperationsService(fs);
        ConsoleSender cs = new ConsoleSender();

        AppExecutor appExecutor = new AppExecutor(sc, cs, os, fs);

        while (os.isReady()) {
            try {
                appExecutor.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}