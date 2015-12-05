package br.com.evolutil.zeplayer.player;

/**
 * <p style="color:green;">
 *      Created by Jez on 11/11/2015.
 * </p>
 */
public interface InterfaceMp3 {
    void play(String mp3); // Inicia a musica
    void pause(); // Faz a pausa da musica
    void stop(); // Para a musica
    boolean isPaused(); // Verifica se a musica esta pausada
    boolean isPlaying(); // Verificar se a musica toca
    String getMp3(); // Retorna endereco da musica

    // Extra
    int getPosition();
    void setPosition(int p);
    int getDuration();
}
