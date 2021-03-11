package br.com.evolutil.zeplayer.filemusicscan;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Jez on 02/11/2016.
 * Class to offer funcional music scan
 *
 */

public class MusicScan {

    private AsyncTask<File, Integer, String[]> astsk;
    private Intent activityintent;
    private List<String> musiclist;
    private final static String EXTENSION_MP3 = ".mp3";
    private final static String EXTENSION_AAC = ".aac";
    private String extension;

    public MusicScan(Intent activityintent) {
        this.activityintent = activityintent;
        this.musiclist = new ArrayList<>();
        this.astsk = new AsyncTask<File, Integer, String[]>() {

            @Override
            protected String[] doInBackground(File... files) {
                musiclist = scanDir(files[0]);
                return musiclist.toArray(new String[0]);
            }
        };
    }

    public void executeScanMp3(String spath) {
        extension = EXTENSION_MP3;
        File filepath = new File(spath);
        try {
            String[] result = astsk.executeOnExecutor(Executors.newCachedThreadPool(), filepath).get();
            activityintent.putExtra("musiclist", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeScanAac(String spath) {
        extension = EXTENSION_AAC;
        File filepath = new File(spath);
        try {
            String[] result = astsk.executeOnExecutor(Executors.newCachedThreadPool(), filepath).get();
            activityintent.putExtra("musiclist", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> scanDir(File path) {
        List<String> r = new ArrayList<>();
        if (path.exists() && path.canRead()) {
            String[] fileList1 = path.list(makeFilterEndsWith(extension));
            for (String file : fileList1) {
                File ftmp = new File(path.getPath()+"/"+file);
                if (ftmp.isDirectory()) {
                    r.addAll(scanDir(ftmp));
                } else {
                    r.add(ftmp.getPath());
                }
            }
        }
        return r;
    }

    private FilenameFilter makeFilterEndsWith(final String tag) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                File current = new File(dir, filename);
                if (current.canRead()) {
                    boolean endsWith = tag == null || filename.toLowerCase().endsWith(tag);
                    return endsWith || current.isDirectory();
                }
                return false;
            }
        };
        return filter;
    }
}
