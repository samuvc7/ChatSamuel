package com.example.chatsamuel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class MessageAdapter extends ArrayAdapter<String> {
    private String currentUsername;

    public MessageAdapter(Context context, List<String> messages, String currentUsername) {
        super(context, 0, messages);
        this.currentUsername = currentUsername;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtiene el mensaje actual
        String message = getItem(position);

        // Reutiliza la vista si está disponible, de lo contrario, infla una nueva
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }

        // Obtiene las referencias a los TextView en message.xml y establece los valores
        TextView textViewUser = convertView.findViewById(R.id.message_user);
        textViewUser.setText(currentUsername);  // Utiliza el nombre de usuario actual

        TextView textViewTime = convertView.findViewById(R.id.message_time);
        textViewTime.setText(getCurrentTime());  // Establece la hora actual

        TextView textViewText = convertView.findViewById(R.id.message_text);
        textViewText.setText(message);

        return convertView;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Formato de 24 horas (puedes ajustarlo según tus preferencias)
        String currentTime = sdf.format(new Date());
        return currentTime;
    }
}