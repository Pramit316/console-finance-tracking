package service;

import entity.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileService {
    private static final String FILE_PATH = "data/transactions.csv";

    public void saveTransactions(List<Transaction> transactions){
        File file = new File(FILE_PATH);

        File parentFolder = file.getParentFile();
        if(parentFolder !=null && !parentFolder.exists()){
            parentFolder.mkdirs();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write("id,title,amount,type,date,description");
            writer.newLine();;

            for(Transaction t : transactions){
                writer.write(convertTransactionToCsv(t));
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertTransactionToCsv(Transaction t) {
        return t.getId() + "," +
                t.getTitle()+ "," +
                t.getAmount()+ "," +
                t.getTitle()+ "," +
                t.getDate()+ "," +
                t.getDescription();
    }
}
