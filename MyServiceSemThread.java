package com.example.patrick.legps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class MyServiceSemThread extends Service {


    final Handler handler = new Handler();
    final AquisicaoSensores info = new AquisicaoSensores(this);

    //Obtém sua localizção atual
    final Localizador locationListener = new Localizador(this);


    Runnable runnableCode;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service Started", LENGTH_LONG).show();

        runnableCode = new Runnable() {

            private int contador = 0;

            @Override
            public void run() {

                Calendar calendario = Calendar.getInstance();

                File arquivo = new File(Environment.getExternalStorageDirectory().toString() + "/InformacoesDaVidaDoUsuario.txt");



                try {
                    FileWriter escritor = new FileWriter(arquivo, true);

                    escritor.write("\n\nTempo atual: " + calendario.get(Calendar.HOUR) + ":" + calendario.get(Calendar.MINUTE) + ":" + calendario.get(Calendar.SECOND) + "," + calendario.get(Calendar.MILLISECOND) + "\n" + info.getInfo());
                    escritor.write("\n\n" + locationListener.getMyLocation() + "\n----------------\n");


                    escritor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (contador++ < 10) {
                    handler.postDelayed(this, 2000);
                }// 1008 indica que o servico se repetirá por uma semana, considerando o delay de 10min (600000).
                else {
                    onDestroy();
                }
            }
        };

        handler.post(runnableCode);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        handler.removeCallbacks(runnableCode);
        Toast.makeText(this, "Service Destroyed", LENGTH_LONG).show();

    }

}