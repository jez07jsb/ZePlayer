package br.com.evolutil.zeplayer.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

import br.com.evolutil.zeplayer.R;
import br.com.evolutil.zeplayer.fragment.MusicListFragment.OnClickMusicItemListener;
import br.com.evolutil.zeplayer.model.MusicMetaData;

/**
 * Created by Jez on 27/10/2016.
 * .
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private List<String> musiclist;

    private OnClickMusicItemListener mListener;

    public MusicListAdapter(List<String> musiclist, OnClickMusicItemListener mListener) {
        this.musiclist = musiclist;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.music = extractMusicMetadata(musiclist.get(position));
        if (holder.music.getAlbum()!= null) {
            holder.img.setImageBitmap(holder.music.getAlbum());
        }
        holder.txvw_title.setText(holder.music.getTitle());
        holder.txvw.setText(
                holder.music.getArtist()
                        + " - "
                        + holder.music.getTextduration()
        );
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickMusicItem(holder.music);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (musiclist == null) return 0;
        else return musiclist.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MusicMetaData music;
        private TextView txvw_title;
        private TextView txvw;
        private ImageView img;
        private FloatingActionButton fab;

        public ViewHolder(View itemView) {
            super(itemView);
            txvw_title = (TextView) itemView.findViewById(R.id.card_view_music_item_txvw_title);
            txvw = (TextView) itemView.findViewById(R.id.card_view_music_item_txvw);
            img = (ImageView) itemView.findViewById(R.id.card_view_music_item_img);
            fab = (FloatingActionButton) itemView.findViewById(R.id.card_view_music_item_fab);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private MusicMetaData extractMusicMetadata(String path) {
        File f = new File(path);
        MusicMetaData mmd = new MusicMetaData();
        mmd.setFilepath(path);
        String title, artist, duration;

        try {
            // Classe para obter dados de um mediafile
            MediaMetadataRetriever mmdr = new MediaMetadataRetriever();
            if (f.exists() && f.canRead()) {
                // Aqui configuramos a URI do mediafile para obter dados.
                mmdr.setDataSource(f.getAbsolutePath());
                byte[] ba = mmdr.getEmbeddedPicture();
                title = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                artist = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                duration = mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                long tmp = Long.decode(duration);
                long min = (int) tmp/(60*1000);
                long rmin = (int) tmp%(60*1000);
                long sec = (int) rmin/1000;
                duration = ""+min+":"+sec;
                Bitmap bmp = BitmapFactory.decodeByteArray(
                        ba,
                        0,
                        ba.length
                );

                if (title == null) {
                    title = f.getName().replaceFirst(".*.mp3", "");
                }
                if (artist == null) {
                    artist = "Artist?";
                }

                mmd.setAlbum(bmp);
                mmd.setTitle(title);
                mmd.setArtist(artist);
                mmd.setTextduration(duration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mmd;
    }
}
