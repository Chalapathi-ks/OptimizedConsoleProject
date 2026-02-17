package lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UnbxdFileUtils {


    public static String getFilePathInResources(String file){

        ClassLoader classLoader= UnbxdFileUtils.class.getClassLoader();
        String path=classLoader.getResource(file).getPath();
        path=normalizePath(path);
        return path;
    }

    public static InputStream getResourceAsStream(String file){

        ClassLoader classLoader= UnbxdFileUtils.class.getClassLoader();
        return classLoader.getResourceAsStream(file);
    }

    public static boolean createFile(String fileName){
        File file=new File(fileName);
        return createFile(file);
    }

    public static boolean createFile(File file){

        if(file.exists())
            return true;
        else{
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Failed to Create File : "+file.getAbsolutePath());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public  static String normalizePath(String path){

        return path.replace("\\",File.separator).replace("/",File.separator);
    }

}
