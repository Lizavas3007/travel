package com.example.travel;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.InputStream;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CreateUserActivity extends AppCompatActivity {
    private ImageView imageView;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new GetContent(),
        new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            // Handle the returned Uri
            try {
                final InputStream imageStream = getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        
        //Связываемся с нашим ImageView:
        imageView = (ImageView)findViewById(R.id.imageView);

        //Связываемся с нашей кнопкой Button:
        Button PickImage = (Button) findViewById(R.id.button);
        //Настраиваем для нее обработчик нажатий OnClickListener:
        PickImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass in the mime type you want to let the user select
                // as the input
                mGetContent.launch("image/*");
            }
        });
        
    }
}