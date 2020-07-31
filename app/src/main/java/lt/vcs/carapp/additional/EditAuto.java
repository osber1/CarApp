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
public class EditAuto extends DialogFragment {
    View view;
    EditText title, about;
    TextView actionOk, actionCancel;
    DatabaseHandler databaseHandler;
    int idAuto;
    String autoName, autoAbout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_new, container, false);
        databaseHandler = new DatabaseHandler(getActivity());
        title = view.findViewById(R.id.inputTitle);
        about = view.findViewById(R.id.inputAbout);
        actionOk = view.findViewById(R.id.action_ok);
        actionCancel = view.findViewById(R.id.action_cancel);

        Bundle bundle = getArguments();
        if (bundle != null) {
            idAuto = bundle.getInt("spinnerItemId");
            autoName = bundle.getString("autoName");
            autoAbout = bundle.getString("autoAbout");
        }

        title.setText(autoName);
        about.setText(autoAbout);

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
                if (idAuto > 0) {
                    updateCarInfo(idAuto);
                    getDialog().dismiss();
                    Intent refresh = new Intent((getContext()), MainActivity.class);
                    startActivity(refresh);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Nerasta transporto priemonė.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void updateCarInfo(int id) {
        String title1 = null;
        String about1 = null;

        if (!isEmptyString(title) && !!isEmptyString(about)) {
            title1 = title.getText().toString();
            about1 = about.getText().toString();
            databaseHandler.updateCarInfo(title1, about1, id);
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
