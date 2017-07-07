package com.example.szymon.sklejka;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDB;
    Button button_newAccount,button_LogIn,button_Administrator;
    String last_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(this);

        button_newAccount = (Button)findViewById(R.id.Button_NewAccount);
        button_LogIn = (Button)findViewById(R.id.Button_LogIn);
        button_Administrator = (Button)findViewById(R.id.Button_Administrator);

        AdministratorListner();
        LoginListner();
        NewAccountListner();
    }

    public void AdministratorListner() {
        button_Administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_main_administrator_dialog,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                final EditText mPassword = (EditText) mView.findViewById(R.id.editText_AdminPassword);
                Button mButton = (Button) mView.findViewById(R.id.button_Administrator_Password);

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mPassword.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Fill blanks",Toast.LENGTH_LONG).show();
                        }
                        else
                            if(mPassword.getText().toString().equals("admin1234"))
                            {
                                Intent i = new Intent(MainActivity.this,AdministratorActivity.class);
                                startActivity(i);
                            }
                            else
                                Toast.makeText(MainActivity.this,"U dont know password",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void LoginListner() {
        button_LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_main_login_layout,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                final EditText mLogin = (EditText) mView.findViewById(R.id.edit_text_login_login);
                final EditText mPassword = (EditText) mView.findViewById(R.id.edit_text_login_password);
                Button mButton = (Button) mView.findViewById(R.id.button_login_login);
                System.out.println("Login "+mLogin.getText().toString());
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("U clicked");
                        if(mLogin.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Fill the blank fields",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            String s=FindPassword(mLogin.getText().toString());
                            if(s.isEmpty())
                            {
                                Toast.makeText(MainActivity.this,"In our database there is not such user",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(mPassword.getText().toString().equals(s))
                                {
                                    last_name=mLogin.getText().toString();
                                    Intent i = new Intent(MainActivity.this,HeroActivity.class);
                                    i.putExtra("last_name",last_name);
                                    i.putExtra("level",FindLevel(last_name));
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this,"Bad Combination. Try Again",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    public void NewAccountListner() {
        button_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_main_login_layout,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                final EditText mLogin = (EditText) mView.findViewById(R.id.edit_text_login_login);
                final EditText mPassword = (EditText) mView.findViewById(R.id.edit_text_login_password);
                Button mButton = (Button) mView.findViewById(R.id.button_login_login);
                mButton.setText("Create new Account");
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = FindPassword(mLogin.getText().toString());
                        if(mLogin.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Fill the blank fields",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if(s.isEmpty())
                            {
                                last_name=mLogin.getText().toString();
                                myDB.insertData(mLogin.getText().toString(),mPassword.getText().toString(),"1");
                                Intent i = new Intent(MainActivity.this,HeroActivity.class);
                                i.putExtra("last_name",last_name);
                                i.putExtra("level",FindLevel(last_name));
                                startActivityForResult(i,1);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"We already have such user.Change name",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
    }

    public String FindPassword(String name) {
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0) //No data avaliable for us
        {
            return "";
        }
        while(res.moveToNext())
        {
            if(res.getString(1).equals(name)) {
                return res.getString(2);
            }
        }
        return "";


    }

    public String FindLevel(String name) {
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0) //No data avaliable for us
        {
            return "";
        }
        while(res.moveToNext())
        {
            if(res.getString(1).equals(name)) {
                return res.getString(3);
            }
        }
        return "";

    }

    public String FindId(String name) {
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0) //No data avaliable for us
        {
            return "";
        }
        while(res.moveToNext())
        {
            if(res.getString(1).equals(name)) {
                return res.getString(0);
            }
        }
        return "";

    }

    public void updatedata(String name,String level)
    {
        myDB.updateData(FindId(name),name,FindPassword(name),level);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(MainActivity.this,"Heros update",Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                myDB.updateData(FindId(last_name),
                        last_name,
                        FindPassword(last_name),
                        data.getStringExtra("level"));
            }
        }
    }
}
