package lt.vcs.carapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lt.vcs.carapp.additional.AddNewAuto;
import lt.vcs.carapp.DatabaseHandler;
import lt.vcs.carapp.DateObject;
import lt.vcs.carapp.additional.DeleteFragment;
import lt.vcs.carapp.ObjectInfoListAdapter;
import lt.vcs.carapp.R;
import lt.vcs.carapp.model.Maintenance;
import lt.vcs.carapp.model.Service;

public class MainFragment extends Fragment {
    public static int idAuto;
    public static long id;
    View view;
    DatabaseHandler databaseHandler;
    ListView listView;
    FloatingActionButton addNewButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        init();

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAuto addNewAuto = new AddNewAuto();
                addNewAuto.show(getFragmentManager(), "AddNewAuto");
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            idAuto = bundle.getInt("spinnerItemId");
        }

        List<Service> serviceList = databaseHandler.getServiceListById(idAuto);
        List<Maintenance> maintenanceList = databaseHandler.getMaintenanceListById(idAuto);
        List<DateObject> objectList = new ArrayList<>();
        objectList.addAll(maintenanceList);
        objectList.addAll(serviceList);

        ObjectInfoListAdapter adapter = new ObjectInfoListAdapter(getContext(), R.layout.adapter_view_layout, objectList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.getItemAtPosition(position) instanceof Service) {
                    Service service = (Service) listView.getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    ServiceFragment serviceFragment = new ServiceFragment();

                    bundle.putString(serviceFragment.titleString, service.getTitle());
                    bundle.putString(serviceFragment.dateString, service.getDate());
                    bundle.putString(serviceFragment.commentString, service.getComment());
                    bundle.putInt(serviceFragment.idString, service.getId());

                    serviceFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_fragment, serviceFragment).commit();
                } else {
                    Maintenance maintenance = (Maintenance) listView.getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    MaintenanceFragment maintenanceFragment = new MaintenanceFragment();

                    bundle.putString(maintenanceFragment.dateString, maintenance.getDate());
                    bundle.putString(maintenanceFragment.commentString, maintenance.getComment());
                    bundle.putString(maintenanceFragment.jobsString, maintenance.getJobId());
                    bundle.putInt(maintenanceFragment.idString, maintenance.getId());
                    bundle.putInt(maintenanceFragment.mileageString, maintenance.getMileage());

                    maintenanceFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container_fragment, maintenanceFragment).addToBackStack(null).commit();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                DeleteFragment deleteFragment = new DeleteFragment();

                if (listView.getItemAtPosition(position) instanceof Service) {
                    Service service = (Service) listView.getItemAtPosition(position);
                    bundle.putInt("idListView", service.getId());
                } else {
                    Maintenance maintenance = (Maintenance) listView.getItemAtPosition(position);
                    bundle.putInt("idListView", maintenance.getId());
                }

                bundle.putString("idListViewString", "service");
                deleteFragment.setArguments(bundle);
                deleteFragment.show(getFragmentManager(), "deleteService");
                return true;
            }
        });
        return view;
    }

    private void init() {
        addNewButton = view.findViewById(R.id.addNewAuto);
        listView = view.findViewById(R.id.listView);
        databaseHandler = new DatabaseHandler(getActivity());
    }
}
