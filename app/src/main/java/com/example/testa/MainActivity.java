package com.example.testa;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testa.databinding.ActivityMainBinding;
import com.example.testa.model.PokerCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    static char lastDeleted;
    static int deletions = 0;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        listeners();
    }

    private void listeners() {
        binding.btnExc1.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etExc1.getText())) {
                binding.tilExc1.setError("Please enter a string");
            } else {
                deletions = 0;
                binding.tilExc1.setError(null);
                Thread thread = new Thread(() -> {
                    deleteUtil(binding.etExc1.getText().toString());
                    runOnUiThread(() -> binding.tvOutputExc1.setText("Deletions: " + deletions));
                });

                thread.start();
            }
        });


        binding.btnExc21.setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                int cont = getFullDecksCount("data1.json");
                runOnUiThread(() -> binding.tvOutputExc2.setText("Count of full decks: " + cont));
            });

            thread.start();
        });


        binding.btnExc22.setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                int cont = getFullDecksCount("data2.json");
                runOnUiThread(() -> binding.tvOutputExc2.setText("Count of full decks: " + cont));
            });

            thread.start();
        });
    }

    public int getFullDecksCount(String source) {
        int cont = 0;
        String json1 = readJsonFromAssets(source, this);

        Gson gson = new Gson();
        Type listCardsType = new TypeToken<List<PokerCard>>() {
        }.getType();
        List<PokerCard> pokerCardList = gson.fromJson(json1, listCardsType);


        if (pokerCardList != null) {
            if (pokerCardList.size() >= 52) {
                List<Map.Entry<PokerCard, Long>> cant = new ArrayList<>(pokerCardList
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet());

                cant.sort(Map.Entry.comparingByValue());

                if (cant.size() == 52) {
                    cont = cant.get(0).getValue().intValue();
                }
            }
        }

        return cont;
    }

    String readJsonFromAssets(String filename, Context context) {
        String jsonString;

        try {
            InputStream is = context.getAssets().open(filename, MODE_PRIVATE);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }

        return jsonString;
    }


    //problem A
    public static String deleteUtil(String inputSting) {
        lastDeleted = '\0';

        //Si la longitud es 0 o 1 retorno la cadena
        if (inputSting.length() == 0 || inputSting.length() == 1) {
            return inputSting;
        }

        if (inputSting.charAt(0) == inputSting.charAt(1)) {
            lastDeleted = inputSting.charAt(0);
            deletions++;
            while (inputSting.length() > 1 && inputSting.charAt(0) == inputSting.charAt(1)) {
                inputSting = inputSting.substring(1);
                return deleteUtil(inputSting);
            }
        }

        String remainString = deleteUtil(inputSting.substring(1));

        if (remainString.length() != 0 && remainString.charAt(0) == inputSting.charAt(0)) {
            lastDeleted = inputSting.charAt(0);
            deletions++;
            //Delete first character
            return remainString.substring(1);
        }

        if (remainString.length() == 0 && lastDeleted == inputSting.charAt(0)) {
            return remainString;
        }


        //If los dos primeros caracteres del input y remain no coinciden, insertar
        // el primer caracter del input antes del primer caracter del remain
        return (inputSting.charAt(0) + remainString);
    }
}




