package com.example.chatsamuel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private ListView messageListView;
    private FloatingActionButton sendButton;
    private String currentUsername; // Variable para almacenar el nombre de usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputEditText = findViewById(R.id.input);
        messageListView = findViewById(R.id.list_of_messages);
        sendButton = findViewById(R.id.floatingActionButton);

        // Obtiene el nombre de usuario del Intent
        currentUsername = getIntent().getStringExtra("USERNAME");


        ArrayList<String> messages = new ArrayList<>();
        // Crea el adaptador personalizado
        MessageAdapter adapter = new MessageAdapter(this, messages, currentUsername);

        // Obtiene la referencia al ListView en activity_main.xml y asigna el adaptador
        messageListView.setAdapter(adapter);



        //FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText input = (EditText)findViewById(R.id.input);
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue(FirebaseAuth.getInstance().getCurrentUser() + ": " + inputEditText.getText().toString());
                // Clear the input
                //inputEditText.setText("");

                String newMessage = inputEditText.getText().toString();
                if (!newMessage.isEmpty()) {
                    // Agrega el nuevo mensaje a la lista
                    messages.add(newMessage);
                    // Notifica al adaptador sobre el cambio en los datos
                    adapter.notifyDataSetChanged();
                    // Limpia el campo de entrada
                    inputEditText.setText("");
                }


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("MainActivity", "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Failed to read value
                        Log.w("MainActivity", "Failed to read value.", error.toException());
                    }
                });

            }
        });

    }

}