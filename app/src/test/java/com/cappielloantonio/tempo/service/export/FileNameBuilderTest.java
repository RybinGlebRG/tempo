package com.cappielloantonio.tempo.service.export;


import com.cappielloantonio.tempo.subsonic.models.Child;

import org.junit.Assert;
import org.junit.Test;

public class FileNameBuilderTest {

    /**
     * <p>Title is present, artist is present, suffix is present.</p>
     */
    @Test
    public void shouldBuildName(){
        /*
        Given
         */
        String anyMediaId = "123";

        Child media = new Child(
                anyMediaId,
                null,
                false,
                "Test Title",
                null,
                "Test Artist",
                null,
                null,
                null,
                null,
                null,
                null,
                "mp3",
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
                );


        /*
        When
         */
        String resultFileName = new FileNameBuilder()
                .media(media)
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

        Child media = new Child(
                anyMediaId,
                null,
                false,
                "/path/to/file/with/title",
                null,
                "[Unknown Artist]",
                null,
                null,
                null,
                null,
                null,
                null,
                "mp3",
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );


        /*
        When
         */
        String resultFileName = new FileNameBuilder()
                .media(media)
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
