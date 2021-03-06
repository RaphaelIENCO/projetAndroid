package com.example.administrateur.projetandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences sharedPref;
    private TextView tv_loc_enabled_out = null;
    private View root = null;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
// Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        return root;
    }
    public void updateUI(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean locationEnabled =
                sharedPref.getBoolean(getResources().getString(R.string.key_location_switch), false);
        String isLocationEnable = ": ";
        isLocationEnable += locationEnabled ? "True" : "False";
        tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_location_switch_out);
        tv_loc_enabled_out.setText(isLocationEnable);

        if (locationEnabled) {

            sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String delayValue = sharedPref.getString(getResources().getString(R.string.key_search_delay),
                    "123");
            String string_delay = ": ";
            string_delay += delayValue;
            tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_search_delay_out);
            tv_loc_enabled_out.setText(string_delay);

            String radiusValue = sharedPref.getString(getResources().getString(R.string.key_search_radius),
                    "123");
            String string_radius = ": ";
            string_radius += radiusValue;
            tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_search_radius_out);
            tv_loc_enabled_out.setText(string_radius);
        } else {
            String string_delay = ": ";

            tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_search_delay_out);
            tv_loc_enabled_out.setText(string_delay);


            String string_radius = ": ";
            tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_search_radius_out);
            tv_loc_enabled_out.setText(string_radius);
        }

        String name = sharedPref.getString(getResources().getString(R.string.key_name),"John Smith");
        String string_name = " ";
        string_name += name;
        tv_loc_enabled_out = (TextView) root.findViewById(R.id.text_name);
        tv_loc_enabled_out.setText(string_name);
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
