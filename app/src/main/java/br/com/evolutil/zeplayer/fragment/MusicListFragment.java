package br.com.evolutil.zeplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.evolutil.zeplayer.R;
import br.com.evolutil.zeplayer.adapter.MusicListAdapter;
import br.com.evolutil.zeplayer.model.MusicMetaData;

/**
 * Created by Jez on 27/10/2016.
 * Subclass of {@link Fragment} to list musics in storage.
 */

public class MusicListFragment extends Fragment {

    private OnClickMusicItemListener musicItemListener;

    public MusicListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_music_list, container, false);

        if (rootview instanceof RecyclerView) {
            RecyclerView rcvw = (RecyclerView) rootview.findViewById(R.id.fragment_music_list_rcvw);

            String[] musiclist_a = getActivity().getIntent().getStringArrayExtra("musiclist");

            List<String> musiclist = new ArrayList<>();

            Collections.addAll(musiclist, musiclist_a);

            MusicListAdapter mla = new MusicListAdapter(musiclist, musicItemListener);
            rcvw.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvw.setAdapter(mla);
        }

        return rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickMusicItemListener) {
            musicItemListener = (OnClickMusicItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "Precisa implementar OnClickMusicItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        musicItemListener = null;
    }

    public interface OnClickMusicItemListener {
        void onClickMusicItem(MusicMetaData music);
    }
}
