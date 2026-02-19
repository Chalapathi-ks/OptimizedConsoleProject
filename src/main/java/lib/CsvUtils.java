package lib;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {


    private static List<Map<String, Object>> getAsMap(BufferedReader reader) throws IOException {
        List<String []> rows=readCsvRows(reader);
        String [] headerFields=rows.remove(0);

        List<Map<String, Object>> csvMap = new ArrayList<>();

        for(int i=0;i<rows.size();i++){

            Map<String, Object> entry = new HashMap<>();
            for(int j=0;j<headerFields.length;j++){

                entry.put(headerFields[j],rows.get(i)[j]);
            }

            csvMap.add(entry);

        }
        return csvMap;
    }

    public static  List<Map<String, Object>> getAsMap(String file) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file));
        return getAsMap(reader);
    }

    public static  List<Map<String, Object>> getAsMap(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return getAsMap(reader);
    }


    public static void updateRow(String file,int row, int column,String value) throws IOException {

        List<String []> rows=readCsvRows(file);
        rows.get(row)[column]=value;

        CSVWriter csvWriter=new CSVWriter(new FileWriter(file));

        csvWriter.writeAll(rows);
        csvWriter.close();
    }

    public static void updateRows(String file, int column,String value) throws IOException {

        List<String [] >rows=readCsvRows(file);
        for(int i=1;i<rows.size();i++){
            rows.get(i)[column]=value;
        }
        CSVWriter csvWriter=new CSVWriter(new FileWriter(file));
        csvWriter.writeAll(rows);
        csvWriter.close();

    }

    private static List<String []> readCsvRows(String file) throws IOException {

        BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
        return readCsvRows(bufferedReader);
    }

    private static List<String []> readCsvRows(BufferedReader reader) throws IOException {

        CSVReader csvReader=new CSVReader(reader);
        List<String []> rows=csvReader.readAll();
        csvReader.close();
        return rows;

    }
}
