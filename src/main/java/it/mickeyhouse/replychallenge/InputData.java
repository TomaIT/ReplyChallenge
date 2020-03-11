package it.mickeyhouse.replychallenge;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
public class InputData {

    public InputData(String fileName) throws IOException {
        this.loadDataFromFile(fileName);
    }

    public void loadDataFromFile(String fileName) throws IOException {
        //TODO
        File fileDir = new File(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_8));
        String str;
        while ((str = in.readLine()) != null) {
            System.out.println(str);
        }
        in.close();
    }
}
