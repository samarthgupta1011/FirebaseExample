package example.samarthgupta.com.firebasetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    EditText etName, etAge, etSearch;
    Button btEnter, btSearch;
    TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
        btEnter = (Button) findViewById(R.id.bt_enter);
        etSearch = (EditText) findViewById(R.id.et_search);
        btSearch = (Button) findViewById(R.id.bt_search);
        tvSearch = (TextView) findViewById(R.id.tv_search);


        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                int n = random.nextInt();

                Person p = new Person();
                p.setAge(Integer.parseInt(etAge.getText().toString()));
                p.setName(etName.getText().toString());

                databaseReference.child(Integer.toString(n)).setValue(p);



            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchId = etSearch.getText().toString();

                databaseReference.child(searchId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Person pi = dataSnapshot.getValue(Person.class);
                        if (pi != null) {
                            tvSearch.setText(pi.getName());
                        } else {
                            Toast.makeText(MainActivity.this, "No person", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });


    }
}
