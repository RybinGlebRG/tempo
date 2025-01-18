package com.cappielloantonio.tempo.service.export;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.cappielloantonio.tempo.service.export.exceptions.PermissionsNotGrantedException;

import java.util.Objects;

/**
 * <p>Validations for export.</p>
 */
public class Validator {

    public void validatePermissions(@NonNull Context context){
        Objects.requireNonNull(context);

        // Checking write permissions for Android 10 and below.
        boolean isWriteNotGranted = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && isWriteNotGranted) {
            // TODO: i18n?
            throw new PermissionsNotGrantedException("Write permissions not granted");
        }
    }
}
