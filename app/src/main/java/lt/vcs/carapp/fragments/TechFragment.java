package lt.vcs.carapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.R;
import lt.vcs.carapp.model.Car;
import lt.vcs.carapp.model.Image;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TechFragment extends Fragment {
    private static final int RESULT_OK = -1;
    final int RESULT_LOAD_IMAGE = 1;
    View view;
    EditText techNuo;
    TextView techIki;
    int techLaikotarpis, idAuto;
    DatabaseHandler databaseHandler;
    String techNuo1, techIki1 = null;
    ImageView imageView;
    Button addPhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tech, container, false);
        init();
        setAllFields();

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });


        try {
            Image image = databaseHandler.getImage(2, idAuto);
            image.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image.getImage(), 0, image.getImage().length);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (idAuto > 0) {
            updateTech(idAuto);
        } else {
            Toast.makeText(getActivity(), "Pridėkite transporto priemonę.", Toast.LENGTH_LONG).show();
        }

        try {
            databaseHandler.setImage(2, idAuto, imageViewToByte(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
        techNuo = view.findViewById(R.id.techFromDate);
        techIki = view.findViewById(R.id.techToDate);
        imageView = view.findViewById(R.id.imageViewTech);
        addPhoto = view.findViewById(R.id.addPhotoTech);
        idAuto = MainFragment.idAuto;
    }

    private void setAllFields() {
        Car car = databaseHandler.getCarById(idAuto);
        techNuo.setText(car.getTechFrom());
        techIki.setText(car.getTechTo());
    }

    private void updateTech(int id) {
        techLaikotarpis = 24;

        if (!isEmptyString(techNuo)) {
            techNuo1 = techNuo.getText().toString();
            techIki1 = techIki.getText().toString();
            databaseHandler.updateTech(techNuo1, techIki1, techLaikotarpis, id);
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isEmptyString(EditText toTest) {
        boolean isEmptyString = toTest.getText().toString().matches("");
        return isEmptyString;
    }
}
