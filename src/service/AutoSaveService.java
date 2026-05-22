package service;


import entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoSaveService {
    private final ScheduledExecutorService scheduler;
    private final FileService fileService;
    private final TransactionService transactionService;

    public AutoSaveService(FileService fileService, TransactionService transactionService) {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.fileService = fileService;
        this.transactionService = transactionService;
    }

    public void startAutoSave() {
        scheduler.scheduleAtFixedRate(() -> {
            try{
                List<Transaction> snapshot = transactionService.getTransactionSnapshot();
                fileService.saveTransactions(snapshot);

                System.out.println("\n[AutoSave] Transactions saved at " + LocalDateTime.now());
            } catch (Exception e){
                System.out.println("\n [AutoSave Error] Couldnt save transactions: " + e.getMessage());
            }
        }, 10, 60, TimeUnit.SECONDS);
    }

    public void stopAutoSave() {
        scheduler.shutdown();

        try{
            if(!scheduler.awaitTermination(5, TimeUnit.SECONDS)){
                scheduler.shutdown();
            }
        } catch (InterruptedException e){
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
