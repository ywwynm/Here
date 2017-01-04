package util;

import meta.Definitions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by 张启 on 2016/4/17.
 * utils for {@link File}
 */
public class FileUtil {

    public static String saveFile(HttpServletRequest request, Part filePart, long userId, short blogType) {
        if (filePart == null) {
            return null;
        }

        InputStream is = null;
        FileOutputStream fos = null;
        try {
            String userTypePath = userId + "\\" + blogType;
            File dir = new File(request.getServletContext().getRealPath("/")
                    + Definitions.Constants.MEDIA_FILE_URL_ROOT
                    + userTypePath);
            boolean existed = isDirExisted(dir);
            if (!existed) return null;

            is = filePart.getInputStream();
            String name = generateMediaFileName(filePart.getSubmittedFileName());
            File file = new File(dir, name);
            fos = new FileOutputStream(file);

            int ch;
            while ((ch = is.read()) != -1) {
                fos.write(ch);
            }
            return userTypePath + "\\" + name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(is);
            close(fos);
        }
    }

    public static boolean deleteFile(String pathName) {
        File file = new File(pathName);
        return file.delete();
    }

    private static boolean isDirExisted(File dir) {
        boolean existed = true;
        if (!dir.exists()) {
            existed = dir.mkdirs();
        }
        return existed;
    }

    private static String generateMediaFileName(String basename) {
        int slash = basename.lastIndexOf("\\");
        if (slash != -1) {
            basename = basename.substring(slash, basename.length());
        }

        int point = basename.lastIndexOf(".");
        if (point != -1) {
            String prefix = basename.substring(0, point);
            String postfix = basename.substring(point, basename.length());
            return prefix + "_" + System.currentTimeMillis() + postfix;
        }
        return basename + "_" + System.currentTimeMillis();
    }

    private static boolean close(Closeable closeable) {
        if (closeable == null) {
            return true;
        }
        try {
            closeable.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
