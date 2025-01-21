package com.cappielloantonio.tempo.service.export;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;

import com.cappielloantonio.tempo.subsonic.models.Child;
import com.cappielloantonio.tempo.util.MappingUtil;
import com.cappielloantonio.tempo.util.MusicUtil;
import com.cappielloantonio.tempo.util.Preferences;

import java.util.Objects;

/**
 * <p>Service for exporting songs to local storage.</p>
 */
@UnstableApi
public class Exporter {

    private final Context context;
    private final Activity activity;
    private final DownloadManager downloadManager;

    public Exporter(@NonNull Context context, @NonNull Activity activity) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(activity);

        this.activity = activity;
        this.context = context;
        this.downloadManager = context.getSystemService(DownloadManager.class);
        Objects.requireNonNull(downloadManager);
    }

    /**
     * <p>Export using {@link MediaItem}.</p>
     */
    public void exportMedia(@NonNull MediaItem mediaItem){
        Objects.requireNonNull(mediaItem);

        // Validationg all necessary permissions
        Validator validator = new Validator();
        validator.validatePermissions(context);

        // Getting pretty name for file
        String fileName = new FileNameBuilder()
                .mediaItem(mediaItem)
                .build();

        // Constructing request to DownloadManager
        DownloadManager.Request request = new DownloadManager.Request(mediaItem.requestMetadata.mediaUri)
                .setMimeType(MimeTypes.BASE_TYPE_AUDIO)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        downloadManager.enqueue(request);
        // TODO: i18n?
        Toast.makeText(context, "Export started", Toast.LENGTH_SHORT).show();
    }
}
