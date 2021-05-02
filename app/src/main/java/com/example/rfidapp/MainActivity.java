package com.example.rfidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView listviewparent;
    ListView listviewstudentdata;
    ListView listviewstudenttime;
    ListView listadminatd;
    ListView listadminatdtime;
    ListView listadminparent;
    ListView liststudent;
    Button back4;
    Button buttonaddStudent;
    Button button;
    Button adminATD;
    Button ADDP;
    Button ADDS;
    Button back;
    Button back2;
    Button back3;
    Button addparentB;
    Button Buttonadd;
    Button addS;
    EditText addsID;
    EditText addsPass;
    EditText IDfield;
    EditText Pwfield;
    EditText addparentID;
    EditText addparentPass;
    EditText addparentphone;
    TextView textTime;
    DatabaseReference ref;
    ArrayList<String> arrayList= new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    String currentDate = dateFormat.format(calendar.getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button  = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        IDfield = (EditText) findViewById(R.id.IDfield);
        Pwfield = (EditText) findViewById(R.id.Pwfield);



        String IDdata = IDfield.getText().toString();
        String pass = Pwfield.getText().toString();
        ref  = FirebaseDatabase.getInstance().getReference("user").child(IDdata);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Getdata getdata = (Getdata) snapshot.getValue(Getdata.class);
                if (getdata == null) {
                    IDfield.setText("");
                    Pwfield.setText("");
                    new AlertDialog.Builder(MainActivity.this).setTitle("ไม่พบข้อมูล").setCancelable(false).setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                    return;
                }
                else{
                    String passdata = getdata.getPassword();
                    Log.d("ddddddddddddddddddd01", passdata);
                    Log.d("ddddddddddddddddddd02", pass);
                    if(passdata.equals(pass) )
                    {
                        String roledata = getdata.getRole();
                        Log.d("ddddddddddddddddddd02", roledata);
                        if(roledata.equals("admin")){
                            Adminmain(IDdata);
                        }
                        else{
                            parentmain(IDdata);
                        }
                    }
                    else{
                        new AlertDialog.Builder(MainActivity.this).setTitle("พาสผิด").setCancelable(false).setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                        return;
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
//Parent part
    public void parentmain(String IDdata){
        setContentView(R.layout.paren_main);
        String parentID = IDdata;
        listviewparent = (ListView) findViewById(R.id.listadminparent);
        ref  = FirebaseDatabase.getInstance().getReference("user").child(IDdata).child("student");
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listviewparent.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value2= (String) snapshot.getKey();
                String value1= (String) snapshot.getValue();
                String Data = "ID  " + value2 + "   ชื่อ   "+ value1;
                arrayList.add(Data);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listviewparent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemdata = arrayAdapter.getItem(position);
                itemdata = itemdata.replaceAll("[^\\d.]", "");
                itemdata = itemdata.replace(".", "");
               // Log.d("ddddddddddddddddddd", itemdata);
               showstudentdata(itemdata,parentID);

            }
        });
    }
    public void showstudentdata(String itemdata,String parentID){
        setContentView(R.layout.student_info);
        String iddata = itemdata;
        arrayList.clear();
        listviewstudentdata = (ListView) findViewById(R.id.listviewstudentdata);
        ref  = FirebaseDatabase.getInstance().getReference("ATD").child(itemdata);
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listviewstudentdata.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getKey();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listviewstudentdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemdata = arrayAdapter.getItem(position);
                itemdata = itemdata.replaceAll("[^\\d.]", "");
                itemdata = itemdata.replace(".", "");
                // Log.d("ddddddddddddddddddd", itemdata);
                showstudenttime(itemdata,iddata,parentID);
            }
        });

    }
    public void showstudenttime(String itemdata,String iddata,String parentID){
        setContentView(R.layout.student_time);
        Button button;
        back = (Button) findViewById(R.id.back);
        textTime = (TextView) findViewById(R.id.textTime);
        textTime.setText(itemdata);
        arrayList.clear();
        listviewstudenttime = (ListView) findViewById(R.id.listviewstudenttime);
        ref  = FirebaseDatabase.getInstance().getReference("ATD").child(iddata).child(itemdata);
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listviewstudenttime.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getKey();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                parentmain(parentID);
            }
        });
    }

    // Admin part
    public void Adminmain(String IDdata){
        setContentView(R.layout.admin_main);
        adminATD = (Button) findViewById(R.id.adminATD);
        ADDP = (Button) findViewById(R.id.ADDP);
      //  ADDS = (Button) findViewById(R.id.ADDS);

        adminATD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminATDmain(IDdata);
            }
        });
        ADDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usermain(IDdata);
            }
        });




    }
    public void usermain(String IDdata){
        setContentView(R.layout.usermain);
        arrayList.clear();

        back3 = (Button) findViewById(R.id.back3);
        Buttonadd = (Button) findViewById(R.id.Buttonadd);
        listadminparent = (ListView) findViewById(R.id.listadminparent);
        ref  = FirebaseDatabase.getInstance().getReference("user");
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listadminparent.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getKey();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listadminparent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemdata = arrayAdapter.getItem(position);
                adminstudent(itemdata,IDdata);

            }
        });
        Buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDPmain(IDdata);
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adminmain(IDdata);
            }
        });

    }
    public  void  adminstudent(String itemdata,String IDdata){
        arrayList.clear();
        setContentView(R.layout.studentmain);
        back4 = (Button) findViewById(R.id.back4);
        buttonaddStudent = (Button) findViewById(R.id.buttonaddStudent);
        liststudent = (ListView) findViewById(R.id.liststudent);
        ref  = FirebaseDatabase.getInstance().getReference("user").child(itemdata).child("student");
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        liststudent.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value2= (String) snapshot.getKey();
                String value1= (String) snapshot.getValue();
                String Data = "ID  " + value2 + "ชื่อ"+ value1;
                arrayList.add(Data);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                Adminmain(IDdata);
            }
        });
        buttonaddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDSmain(IDdata,itemdata);
            }
        });

    }
    public void adminATDmain(String IDdata){
        setContentView(R.layout.adminatdmain);
        ref  = FirebaseDatabase.getInstance().getReference("ATD").child("DATE");
        listadminatd = (ListView) findViewById(R.id.listadminparent);
        arrayList.clear();
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listadminatd.setAdapter(arrayAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getKey();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listadminatd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemdata = arrayAdapter.getItem(position);
                itemdata = itemdata.replaceAll("[^\\d.]", "");
                itemdata = itemdata.replace(".", "");
                // Log.d("ddddddddddddddddddd", itemdata);
                adminATDtime(itemdata,IDdata);

            }
        });
    }
    public void adminATDtime(String itemdata,String IDdata){
        setContentView(R.layout.adminatdtime);
        back2 = (Button) findViewById(R.id.back2);
        listadminatdtime = (ListView) findViewById(R.id.listadminparent);
        arrayList.clear();
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listadminatdtime.setAdapter(arrayAdapter);
        ref  = FirebaseDatabase.getInstance().getReference("ATD").child("DATE").child(itemdata);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key= (String) snapshot.getKey();
                String value= (String) snapshot.getValue();
                String data = "  ID  " + key +"  เวลาเช็ค "+ value ;
                arrayList.add(data);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adminmain(IDdata);
            }
        });
    }


    public void ADDPmain(String IDdata){
        setContentView(R.layout.addpmain);
        addparentB = (Button) findViewById(R.id.addparentB);
        addparentPass = (EditText) findViewById(R.id.addparentPass);
        addparentphone = (EditText) findViewById(R.id.addparentphone);
        addparentID = (EditText) findViewById(R.id.addparentID);
        ref  = FirebaseDatabase.getInstance().getReference("user");
        addparentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iddata = addparentID.getText().toString();
                String passdata = addparentPass.getText().toString();
                String phonedata = addparentphone.getText().toString();
                ref.child(iddata).child("ID").setValue(iddata);
                ref.child(iddata).child("password").setValue(passdata);
                ref.child(iddata).child("role").setValue("parent");
                ref.child(iddata).child("phone").setValue(phonedata);

                arrayList.clear();
                usermain(IDdata);
            }
        });



    }
    public void ADDSmain(String IDdata,String itemdata){
        setContentView(R.layout.addsmain);
        addS = (Button) findViewById(R.id.addS);
        addsID = (EditText) findViewById(R.id.addsID);
        addsPass = (EditText) findViewById(R.id.addsPass);
        ref  = FirebaseDatabase.getInstance().getReference("user").child(itemdata).child("student");
        addS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iddata = addsID.getText().toString();
                String namedata = addsPass.getText().toString();
                ref.child(namedata).setValue(iddata);
                ref  = FirebaseDatabase.getInstance().getReference("student").child(iddata);
                ref.child("ID").setValue(iddata);
                ref.child("Name").setValue(namedata);
                ref.child("parent").setValue(itemdata);
                arrayList.clear();
                usermain(IDdata);
            }
        });


    }
}