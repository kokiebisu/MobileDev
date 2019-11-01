package ca.bcit.lab09;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Todo> {
    private Activity context;
    private List<Todo> todoList;

    public TaskListAdapter(Activity context, List<Todo> todoList) {
        super(context, R.layout.list_layout, todoList );
        this.context = context;
        this.todoList = todoList;
    }

    public TaskListAdapter(Context context, int resource, List<Todo> objects, Activity context1, List<Todo> todoList) {
        super(context, resource, objects);
        this.context = context1;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvTask = listViewItem.findViewById(R.id.textViewTask);
        TextView tvWho = listViewItem.findViewById(R.id.textViewWho);
        TextView tvDueDate = listViewItem.findViewById(R.id.textViewDueDate);
        TextView tvCompleted = listViewItem.findViewById(R.id.textViewComplete);

        Todo todo = todoList.get(position);
        tvTask.setText(todo.getTask());
        tvWho.setText(todo.getWho());
        tvDueDate.setText(todo.getDueDate());
        if (todo.getDone()) {
            tvCompleted.setText("Complete");
        } else {
            tvCompleted.setText("Incomplete");
        }
        return listViewItem;
    }
}
