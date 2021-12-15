package com.shop.helper;

import lombok.experimental.UtilityClass;

import java.net.URL;
import java.util.Objects;

@UtilityClass
public class ClasspathReader {

    public static String getFilePath(String resource) {
        URL resourceUrl = Objects.requireNonNull(ClasspathReader.class.getResource(resource));
        return resourceUrl.getFile();
    }
}
