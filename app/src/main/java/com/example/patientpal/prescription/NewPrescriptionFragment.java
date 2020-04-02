package com.example.patientpal.prescription;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.patientpal.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class NewPrescriptionFragment extends Fragment{

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;

    private ImageButton takePictureBtn, choosePicFromGalleryBtn, retryButton;
    private ImageView prescriptionImage;
    private AutoCompleteTextView autoText;

    String currentPhotoPath;

    private static final String[] pharmaciesAvailable = new String[]{ "Joes Pharmacy", "McElroys Pharmacy", "Your Local Pharmacies", "Bobs Pharmacy", "Boots", "Jockos Pharmacy", "Macho Meds Pharm", "Snake oil supplies"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_prescription_frag_layout, container, false);

        //instantiation
        takePictureBtn = v.findViewById(R.id.ImageButtonTakePhoto);
        choosePicFromGalleryBtn = v.findViewById(R.id.ImageButtonChooseFromGallery);
        prescriptionImage = v.findViewById(R.id.prescription_photo_area);
        autoText = v.findViewById(R.id.autoCompletePharmacy);
        retryButton = v.findViewById(R.id.retryBtn);
        retryButton.setVisibility(View.GONE);

        //Probably be of pharmacy objects
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, pharmaciesAvailable);
        autoText.setAdapter(adapter);


        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });

        choosePicFromGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionImage.setImageDrawable(null);
                choosePicFromGalleryBtn.setVisibility(View.VISIBLE);
                takePictureBtn.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
            }
        });
        return v;
    }


    ////Methods
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }else {
            //Permission is already granted
            System.out.println("Calling dispatch Picture Intent");
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                System.out.println("Calling dispatch Picture Intent");
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(getContext(), "Camera access needed to take picture", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                System.out.println("Current photo path: " + currentPhotoPath);
                takePictureBtn.setVisibility(View.GONE);
                choosePicFromGalleryBtn.setVisibility(View.GONE);
                File f = new File(currentPhotoPath);
                prescriptionImage.setImageURI(Uri.fromFile(f));
                retryButton.setVisibility(View.VISIBLE);
            }
        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {

                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "."+getFileExtension(contentUri);
               
                prescriptionImage.setImageURI(contentUri);

                takePictureBtn.setVisibility(View.GONE);
                choosePicFromGalleryBtn.setVisibility(View.GONE);
            }
        }
    }

    private String getFileExtension(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        System.out.println("create image file mthd created");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        System.out.println("Image absolute path " + image.getAbsolutePath());
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        System.out.println("Dispatch Take picture Intent called");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


}
