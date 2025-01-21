package com.cappielloantonio.tempo.service.export;


import static org.mockito.Mockito.when;

import android.os.Bundle;

import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileNameBuilderTest {

    @Mock
    Bundle extras;

    /**
     * <p>Title is present, artist is present, suffix is present.</p>
     */
    @Test
    public void shouldBuildName(){
        /*
        Given
         */
        String anyMediaId = "123";

        MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                .setTitle("Test Title")
                .setArtist("Test Artist")
                .setExtras(extras)
                .build();

        MediaItem mediaItem = new MediaItem.Builder()
                .setMediaId(anyMediaId)
                .setMediaMetadata(mediaMetadata)
                .build();

        when(extras.getString("suffix"))
                .thenReturn("mp3");


        /*
        When
         */
        String resultFileName = new FileNameBuilder()
                .mediaItem(mediaItem)
                .build();


        /*
        Then
         */
        Assert.assertEquals(
                "Incorrect file name",
                "Test Artist - Test Title.mp3",
                resultFileName
        );
    }


    /**
     * <p>Title equals file path, artist is unknown, suffix is present.</p>
     *
     * <p>Using last part of path as file name.</p>
     */
    @Test
    public void shouldProcessPath(){
        /*
        Given
         */
        String anyMediaId = "123";

        MediaMetadata mediaMetadata = new MediaMetadata.Builder()
                .setTitle("/path/to/file/with/title")
                .setArtist("[Unknown Artist]")
                .setExtras(extras)
                .build();

        MediaItem mediaItem = new MediaItem.Builder()
                .setMediaId(anyMediaId)
                .setMediaMetadata(mediaMetadata)
                .build();

        when(extras.getString("suffix"))
                .thenReturn("mp3");


        /*
        When
         */
        String resultFileName = new FileNameBuilder()
                .mediaItem(mediaItem)
                .build();


        /*
        Then
         */
        Assert.assertEquals(
                "Incorrect file name",
                "title.mp3",
                resultFileName
        );
    }
}
