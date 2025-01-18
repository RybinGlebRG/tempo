package com.cappielloantonio.tempo.service.export;

import com.cappielloantonio.tempo.subsonic.models.Child;

import java.util.Objects;

/**
 * <p>Builds name for file from passed {@link Child}.</p>
 */
public class FileNameBuilder {

    private Child media;

    public FileNameBuilder media(Child media) {
        this.media = media;
        return this;
    }

    public String build(){
        Objects.requireNonNull(media);

        StringBuilder fileNameBuilder = new StringBuilder();
        if (media.getArtist() != null){
            fileNameBuilder.append(String.format("%s - ", media.getArtist()));
        }
        if (media.getTitle() != null){
            fileNameBuilder.append(media.getTitle());
        } else {
            fileNameBuilder.append(media.getId());
        }
        if (media.getSuffix() != null){
            fileNameBuilder.append(String.format(".%s", media.getSuffix()));
        }
        return fileNameBuilder.toString();
    }
}
