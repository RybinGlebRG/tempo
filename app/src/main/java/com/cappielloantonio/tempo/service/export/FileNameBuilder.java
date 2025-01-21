package com.cappielloantonio.tempo.service.export;

import androidx.media3.common.MediaItem;

import com.cappielloantonio.tempo.subsonic.models.Child;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Builds name for file using passed {@link Child}.</p>
 */
public class FileNameBuilder {

    private MediaItem mediaItem;

    public FileNameBuilder mediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
        return this;
    }

    public String build() {
        Objects.requireNonNull(mediaItem);

        StringBuilder fileNameBuilder = new StringBuilder();

        String title = mediaItem.mediaMetadata.title != null ? mediaItem.mediaMetadata.title.toString() : null;
        String artist = mediaItem.mediaMetadata.artist != null ? mediaItem.mediaMetadata.artist.toString() : null;
        String suffix = mediaItem.mediaMetadata.extras != null ? mediaItem.mediaMetadata.extras.getString("suffix") : null;

        // Sometimes title can consist of path to file on server. Most likely when there is no metadata in files
        boolean isTitleContainsPath = false;
        if (title != null && title.contains("/")) {
            isTitleContainsPath =true;
            List<String> pathParts = Arrays.stream(title.split("/"))
                    .filter(Objects::nonNull)
                    .filter(item -> !item.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));
            title = pathParts.get(pathParts.size() - 1);
        }

        // If there are problems with metadata, then just use calculated title
        if (!isTitleContainsPath) {
            if (artist != null) {
                fileNameBuilder.append(String.format("%s - ", artist));
            }
        }

        // If there is no title at all, then use id. Better than nothing
        if (title != null) {
            fileNameBuilder.append(title);
        } else {
            fileNameBuilder.append(mediaItem.mediaId);
        }

        if (suffix != null) {
            fileNameBuilder.append(String.format(".%s", suffix));
        }

        return fileNameBuilder.toString();
    }
}
