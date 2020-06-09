package lt.vcs.carapp.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ServiceFragment extends Fragment {
    public final static String titleString = "title";
    public final static String dateString = "date";
    public final static String commentString = "comment";
    public final static String idString = "id";
    View view;
    EditText serviceTitle, serviceCom, serviceDate;
    DatabaseHandler databaseHandler;
    int idUpdate, idNew;
    boolean isNewService = true;
    String serviceTitle1, serviceCom1, serviceDate1 = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);
        init();

        serviceTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        serviceCom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            serviceTitle.setText(bundle.getString(titleString));
            serviceDate.setText(bundle.getString(dateString));
            serviceCom.setText(bundle.getString(commentString));
            idUpdate = bundle.getInt(idString);
            isNewService = false;
        }
        return view;
    }

    private void init() {
        databaseHandler = new DatabaseHandler(getActivity());
        serviceTitle = view.findViewById(R.id.serviceName);
        serviceCom = view.findViewById(R.id.serviceCom);
        serviceDate = view.findViewById(R.id.serviceDate);
        idNew = MainFragment.idAuto;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (idNew > 0) {
            setUpdateService();
        } else {
            Toast.makeText(getActivity(), "Pridėkite transporto priemonę.", Toast.LENGTH_LONG).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setUpdateService() {
        if (!serviceTitle.getText().toString().matches("")) {
            serviceTitle1 = serviceTitle.getText().toString();
        }

        if (!serviceCom.getText().toString().matches("")) {
            serviceCom1 = serviceCom.getText().toString();
        }

        if (!serviceDate.getText().toString().matches("")) {
            serviceDate1 = serviceDate.getText().toString();
        }

        if (!serviceTitle.getText().toString().matches("") && !serviceCom.getText().toString().matches("")
                && !serviceDate.getText().toString().matches("")) {
            if (isNewService) {
                databaseHandler.setService(serviceTitle1, serviceCom1, serviceDate1, idNew);
            } else {
                databaseHandler.updateService(serviceTitle1, serviceCom1, serviceDate1, idUpdate);
            }
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }
    }
}
