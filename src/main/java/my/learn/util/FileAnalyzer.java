package my.learn.util;

import my.learn.executor.FileAnalyzerExecutor;
import my.learn.model.FileAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static my.learn.config.AppConfig.COUNT_OF_THREADS;

public class FileAnalyzer {



    public static List<FileAnalysis> analyzeSomeFiles(List<String> filePaths) {


        BlockingQueue<String> files = new LinkedBlockingQueue<>(filePaths);
        List<FileAnalysis> analyses = new ArrayList<>();

        ExecutorService threadPool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
        for (int i = 0; i < COUNT_OF_THREADS; i++) {
            threadPool.submit(new FileAnalyzerExecutor(files, analyses));
        }

        while (!files.isEmpty()) {
            try {
                synchronized (analyses) {
                    Logger.log("Поток " + Thread.currentThread().getName() + " ждет");
                    analyses.wait();
                }
            } catch (InterruptedException e) {
                Logger.error("Поток " + Thread.currentThread().getName() + " не хочет работать");
            }
        }

        threadPool.shutdown();
        return analyses;
    }





}
