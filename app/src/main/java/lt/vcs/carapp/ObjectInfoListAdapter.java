package lt.vcs.carapp;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.List;

import lt.vcs.carapp.model.Maintenance;
import lt.vcs.carapp.model.Service;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ObjectInfoListAdapter extends ArrayAdapter<DateObject> {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    int resource;
    private Context context;

    public ObjectInfoListAdapter(Context context, int adapter_view_layout, List<DateObject> objectList) {
        super(context, adapter_view_layout, objectList);
        this.context = context;
        this.resource = adapter_view_layout;
        Collections.sort(objectList, new DateComparator());

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Object o = (Object) getItem(position);
        String title = null;
        String date = null;

        if (o instanceof Service) {
            title = ((Service) getItem(position)).getTitle();
            date = ((Service) getItem(position)).getDate();
        } else {
            title = "Planinis aptarnavimas";
            date = ((Maintenance) getItem(position)).getDate();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView tvTitle = convertView.findViewById(R.id.adapter_title);
        TextView tvDate = convertView.findViewById(R.id.adapter_date);


        tvTitle.setText(title);
        tvDate.setText(date);

        return convertView;
    }
}
