package it.mickeyhouse.replychallenge;

import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Data
public class Solution {

    public void saveInFile(String fileName) throws IOException {
        File f = new File(fileName);
        FileUtils.writeStringToFile(f, this.toString(), StandardCharsets.UTF_8);
    }
}
