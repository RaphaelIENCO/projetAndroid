package com.example.administrateur.projetandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<Avis> avisList = new ArrayList<>();
    public RecyclerView recyclerView;
    private Spinner spinnerRestaurant;
    String[] lRestaurant;
    private List<Restaurant> restaurantList = new ArrayList<>();

    public AvisFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AvisFragment(List<Avis> avisList, List<Restaurant> restaurantList) {
        this.avisList = avisList;
        this.restaurantList = restaurantList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisFragment newInstance(String param1, String param2) {
        AvisFragment fragment = new AvisFragment();
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
        View v = inflater.inflate(R.layout.fragment_my_avis, container, false);

        recyclerView = v.findViewById(R.id.avis_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new AvisListAdapteur(avisList, getContext()));

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        sp.edit().putBoolean(getResources().getString(R.string.key_is_db_initialized), true).apply();
        if (!sp.getBoolean(getResources().getString(R.string.key_is_db_initialized), false)) {

            sp.edit().putBoolean(getResources().getString(R.string.key_is_db_initialized), true).apply();
        }

        lRestaurant = getNameRestaurants(restaurantList);
        spinnerRestaurant = (Spinner) v.findViewById(R.id.spinner_restaurant);

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lRestaurant);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurant.setAdapter(dataAdapterR);


        spinnerRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String myRestaurant = String.valueOf(spinnerRestaurant.getSelectedItem());
                getAvisByRestaurant(myRestaurant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

//        (new GetAllAvisAsyncTask(avisDAO)).execute();
        return v;
    }

    private String[] getNameRestaurants(List<Restaurant> restaurantList) {
        String[] names = new String[restaurantList.size()];
        for (int i = 0; i < restaurantList.size(); i++) {
            names[i] = restaurantList.get(i).getNom();
        }

        return names;
    }

    private void getAvisByRestaurant(String restaurant) {
        ArrayList<Avis> listTemp = new ArrayList<>();
        for (Avis a : avisList) {
            if (a.getRestaurant().equals(restaurant)) {
                listTemp.add(a);
            }
        }
        recyclerView.setAdapter(new AvisListAdapteur(listTemp, getContext()));
        listTemp = null;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
