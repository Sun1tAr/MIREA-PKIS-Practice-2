package my.learn.executor;

import lombok.RequiredArgsConstructor;
import my.learn.model.FileAnalysis;
import my.learn.service.FileService;
import my.learn.util.Logger;


import java.util.List;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class FileAnalyzerExecutor implements Runnable {

    private final BlockingQueue<String> pathsQueue;
    private final List<FileAnalysis> fileAnalyses;

    private final FileService fileService = new FileService();

    @Override
    public void run() {
        while (!pathsQueue.isEmpty()){
            String takenPath;
            try {
                takenPath = pathsQueue.take();
                Logger.log("Поток " + Thread.currentThread().getName() + " взял путь: " + takenPath);
            } catch (InterruptedException e) {
                Logger.error("Поток " + Thread.currentThread().getName() + " не хочет работать");
                return;
            }

            FileAnalysis analysis;
            try {
                fileService.setAndValidateFilePath(takenPath);
                analysis = new FileAnalysis(
                        takenPath,
                        fileService.isValidFilePath(),
                        (long) fileService.getCountOfWords(),
                        (long) fileService.getCountOfChars()
                );
            } catch (Exception e) {
                analysis = new FileAnalysis(
                        takenPath,
                        false,
                        0L,
                        0L
                );
            }

            synchronized (fileAnalyses) {
                fileAnalyses.add(analysis);
                fileAnalyses.notify();
            }

        }
    }
}
