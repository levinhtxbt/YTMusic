package net.levinh.ytmusic.temp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.levinh.ytmusic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] strings = {"abc","def"};

        List<String> list = new ArrayList<>(30);
        Random random = new Random();
        while (list.size() < 30) {
            //list.add(BaseActivity.sCheeseStrings[random.nextInt(BaseActivity.sCheeseStrings.length)]);
            list.add(strings[random.nextInt(strings.length)]);
        }
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(list)); // getActivity();
        return recyclerView;
    }

    static class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        // private final TypedValue mTypedValue = new TypedValue();
        private final List<String> mValues;

        SimpleStringRecyclerViewAdapter(List<String> items) {
            // context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            // mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mTextView.setText(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mTextView;

            ViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }
    }
}
