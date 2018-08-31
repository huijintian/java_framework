package jdk8.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mengtian on 2018/8/30
 */
public class FilesTest {
    public static void main(String[] args) {
        // 遍历当前目录下的文件
        try (Stream<Path> stream = Files.list(Paths.get(""))) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> !path.startsWith("."))
                    .sorted()
                    .collect(Collectors.joining("\n"));
            System.out.println("List: " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 查询当前目录及其子目录下的特定文件
        Path start = Paths.get("");
        int maxDepth = 5;
        try (Stream<Path> stream = Files.find(start, maxDepth,
                (path, attr) -> String.valueOf(path).endsWith(".md"))) {
            String joined = stream.sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n"));
            System.out.println("Found: " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 查询当前目录及其子目录下的特定文件的另一种方式
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String joined = stream.map(String::valueOf)
                    .filter(path -> path.endsWith(".md"))
                    .sorted()
                    .collect(Collectors.joining("\n"));
            System.out.println("Found2 :" + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
