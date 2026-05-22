package service;

import entity.Transaction;
import entity.TransactionType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Transaction> loadTransaction(){
        List<Transaction> transactions = new ArrayList<>();

        File file = new File(FILE_PATH);

        if(!file.exists()){
            return transactions;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while((line = reader.readLine()) != null){
                if(isFirstLine){
                    isFirstLine = false;
                    continue;
                }

                if(line.isBlank()){
                    continue;
                }

                Transaction transaction = convertCsvToTransaction(line);
                transactions.add(transaction);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    private String convertTransactionToCsv(Transaction t) {
        return t.getId() + "," +
                t.getTitle()+ "," +
                t.getAmount()+ "," +
                t.getType()+ "," +
                t.getDate()+ "," +
                t.getDescription();
    }

    private Transaction convertCsvToTransaction(String line) {
        String[] parts = line.split(",");

        if(parts.length != 6){
            throw new IllegalArgumentException("Invalid CSV format. Expected 6 values but found " + parts.length);
        }

        Transaction t = new Transaction();
        t.setId(Integer.parseInt(parts[0]));
        t.setTitle(parts[1]);
        t.setAmount(Double.parseDouble(parts[2]));
        t.setType(TransactionType.valueOf(parts[3]));
        t.setDate(LocalDate.parse(parts[4]));
        t.setDescription(parts[5]);

        return t;
    }

}
