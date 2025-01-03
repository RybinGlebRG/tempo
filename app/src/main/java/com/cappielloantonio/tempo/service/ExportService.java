package com.cappielloantonio.tempo.service;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;

import com.cappielloantonio.tempo.subsonic.models.Child;
import com.cappielloantonio.tempo.util.MappingUtil;

import java.util.Objects;

/**
 * <p>Service for exporting songs to local storage.</p>
 */
@UnstableApi
public class ExportService {

    private final Context context;
    private final Activity activity;
    private final DownloadManager downloadManager;

    public ExportService(@NonNull Context context, @NonNull Activity activity) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(activity);

        this.activity = activity;
        this.context = context;
        this.downloadManager = context.getSystemService(DownloadManager.class);
        Objects.requireNonNull(downloadManager);
    }

    /**
     * <p>Checking write permissions for Android 10 and below.</p>
     */
    private void checkPermissions(){
        boolean isWriteNotGranted = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && isWriteNotGranted) {
            Toast.makeText(context, "Write permissions not granted(", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void exportMedia(@NonNull Child media){
        checkPermissions();

        Objects.requireNonNull(media);
        MediaItem mediaItem = MappingUtil.mapDownload(media);

        StringBuilder fileNameBuilder = new StringBuilder();
        if (media.getArtist() != null){
            fileNameBuilder.append(String.format("%s - ", media.getArtist()));
        }
        if (media.getTitle() != null){
            fileNameBuilder.append(String.format("%s", media.getTitle()));
        } else {
            fileNameBuilder.append(String.format("%s", media.getId()));
        }
        if (media.getSuffix() != null){
            fileNameBuilder.append(String.format(".%s", media.getSuffix()));
        }
        String fileName = fileNameBuilder.toString();

        DownloadManager.Request request = new DownloadManager.Request(mediaItem.requestMetadata.mediaUri)
                .setMimeType(MimeTypes.BASE_TYPE_AUDIO)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        downloadManager.enqueue(request);
        Toast.makeText(context, "Export started", Toast.LENGTH_SHORT).show();
    }
}
