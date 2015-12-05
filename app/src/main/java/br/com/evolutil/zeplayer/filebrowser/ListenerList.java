package br.com.evolutil.zeplayer.filebrowser;

import java.util.ArrayList;
import java.util.List;

/**
 * Escutador da lista de arquivos
 * Created by Jez on 13/11/2015.
 */
public class ListenerList<L> {
    private List<L> listenerList = new ArrayList<L>();

    public void add(L listener) {
        listenerList.add(listener);
    }

    public void fireEvent(FireHandler<L> fireHandler) {
        List<L> copy = new ArrayList<L>(listenerList);
        for (L l : copy) {
            fireHandler.fireEvent(l);
        }
    }

    public void remove(L listener) {
        listenerList.remove(listener);
    }

    public List<L> getListenerList() {
        return listenerList;
    }
}
