package UnbxdTests.testNG.dataProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lib.UnbxdFileUtils;
import lib.annotation.FileToTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

public class ResourceLoader {

    private final String testFilePath="target"+File.separator+"test-classes"+File.separator+"testData"+File.separator;

    @DataProvider(name = "getTestDataFromFile")
    public Object [][] getTestDataFromFile(Method m){

        Object [] [] jsonData;
        String filePath=(m.getAnnotation(FileToTest.class)).value();
        String fileName = testFilePath+filePath;
        fileName= UnbxdFileUtils.normalizePath(fileName);

        JsonParser parser=new JsonParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JsonObject object=(JsonObject) obj;
            JsonArray array=object.getAsJsonArray("testData");
            jsonData=new Object[array.size()][];

            for(int i=0;i<array.size();i++)
            {
                jsonData[i]=new Object[]{array.get(i)};
            }

            return jsonData;
        }

        catch(Exception e)
        {
            e.printStackTrace();

        }
        return new Object[0][0];
    }

}




