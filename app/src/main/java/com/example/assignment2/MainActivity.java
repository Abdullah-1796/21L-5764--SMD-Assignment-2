package com.example.assignment2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout portrait, landscape;
    FragmentManager fragmentManager;
    NewTaskFrag newTaskFrag;
    ToDoListFrag toDoListFrag;
    View viewToDoList, viewNewTask;

    ArrayList<Task> tasks;

    ListView lvToDoList;
    FloatingActionButton btnAddNewTask;
    Button btnAddTasktoList;
    EditText etTaskName, etTaskDescription;

    //Fragment nt, tdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        init();

        if(portrait != null)
        {
            Toast.makeText(MainActivity.this, "You are in portrait mode", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().show(toDoListFrag).hide(newTaskFrag).commit();
        }
        else if(landscape != null)
        {
            Toast.makeText(MainActivity.this, "You are in landscape mode", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().show(toDoListFrag).hide(newTaskFrag).commit();
        }

        lvToDoList = viewToDoList.findViewById(R.id.lvToDoList);
        ToDoListAdaptor toDoListAdaptor = new ToDoListAdaptor(MainActivity.this,
                R.layout.single_task_design, tasks);
        lvToDoList.setAdapter(toDoListAdaptor);

        etTaskName = viewNewTask.findViewById(R.id.etTaskName);
        etTaskDescription = viewNewTask.findViewById(R.id.etTaskDescription);
        btnAddTasktoList = viewNewTask.findViewById(R.id.btnAddTasktoList);
        btnAddTasktoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etTaskName.getText().toString().trim();
                String description = etTaskDescription.getText().toString().trim();
                if(!name.isEmpty() && !description.isEmpty())
                {
                    Task t = new Task(name, description);
                    tasks.add(t);
                    toDoListAdaptor.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Task added in TODO List", Toast.LENGTH_SHORT).show();
                    fragmentManager.beginTransaction().show(toDoListFrag).hide(newTaskFrag).commit();
                    etTaskName.setText("");
                    etTaskDescription.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Both Fields must be filled",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().show(newTaskFrag).hide(toDoListFrag).commit();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void init()
    {
        portrait = findViewById(R.id.portrait);
        landscape = findViewById((R.id.landscape));
        fragmentManager = getSupportFragmentManager();
        toDoListFrag = (ToDoListFrag) fragmentManager.findFragmentById(R.id.toDoListFrag);
        newTaskFrag = (NewTaskFrag) fragmentManager.findFragmentById(R.id.newTaskFrag);
        viewToDoList = toDoListFrag.getView();
        viewNewTask = newTaskFrag.getView();

        btnAddNewTask = findViewById(R.id.btnAddNewTask);
        /*
        nt = fragmentManager.findFragmentById(R.id.newTaskFrag);
        tdl = fragmentManager.findFragmentById(R.id.toDoListFrag);

        viewToDoList = tdl.getView();
         */


        tasks = new ArrayList<>();
        tasks.add(new Task("FYP1", "Proposal"));
        tasks.add(new Task("FYP2", "Client Meeting"));
        tasks.add(new Task("FYP3", "Report"));
        tasks.add(new Task("FYP4", "Mid-Evaluation"));

        View decorView = getWindow().getDecorView();
        decorView.setOnApplyWindowInsetsListener((v, insets) -> {
            // Apply the insets to the layout
            Insets systemBarsInsets = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    systemBarsInsets = Insets.wrap(insets.getInsets(WindowInsets.Type.systemBars()));
                }
            }

            // Adjust the padding of the ConstraintLayout to avoid overlapping with status/navigation bars
            v.setPadding(0, systemBarsInsets.top, 0, systemBarsInsets.bottom);
            return insets;
        });

    }
}