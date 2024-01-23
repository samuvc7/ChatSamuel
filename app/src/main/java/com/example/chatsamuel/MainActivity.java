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
    private DatabaseReference myRef; // Referencia global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.input);
        messageListView = findViewById(R.id.list_of_messages);
        sendButton = findViewById(R.id.floatingActionButton);

        // Obtiene el nombre de usuario del Intent
        currentUsername = getIntent().getStringExtra("USERNAME");

        ArrayList<ChatMessage> messages = new ArrayList<>();

        // Crea el adaptador personalizado
        MessageAdapter adapter = new MessageAdapter(this, messages);

        // Obtiene la referencia al ListView en activity_main.xml y asigna el adaptador
        messageListView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://chatsamuel-e6df6-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("messages");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newMessage = inputEditText.getText().toString().trim();
                String messageUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                if (!newMessage.isEmpty()) {
                    ChatMessage chatMessage = new ChatMessage(newMessage, messageUser);
                    inputEditText.setText("");

                    // Usa push() para generar una clave única para cada mensaje
                    myRef.push().setValue(chatMessage);
                }
            }
        });

        // Agrega un listener solo una vez para evitar escuchar eventos cada vez que se pulsa el botón
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear(); // Limpia la lista antes de volver a cargar los mensajes

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage valorRegistro = snapshot.getValue(ChatMessage.class);
                    messages.add(valorRegistro);
                }

                // Notifica al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });
    }
}