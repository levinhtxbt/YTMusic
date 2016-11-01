package net.levinh.ytmusic.view.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by virus on 13/10/2016.
 */

public interface SearchView {

    Context getContext();

    RecyclerView.Adapter getAdapter();
}
