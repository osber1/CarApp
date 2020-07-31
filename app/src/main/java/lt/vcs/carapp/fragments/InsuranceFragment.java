package lt.vcs.carapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Date;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.R;
import lt.vcs.carapp.model.Car;
import lt.vcs.carapp.model.Image;

@RequiresApi(api = Build.VERSION_CODES.N)
public class InsuranceFragment extends Fragment {

    private static final int RESULT_OK = -1;
    final int RESULT_LOAD_IMAGE = 1;
    View view;
    EditText period, company, price, dateFrom;
    TextView dateTo;
    DatabaseHandler databaseHandler;
    int idAuto;
    ImageView imageView;
    Button addPhoto;

    public static Date convertString2Date(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insurance, container, false);
        init();
        setAllFields();

        period.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                dateInsurance();
            }
        });

        company.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        period.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        });

        try {
            Image image = databaseHandler.getImage(1, idAuto);
            image.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image.getImage(), 0, image.getImage().length);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setAllFields() {
        Car car = databaseHandler.getCarById(idAuto);
        period.setText(String.valueOf(car.getInsurancePeriod()));
        company.setText(car.getInsuranceCompany());
        dateFrom.setText(car.getInsuranceFrom());
        dateTo.setText(car.getInsuranceTo());
        price.setText(String.valueOf(car.getInsurancePrice()));
    }

    private void init() {
        period = view.findViewById(R.id.insuranceTimeInterval);
        company = view.findViewById(R.id.insuranceCompany);
        price = view.findViewById(R.id.insurancePrice);
        dateFrom = view.findViewById(R.id.insuranceFromDate);
        dateTo = view.findViewById(R.id.insuranceToDate);
        imageView = view.findViewById(R.id.imageViewInsurance);
        addPhoto = view.findViewById(R.id.addPhotoInsurance);
        databaseHandler = new DatabaseHandler(getActivity());
        idAuto = MainFragment.idAuto;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (idAuto > 0) {
            updateInsurance(idAuto);
        } else {
            Toast.makeText(getActivity(), "Pridėkite transporto priemonę.", Toast.LENGTH_LONG).show();
        }

        try {
            databaseHandler.setImage(1, idAuto, imageViewToByte(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dateInsurance() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int timePeriod;
        if (!period.getText().toString().matches("")) {
            timePeriod = Integer.parseInt(period.getText().toString());
        } else timePeriod = 0;

        Date currentDate;

        if (!dateFrom.getText().toString().matches("")) {
            currentDate = convertString2Date(dateFrom.getText().toString());
            String insuranceFromString = sdf.format(currentDate);

            Date date = convertString2Date(insuranceFromString);
            Date date1 = addMonth(date, timePeriod);

            String insuranceToString = sdf.format(date1);
            dateTo.setText(insuranceToString);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void updateInsurance(int id) {
        String company1 = null;
        String dateFrom1 = null;
        String dateTo1 = null;
        int period1 = 0;
        int price1 = 0;

        if (!isEmptyString(company) && !isEmptyString(dateFrom) && !isEmptyString(period) && !isEmptyString(price)) {
            company1 = company.getText().toString();
            dateFrom1 = dateFrom.getText().toString();
            dateTo1 = dateTo.getText().toString();
            period1 = Integer.parseInt(period.getText().toString());
            price1 = Integer.parseInt(price.getText().toString());
            databaseHandler.updateInsurance(company1, price1, period1, dateFrom1, dateTo1, id);
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isEmptyString(EditText toTest) {
        boolean isEmptyString = toTest.getText().toString().matches("");
        return isEmptyString;
    }
}