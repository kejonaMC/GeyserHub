package dev.projectg.geyserhub.utils;

import javax.annotation.Nullable;
import java.io.InputStream;

public final class FileUtils {

    @Nullable
    public static InputStream getResource(String resource) {
        return FileUtils.class.getClassLoader().getResourceAsStream(resource);
    }

    private FileUtils() {

    }
}
