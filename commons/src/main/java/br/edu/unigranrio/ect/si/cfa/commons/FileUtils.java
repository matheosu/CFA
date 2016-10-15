package br.edu.unigranrio.ect.si.cfa.commons;

import java.io.InputStream;
import java.util.Properties;

public final class FileUtils {

    private FileUtils(){}

    public static Properties getProperties(String path) throws Exception {
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

}
