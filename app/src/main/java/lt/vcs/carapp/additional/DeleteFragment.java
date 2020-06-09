package lt.vcs.carapp.additional;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.MainActivity;
import lt.vcs.carapp.R;

public class DeleteFragment extends DialogFragment {
    View view;
    TextView deleteYes, deleteNo;
    DatabaseHandler databaseHandler;
    int listId;
    String whereFrom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.delete_layout, container, false);
        databaseHandler = new DatabaseHandler(getActivity());
        deleteYes = view.findViewById(R.id.deleteYes);
        deleteNo = view.findViewById(R.id.deleteNo);

        Bundle bundle = getArguments();
        if (bundle != null) {
            listId = bundle.getInt("idListView");
            whereFrom = bundle.getString("idListViewString");
        }

        deleteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        deleteYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whereFrom.equals("service")) {
                    databaseHandler.deleteService(listId);
                } else {
                    databaseHandler.deleteMaintenance(listId);
                }

                getDialog().dismiss();
                Intent refresh = new Intent((getContext()), MainActivity.class);
                startActivity(refresh);
                getActivity().finish();
            }
        });
        return view;
    }
}
