package br.com.evolutil.zeplayer.player;

import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.util.Log;

/**<p style="font-family:Lucida Sans;font-style:italic;font-weight:bold;color:green;">
 *      Created by Jez on 11/11/2015.
 * </p>
 */
public class PlayerMp3 implements MediaPlayer.OnCompletionListener {

    private static final String CATEGORIA = "PlayerMp3";
    private static final int NOVO = 0;
    private static final int TOCANDO = 1;
    private static final int PAUSADO = 2;
    private static final int PARADO = 3;
    // Comeca o status zerado
    private int status = NOVO;
    private MediaPlayer player;
    // Caminho da musica
    private String mp3;

    public PlayerMp3() {
        // Cria o MediaPlayer
        player = new MediaPlayer();
        // Executa o listener quando terminar a musica
        player.setOnCompletionListener(this);
    }

    public void start(String mp3) {
        if (!mp3.isEmpty()) {
            this.mp3 = mp3;
        } else {
            Log.i(CATEGORIA, "start(String mp3) : String mp3 vazia.");
            return;
        }

        try {
            switch(status) {
                case TOCANDO:
                    player.pause();
                    break;
                case PARADO:
                    player.reset();
                    player.setDataSource(mp3);
                    player.prepare();
                    player.start();
                    break;
                case NOVO:
                    player.setDataSource(mp3);
                    player.prepare();
                    player.start();
                    break;
                case PAUSADO:
                    player.start();
                    break;
            }
            status = TOCANDO;

        } catch (Exception e) {
            Log.e(CATEGORIA, e.getMessage(), e);
        }
    }

    public void pause() {
        player.pause();
        status = PAUSADO;
    }


    public void stop() {
        player.stop();
        status = PARADO;
    }

    // Encerra o media player e libera memoria.
    public void close() {
        stop();
        player.release();
        player = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        status = PARADO;
        Log.i(CATEGORIA, "Fim da musica: "+mp3);
    }

    public boolean isPaused() {
        // Retorna true se a musica esta tocando ou se esta em pause.
        return (status == PAUSADO);
    }

    public boolean isPlaying() {
        // Retorna true se a musica esta tocando ou se esta em pause.
        return (status == TOCANDO || status == PAUSADO);
    }

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // #### para uso na interface ####
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    public int getPosition() {
        if (player!=null) {
            return player.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (player!=null) {
            return player.getDuration();
        }
        return 0;
    }

    public void setPosition(int p) {
        if (isPlaying()) {
            player.seekTo(p);
        }
    }
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
}
