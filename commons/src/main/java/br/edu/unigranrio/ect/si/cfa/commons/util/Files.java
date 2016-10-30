package br.edu.unigranrio.ect.si.cfa.commons.util;

import java.io.InputStream;
import java.util.Properties;

public final class Files {

    private Files(){}

    public static Properties getProperties(String path) throws Exception {
        InputStream is = Files.class.getClassLoader().getResourceAsStream(path);
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }

}
