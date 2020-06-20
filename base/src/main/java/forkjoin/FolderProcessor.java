package forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by mengtian on 2020/4/3
 */
public class FolderProcessor extends RecursiveTask<List<String>> {

    private final String path;
    private final String extension;

    public FolderProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    protected List<String> compute() {
        List<String> list = new ArrayList<>();
        List<FolderProcessor> tasks = new ArrayList<>();
        File file = new File(path);
        File[] contents = file.listFiles();

        return null;
    }
}
