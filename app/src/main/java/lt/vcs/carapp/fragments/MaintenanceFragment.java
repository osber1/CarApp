package lt.vcs.carapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.R;

public class MaintenanceFragment extends Fragment {
    public final static String dateString = "date";
    public final static String commentString = "comment";
    public final static String idString = "id";
    public final static String mileageString = "mileage";
    public final static String jobsString = "jobs";
    public ArrayList<String> jobsList = new ArrayList();
    ArrayList<String> jobsArray = new ArrayList();
    String jobsSet, jobsGet;
    EditText maintenanceCom, date, mileage;
    DatabaseHandler databaseHandler;
    boolean isNewMaintenance = true;
    int id, idNew;
    CheckBox id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
        init(view);

        maintenanceCom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        mileage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        bundle = getArguments();
        if (bundle != null) {
            date.setText(bundle.getString(dateString));
            maintenanceCom.setText(bundle.getString(commentString));
            id = bundle.getInt(idString);
            mileage.setText(String.valueOf(bundle.getInt(mileageString)));
            isNewMaintenance = false;
            jobsGet = bundle.getString(jobsString);
            String str[] = jobsGet.split(",");
            jobsArray = new ArrayList<>(Arrays.asList(str));

            for (String s : jobsArray) {

                if (s.equals("1")) {
                    id1.setChecked(!id1.isChecked());
                }

                if (s.equals("2")) {
                    id2.setChecked(!id2.isChecked());
                }

                if (s.equals("3")) {
                    id3.setChecked(!id3.isChecked());
                }

                if (s.equals("4")) {
                    id4.setChecked(!id4.isChecked());
                }
                if (s.equals("5")) {
                    id5.setChecked(!id5.isChecked());
                }

                if (s.equals("6")) {
                    id6.setChecked(!id6.isChecked());
                }
                if (s.equals("7")) {
                    id7.setChecked(!id7.isChecked());
                }

                if (s.equals("8")) {
                    id8.setChecked(!id8.isChecked());
                }
                if (s.equals("9")) {
                    id9.setChecked(!id9.isChecked());
                }

                if (s.equals("10")) {
                    id10.setChecked(!id10.isChecked());
                }
                if (s.equals("11")) {
                    id11.setChecked(!id11.isChecked());
                }

                if (s.equals("12")) {
                    id12.setChecked(!id12.isChecked());
                }
            }

        }
        return view;
    }

    private void init(View view) {
        databaseHandler = new DatabaseHandler(getActivity());
        date = view.findViewById(R.id.maintenanceDate);
        maintenanceCom = view.findViewById(R.id.maintenanceCom);
        mileage = view.findViewById(R.id.maintenanceMileage);
        idNew = MainFragment.idAuto;
        id1 = view.findViewById(R.id.id_1);
        id2 = view.findViewById(R.id.id_2);
        id3 = view.findViewById(R.id.id_3);
        id4 = view.findViewById(R.id.id_4);
        id5 = view.findViewById(R.id.id_5);
        id6 = view.findViewById(R.id.id_6);
        id7 = view.findViewById(R.id.id_7);
        id8 = view.findViewById(R.id.id_8);
        id9 = view.findViewById(R.id.id_9);
        id10 = view.findViewById(R.id.id_10);
        id11 = view.findViewById(R.id.id_11);
        id12 = view.findViewById(R.id.id_12);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (id1.isChecked()) {
            jobsList.add("1");
        } else {
            jobsList.remove("1");
        }

        if (id2.isChecked()) {
            jobsList.add("2");
        } else {
            jobsList.remove("2");
        }

        if (id3.isChecked()) {
            jobsList.add("3");
        } else {
            jobsList.remove("3");
        }
        if (id4.isChecked()) {
            jobsList.add("4");
        } else {
            jobsList.remove("4");
        }

        if (id5.isChecked()) {
            jobsList.add("5");
        } else {
            jobsList.remove("5");
        }

        if (id6.isChecked()) {
            jobsList.add("6");
        } else {
            jobsList.remove("6");
        }

        if (id7.isChecked()) {
            jobsList.add("7");
        } else {
            jobsList.remove("7");
        }

        if (id8.isChecked()) {
            jobsList.add("8");
        } else {
            jobsList.remove("8");
        }

        if (id9.isChecked()) {
            jobsList.add("9");
        } else {
            jobsList.remove("9");
        }

        if (id10.isChecked()) {
            jobsList.add("10");
        } else {
            jobsList.remove("10");
        }

        if (id11.isChecked()) {
            jobsList.add("11");
        } else {
            jobsList.remove("11");
        }

        if (id12.isChecked()) {
            jobsList.add("12");
        } else {
            jobsList.remove("12");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : jobsList) {
            stringBuilder.append(s + ",");
        }
        jobsSet = stringBuilder.toString();

        if (idNew > 0) {
            if (isNewMaintenance) {
                setMaintenance(idNew);
            } else {
                updateMaintenance(id);
            }
        } else {
            Toast.makeText(getActivity(), "Pridėkite transporto priemonę.", Toast.LENGTH_LONG).show();
        }

    }

    private void setMaintenance(int id) {
        String maintenanceCom1 = null;
        String date1 = null;
        int mileage1 = 0;

        if (!maintenanceCom.getText().toString().matches("")) {
            maintenanceCom1 = maintenanceCom.getText().toString();
        }

        if (!date.getText().toString().matches("")) {
            date1 = date.getText().toString();
        }

        if (!mileage.getText().toString().matches("")) {
            mileage1 = Integer.parseInt(mileage.getText().toString());
        }

        if (!maintenanceCom.getText().toString().matches("") && !date.getText().toString().matches("")) {
            databaseHandler.setMaintenance(maintenanceCom1, date1, mileage1, id, jobsSet);
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }
    }

    private void updateMaintenance(int id) {
        String maintenanceCom1 = null;
        String date1 = null;
        int mileage1 = 0;

        if (!isEmptyString(maintenanceCom) && !isEmptyString(date) && !isEmptyString(mileage)) {
            maintenanceCom1 = maintenanceCom.getText().toString();
            date1 = date.getText().toString();
            mileage1 = Integer.parseInt(mileage.getText().toString());
            databaseHandler.updateMaintenance(maintenanceCom1, date1, mileage1, id, jobsSet);
        } else {
            Toast.makeText(getActivity(), "Neišsaugota. Blogai įvesti duomenys.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isEmptyString(EditText toTest) {
        boolean isEmptyString = toTest.getText().toString().matches("");
        return isEmptyString;
    }
}
