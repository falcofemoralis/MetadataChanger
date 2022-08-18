import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws ParseException {
        File folder = new File("./" + "files");

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) return;

            for (File file : files) {
                if (file.isFile()) {
                    String[] fileName = file.getName().split("\\.");
                    String vidName = fileName[1].replaceAll("VID", "").trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
                    Date date = sdf.parse(vidName);
                    FileTime ft = FileTime.fromMillis(date.getTime());

                    try {
                        Files.setLastModifiedTime(file.toPath(), ft);
                        BasicFileAttributeView attributes = Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class);
                        attributes.setTimes(ft, ft, ft);
                    } catch (Exception e) {
                        System.out.println(file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
