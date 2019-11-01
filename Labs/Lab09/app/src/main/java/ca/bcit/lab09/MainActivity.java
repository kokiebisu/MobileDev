package ca.bcit.lab09;

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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextTask;
    EditText editTextWho;
    EditText editTextDueDate;
    RadioButton complete;
    RadioButton incomplete;
    Button buttonAddTodo;
    Boolean completeState;

    DatabaseReference databaseTodo;

    ListView lvTasks;
    List<Todo> todoList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseTodo = FirebaseDatabase.getInstance().getReference("tasks");

        editTextTask = findViewById(R.id.editTextTask);
        editTextWho = findViewById(R.id.editTextWho);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        buttonAddTodo = findViewById(R.id.buttonAddTodo);
        complete = findViewById(R.id.radio_complete);
        incomplete = findViewById(R.id.radio_incomplete);

        lvTasks = findViewById(R.id.lvTasks);
        todoList = new ArrayList<Todo>();

        buttonAddTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });

        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoList.get(position);

                showUpdateDialog(todo.getTodoId(),todo.getTask(),
                        todo.getWho(), todo.getDueDate(), todo.getDone());

                return false;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTodo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               todoList.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Todo todo = studentSnapshot.getValue(Todo.class);
                    todoList.add(todo);
                }

                TaskListAdapter adapter = new TaskListAdapter(MainActivity.this, todoList);
                lvTasks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void updateTodo(String id, String task, String who, String dueDate, Boolean completed) {
        DatabaseReference dbRef = databaseTodo.child(id);

        Todo todo = new Todo(id, task, who, dueDate, completed);
        Task setValueTask = dbRef.setValue(todo);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Student Updated.",Toast.LENGTH_LONG).show();
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

    private void deleteTodo(String id) {
        DatabaseReference dbRef = databaseTodo.child(id);

        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Task Deleted.",Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void showUpdateDialog(final String todoId,String task, String who, String dueDate, Boolean completed) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTask = dialogView.findViewById(R.id.editTextTask);
        editTextTask.setText(task);

        final EditText editTextWho = dialogView.findViewById(R.id.editTextWho);
        editTextWho.setText(who);

        final EditText editTextDueDate = dialogView.findViewById(R.id.editTextDueDate);
        editTextDueDate.setText(dueDate);

        final Button btnUpdate = dialogView.findViewById(R.id.buttonUpdate);

        dialogBuilder.setTitle("Update Task: " + task);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                String who = editTextWho.getText().toString().trim();
                String dueDate = editTextDueDate.getText().toString().trim();

                if (TextUtils.isEmpty(task)) {
                    editTextTask.setError("Task is required");
                    return;
                } else if (TextUtils.isEmpty(who)) {
                    editTextWho.setError("Person is required");
                    return;
                } else if (TextUtils.isEmpty(dueDate)) {
                    editTextDueDate.setError("Due date is required");
                } else {

                }

                updateTodo(todoId, task, who, dueDate, completeState);

                alertDialog.dismiss();
            }
        });

        final Button btnDelete = dialogView.findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTodo(todoId);

                alertDialog.dismiss();
            }
        });

    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_complete:
                if (checked)
                    completeState = true;
                    break;
            case R.id.radio_incomplete:
                if (checked)
                    completeState = false;
                    break;
        }
    }

    private void addTodo() {
        String task = editTextTask.getText().toString().trim();
        String who = editTextWho.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(task)) {
            Toast.makeText(this, "You must enter a Task.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(who)) {
            Toast.makeText(this, "You must enter who you are.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "You must enter due date", Toast.LENGTH_LONG).show();
            return;
        }

        String todoId = databaseTodo.push().getKey();
        Todo todo = new Todo(todoId, task, who, dueDate, completeState);

        Task setValueTask = databaseTodo.child(todoId).setValue(todo);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this, "Task added.", Toast.LENGTH_LONG).show();

                editTextTask.setText("");
                editTextWho.setText("");
                editTextDueDate.setText("");

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
    }}

