package com.orangecoastcollege.cs273.vnguyen678.petprotecter;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class PetListActivity extends AppCompatActivity {
    private ImageView petImageView;

    // this member varible stores the URI to whatever image has been stored
    // Default: none.png in drawable
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);
        petImageView = (ImageView)findViewById(R.id.petImageView);
        //this construct a full uri to any resource(id, drawable, color, etc...)
        imageUri = getUriToResource(this, R.drawable.none);
        //set the image URI of the imageView in code
        petImageView.setImageURI(imageUri);
    }

    public void selectPetImage(View view)
    {
        ArrayList<String> permList = new ArrayList<>();

        // Start by check if we have permiston for the cam;
        int cameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(cameraPerm != getPackageManager().PERMISSION_GRANTED)
            permList.add(Manifest.permission.CAMERA);
        int writeExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternal != getPackageManager().PERMISSION_GRANTED)
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExternal != getPackageManager().PERMISSION_GRANTED)
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        // if list have item (size greater than 0) get request
        {
            if(permList.size() > 0)
            {
                // convert arraylist to just a array of string
                String[] perm = new String[permList.size()];
                // request the permision
                // we have no controll over this part
                int requestCode = 100;
                ActivityCompat.requestPermissions(this, permList.toArray(perm), requestCode);

            }
        }
    }

    /**
     *  Get uri to any resource type within an Android Studio project. Method public static
     *  to allow other class to use it as a helper fuction.
     *
     * @param context the current context
     * @param resId   the resource identifier of the drawable
     * @return Uri to resource by given id
     * @throws Resources.NotFoundException if the given resource id does not exist.
     */
    public static Uri getUriToResource(@NonNull Context context, @AnyRes int resId)
            throws Resources.NotFoundException
    {
        /** Re*/
        Resources res = context.getResources();
        /** Re*/
        return  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + res.getResourceName(resId)
                + '/'   + res.getResourceTypeName(resId)
                + '/'   + res.getResourceEntryName(resId));
    }
}
