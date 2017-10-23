package com.damithamarasinghe.oeidecisiontool.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.damithamarasinghe.oeidecisiontool.R;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spProject;
    private Button btnVote, btnCriteria, btnCost, btnResults;
    private String strProjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        spProject = (Spinner) findViewById(R.id.spProjects);
        btnVote  = (Button)findViewById(R.id.btn_project_voting);
        btnVote.setOnClickListener(this);
        btnCriteria  = (Button)findViewById(R.id.btn_create_criteria);
        btnCriteria.setOnClickListener(this);
        btnCost  = (Button)findViewById(R.id.btn_assign_cost);
        btnCost.setOnClickListener(this);
        btnResults  = (Button)findViewById(R.id.btn_result);
        btnResults.setOnClickListener(this);




        spProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int btnID = v.getId();
        switch (btnID){
            case R.id.btn_project_voting:
                Intent vote = new Intent(SelectionActivity.this, VotingActivity.class);
                startActivity(vote);
                break;
            case R.id.btn_create_criteria:
                Intent  criteria= new Intent(SelectionActivity.this, CriteriaActivity.class);
                startActivity(criteria);
                break;
            case R.id.btn_assign_cost:
                Intent cost = new Intent(SelectionActivity.this, CostsMainActivity.class);
                startActivity(cost);
                break;
            case R.id.btn_result:
                break;
        }
    }
}
