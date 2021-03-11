package br.com.evolutil.zeplayer.player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * <p style="color:#007830;">
 *     Teste de mp3 player - Service
 * </p>
 * Created by Jez on 11/11/2015.
 */
public class  Mp3Service extends Service implements InterfaceMp3 {

    private static final String TAG = "Mp3Service";
    private PlayerMp3 player = new PlayerMp3();
    private String mp3;
    // Classe filha de Binder para retornar no onBind(intent)
    public class Mp3ServiceBinder extends Binder {
        // Converte para InterfaceMp3
        public InterfaceMp3 getInterface() {
            // Retorna a interface para controlar o Service
            return Mp3Service.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Retorna a classe ConexaoInterfaceMp3 para a activity utilizar
        Log.i(TAG, "Mp3Service onBind. Aqui retorna o IBinder");
        return new Mp3ServiceBinder();
    }

    @Override
    public void onDestroy() {
        // Fim do servico
        Log.i(TAG, "Mp3Service onDestroy().");
        // Para a musica
        stop();
    }

    // Metodo da interface InterfaceMp3
    @Override
    public void play(String mp3) {
        this.mp3 = mp3;
        try {
            player.start(mp3);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    // Metodo da interface InterfaceMp3
    @Override
    public void pause() {
        player.pause();
    }

    // Metodo da interface InterfaceMp3
    @Override
    public void stop() {
        player.stop();
    }

    // Metodo da interface InterfaceMp3
    @Override
    public boolean isPaused() {
        return player.isPaused();
    }

    // Metodo da interface InterfaceMp3
    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    // Metodo da interface InterfaceMp3
    @Override
    public String getMp3() {
        return mp3;
    }

    // Extras
    public int getPosition() {
        return player.getPosition();
    }
    public void setPosition(int p) {
        player.setPosition(p);
    }
    public int getDuration() {
        return player.getDuration();
    }
}
