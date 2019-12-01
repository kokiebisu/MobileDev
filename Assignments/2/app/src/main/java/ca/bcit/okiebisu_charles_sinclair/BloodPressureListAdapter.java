package ca.bcit.okiebisu_charles_sinclair;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.List;

public class BloodPressureListAdapter extends ArrayAdapter<BloodPressure> {
    private Activity context;
    private List<BloodPressure> bloodPressureList;

    public BloodPressureListAdapter(Activity context, List<BloodPressure> bloodPressureList) {
        super(context, R.layout.list_layout, bloodPressureList);
        this.context = context;
        this.bloodPressureList = bloodPressureList;
    }

    public BloodPressureListAdapter(Context context, int resource, List<BloodPressure> objects, Activity context1, List<BloodPressure> studentList) {
        super(context, resource, objects);
        this.context = context1;
        this.bloodPressureList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvUserId = listViewItem.findViewById(R.id.textViewUserId);
        TextView tvSystolic = listViewItem.findViewById(R.id.textViewSystolicReading);
        TextView tvDiastolic = listViewItem.findViewById(R.id.textViewDiastolicReading);
        TextView tvCondition = listViewItem.findViewById(R.id.textViewCondition);
        TextView tvDate = listViewItem.findViewById(R.id.textViewDateTime);

        BloodPressure bloodPressure = bloodPressureList.get(position);
        tvUserId.setText("Name: " + bloodPressure.getUserId());
        tvSystolic.setText("Systolic Value: " + bloodPressure.getSystolicReading().toString());
        tvDiastolic.setText("Diastolic Value: " + bloodPressure.getDiastolicReading().toString());
        tvCondition.setText("Condition: " + bloodPressure.getCondition());
        tvDate.setText(bloodPressure.getReadingDate() + " " + bloodPressure.getReadingTime());

        return listViewItem;
    }



}
