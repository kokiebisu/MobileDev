package ca.bcit.okiebisu_charles_sinclair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView avgSystolic, avgDiastolic;
    TextView avgCondition;
    EditText editTextUserId;
    EditText editTextSystolicReading;
    EditText editTextDiastolicReading;
    Button buttonAdd;

    ListView lvBloodPressure;
    List<BloodPressure> bloodPressureList;

    DatabaseReference databaseBloodPressure;

    String[] conditions = {"Normal", "Elevated", "High Blood Pressure (stage 1)," +
            "High Blood Pressure (stage 2)", "Hypertensive Crisis", "Invalid Input"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseBloodPressure = FirebaseDatabase.getInstance().getReference("bloodpressure");

        avgSystolic = findViewById(R.id.avgSystolic);
        avgDiastolic = findViewById(R.id.avgDiastolic);
        avgCondition = findViewById(R.id.avgCondition);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextSystolicReading = findViewById(R.id.editTextSystolicReading);
        editTextDiastolicReading = findViewById(R.id.editTextDiastolicReading);
        buttonAdd = findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBloodPressure();
            }
        });

        lvBloodPressure = findViewById(R.id.lvBloodPressure);
        bloodPressureList = new ArrayList<BloodPressure>();
        lvBloodPressure.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BloodPressure reading = bloodPressureList.get(position);

                showUpdateDialog(reading.getFirebaseId(), reading.getUserId(),
                        reading.getReadingDate(),
                        reading.getReadingTime(),
                        reading.getSystolicReading(),
                        reading.getDiastolicReading(),
                        reading.getCondition());

                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseBloodPressure.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bloodPressureList.clear();
                for (DataSnapshot bloodPressureSnapshot : dataSnapshot.getChildren()) {
                    BloodPressure bloodPressure = bloodPressureSnapshot.getValue(BloodPressure.class);
                    bloodPressureList.add(bloodPressure);
                }

                BloodPressureListAdapter adapter = new BloodPressureListAdapter(MainActivity.this, bloodPressureList);
                lvBloodPressure.setAdapter(adapter);
                updateAverageReading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    private void addBloodPressure() {
        String userId = editTextUserId.getText().toString().trim();
        float systolicValue = Float.parseFloat(editTextSystolicReading.getText().toString().trim());
        float diastolicValue = Float.parseFloat(editTextDiastolicReading.getText().toString().trim());
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yy");
        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        String currentDate = dfDate.format(new Date());
        String currentTime = dfTime.format(new Date());
        final String condition;


        if (TextUtils.isEmpty(editTextSystolicReading.getText().toString().trim())) {
            Toast.makeText(this, "You must enter the Systolic value.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(editTextDiastolicReading.getText().toString().trim())) {
            Toast.makeText(this, "You must enter a last name.", Toast.LENGTH_LONG).show();
            return;
        }

        condition = validateCondition(systolicValue, diastolicValue);

        String firebaseId = databaseBloodPressure.push().getKey();
        BloodPressure bloodPressure = new BloodPressure(firebaseId, userId, currentDate, currentTime, systolicValue, diastolicValue, condition);

        Task setValueTask = databaseBloodPressure.child(firebaseId).setValue(bloodPressure);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                if (condition.equals("Hypertensive Crisis")) {
                    Toast.makeText(MainActivity.this,"Consult your doctor immediately!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,"Blood added.",Toast.LENGTH_LONG).show();
                }

                editTextSystolicReading.setText("");
                editTextDiastolicReading.setText("");
                updateAverageReading();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String validateCondition(float systolicValue, float diastolicValue) {
        if (systolicValue < 120 && diastolicValue >= 80 && diastolicValue < 180) {
            return "Normal";
        } else if (systolicValue < 129 && systolicValue >= 120 && diastolicValue < 80) {
            return "Elevated";
        } else if (systolicValue < 139 && systolicValue >= 130 || diastolicValue >= 80 && diastolicValue < 89) {
            return "High Blood Pressure (stage 1)";
        } else if (systolicValue >= 140 && systolicValue < 180 || diastolicValue >= 90 && diastolicValue < 120) {
            return "High Blood Pressure (stage 2)";
        } else if (systolicValue >= 180 || diastolicValue >= 120) {
            return "Hypertensive Crisis";
        } else {
            return "Invalid Input";
        }
    }

    private void updateReading(String firebaseId, String userId, String readingDate, String readingTime, float systolicReading, float diastolicReading, String condition) {
        DatabaseReference dbRef = databaseBloodPressure.child(userId);

        BloodPressure bloodPressure = new BloodPressure(firebaseId, userId, readingDate, readingTime, systolicReading, diastolicReading, validateCondition(systolicReading, diastolicReading));

        Task setValueTask = dbRef.setValue(bloodPressure);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Reading Updated.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(final String firebaseId, final String userId, final String readingDate, final String readingTime, float systolicReading, float diastolicReading, final String condition) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText updateDiastolic = dialogView.findViewById(R.id.updateDiastolicReading);
        updateDiastolic.setText(String.valueOf(diastolicReading));

        final EditText updateSystolic= dialogView.findViewById(R.id.updateSystolicReading);
        updateSystolic.setText(String.valueOf(systolicReading));

        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update Reading");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float diastolic = Float.parseFloat(updateDiastolic.getText().toString().trim());
                Float systolic = Float.parseFloat(updateSystolic.getText().toString().trim());

                updateReading(firebaseId, userId, readingDate, readingTime, systolic, diastolic, condition);

                alertDialog.dismiss();
            }
        });
        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReading(userId);

                alertDialog.dismiss();
            }
        });

    }

    private void deleteReading(String id) {
        DatabaseReference dbRef = databaseBloodPressure.child(id);

        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Reading Deleted.",Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



  /*  public void readingTouched(final View v){
        AlertDialog.Builder viewOptions = new AlertDialog.Builder(this);
        viewOptions.setMessage("What would you like to do with this view?");
        viewOptions.setCancelable(true);

        viewOptions.setPositiveButton(
                "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //lvBloodPressure.removeView(v); error: removeView(v) is not supported in AdapterView
                    }
                });

        viewOptions.setNeutralButton(
                "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //update the view
                    }
                });

        viewOptions.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //close the window
                    }
                });

        AlertDialog alert = viewOptions.create();
        alert.show();
    }*/

  private void updateAverageReading(){
      double systolicAvg = 0, diastolicAvg = 0;
      int[] conditionCount = new int[conditions.length];
      for(int i = 0; i < conditions.length; i++)
          conditionCount[i] = 0;

      for(BloodPressure b : bloodPressureList){
          systolicAvg += b.getSystolicReading();
          diastolicAvg += b.getDiastolicReading();

          for(int i = 0; i < conditions.length; i++)
              if(b.getCondition().equals(conditions[i]))
                  conditionCount[i]++;
      }

      int listLength = bloodPressureList.size();
      systolicAvg /= listLength;
      diastolicAvg /= listLength;

      //if there is more than one average condition, the first average condition is displayed
      int maxIndex = 0;
      for(int i = 1; i < conditionCount.length; i++)
          if(conditionCount[i] > conditionCount[maxIndex])
              maxIndex = i;

      avgSystolic.setText("Average systolic value: " + systolicAvg);
      avgDiastolic.setText("Average diastolic value: " + diastolicAvg);
      avgCondition.setText("Average condition: " + conditions[maxIndex]);
  }

}
