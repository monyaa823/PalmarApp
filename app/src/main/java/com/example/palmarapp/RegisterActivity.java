package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button btnAddStudent, btnAddTeacher, btnAddSubject, btnAddSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);

        btnAddStudent.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, AddStudentActivity.class))
        );

        btnAddTeacher.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, AddTeacherActivity.class))
        );

        btnAddSubject.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, AddSubjectActivity.class))
        );

        btnAddSchedule.setOnClickListener(v ->
            startActivity(new Intent(RegisterActivity.this, AddScheduleActivity.class))
        );
    }
}
