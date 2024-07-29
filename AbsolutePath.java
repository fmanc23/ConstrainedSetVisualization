package utilities;

import java.io.File;

public class AbsolutePath {

    // Getting the absolute Path of the folder I'm working in
    static final String THISFOLDER = ".";

    private AbsolutePath(){}
    
    protected static String getAbsolutePath() {
        String absolutePath = new File(THISFOLDER).getAbsolutePath();
        // Removing the "." with -1 and "/bin" or "/jar" with -4
        absolutePath = absolutePath.substring(0, absolutePath.length()-1);
        return absolutePath;
    }

}
