package com.example.szymon.sklejka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Szymon on 06.07.2017.
 */

public class AdministratorActivity extends MainActivity {

    EditText editName,editSurrname,editmarks,editId;
    Button btnAddData,btnViewAll,btnUpdate,btnDelete,btnEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        //Alokacja Edit
        editName    = (EditText)findViewById(R.id.editText_adminact_name);
        editSurrname    = (EditText)findViewById(R.id.editText_admin_password);
        editmarks   = (EditText)findViewById(R.id.editText_adminact_level);
        editId  = (EditText)findViewById(R.id.editText_adminact_id);


        //Alokacja buttonow
        btnAddData=(Button)findViewById(R.id.button_adminact_add);
        btnViewAll=(Button)findViewById(R.id.button_adminact_viewAll);
        btnUpdate=(Button)findViewById(R.id.button_adminact_update);
        btnDelete=(Button)findViewById(R.id.button_adminact_delete);
        btnEnd= (Button)findViewById(R.id.button_adminact_exit);

        //Wywolanie Listenerow
        AddData();
        ShowData();
        UpdateData();
        DeleteData();
        FinishActivity();

    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(editName.getText().toString().isEmpty()
                                || editSurrname.getText().toString().isEmpty()
                                || editmarks.getText().toString().isEmpty())
                        {
                            Toast.makeText(AdministratorActivity.this, "Fill the blanks", Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean isInserted = myDB.insertData(editName.getText().toString(),
                                editSurrname.getText().toString(),
                                editmarks.getText().toString());
                        if (isInserted) {
                            Toast.makeText(AdministratorActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AdministratorActivity.this, "Data not inserted, some error detected", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void ShowData(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData();
                        if(res.getCount() == 0) //No data avaliable fo us
                        {
                            //error
                            showMessage("ERROR","No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext())
                        {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Password :"+ res.getString(2)+"\n");
                            buffer.append("Level :"+ res.getString(3)+"\n\n");
                        }

                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isUpdate = myDB.updateData(editId.getText().toString(),
                                editName.getText().toString(),
                                editSurrname.getText().toString(),editmarks.getText().toString());
                        if(isUpdate)
                            Toast.makeText(AdministratorActivity.this,"Update Sucess",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AdministratorActivity.this,"Update Error detected",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Integer delete=myDB.deleteData(editId.getText().toString());
                        if(delete==0)
                        {
                            Toast.makeText(AdministratorActivity.this,"I didnt delete any data",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(AdministratorActivity.this,"Data deleted succesfully",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void FinishActivity() {
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
