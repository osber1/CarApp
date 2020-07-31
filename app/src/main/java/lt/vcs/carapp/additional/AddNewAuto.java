package lt.vcs.carapp.additional;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.MainActivity;
import lt.vcs.carapp.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddNewAuto extends DialogFragment {
    View view;
    EditText title, about;
    TextView actionOk, actionCancel;
    DatabaseHandler databaseHandler;
    String title1, about1 = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_new, container, false);
        init();

        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        about.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCar();
                getDialog().dismiss();

                restartActivity();
            }
        });
        return view;
    }

    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
        title = view.findViewById(R.id.inputTitle);
        about = view.findViewById(R.id.inputAbout);
        actionOk = view.findViewById(R.id.action_ok);
        actionCancel = view.findViewById(R.id.action_cancel);
    }

    private void restartActivity() {
        Intent refresh = new Intent((getContext()), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();
    }

    private void addNewCar() {
        if (!isEmptyString(title) && !isEmptyString(about)) {
            title1 = title.getText().toString();
            about1 = about.getText().toString();
            databaseHandler.createNewCar(title1, about1);
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isEmptyString(EditText toTest) {
        boolean isEmptyString = toTest.getText().toString().matches("");
        return isEmptyString;
    }
}
