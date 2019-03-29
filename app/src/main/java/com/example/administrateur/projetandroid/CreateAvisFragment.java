package com.example.administrateur.projetandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateAvisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateAvisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAvisFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View root = null;
    private EditText noteTxtV;
    private EditText avisTxtV;
    private int note;
    private String date;
    private String name;
    private String avis;
    private List<Restaurant> restaurantList;
    private Spinner spinnerRestaurant;
    private Spinner spinnerNote;
    private String[] lRestaurant;
    private String myRegion;


    public CreateAvisFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CreateAvisFragment(List<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAvisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAvisFragment newInstance(String param1, String param2) {
        CreateAvisFragment fragment = new CreateAvisFragment();
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
        root = inflater.inflate(R.layout.fragment_create_avis, container, false);

        spinnerRestaurant = (Spinner) root.findViewById(R.id.spinnerRestaurant_create);


        lRestaurant = getNameRestaurants(restaurantList);
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lRestaurant);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestaurant.setAdapter(dataAdapterR);

        spinnerRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                myRegion = String.valueOf(spinnerRestaurant.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });

        spinnerNote = (Spinner) root.findViewById(R.id.spinner_note_create);
        String[] lNote = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> dataAdapterN = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, lNote);
        dataAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNote.setAdapter(dataAdapterN);

        spinnerNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                note = Integer.parseInt(String.valueOf(spinnerNote.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Date d=new Date();
        date = d.getDate()+" / "+(d.getMonth()+1)+" / "+(d.getYear()+1900);

        Button bouton = root.findViewById(R.id.button_create);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avisTxtV = root.findViewById(R.id.text_input);
                avis = avisTxtV.getText().toString();
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                name = sharedPref.getString(getResources().getString(R.string.key_name),"John Smith");
                Avis newAvis = new Avis(myRegion,name, date, note, avis);

                MainActivity.addAvis(newAvis);

                FragmentManager fm = getFragmentManager();
                Fragment f = new HomeFragment();
                if (fm != null) {
                    fm.beginTransaction().replace(R.id.content_main,f).commit();
                }


                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getActivity().getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Toast.makeText(getContext(),"Avis enregistré",Toast.LENGTH_LONG).show();

            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    private String[] getNameRestaurants(List<Restaurant> restaurantList) {
        String[] names = new String[restaurantList.size()];
        for (int i=0;i<restaurantList.size();i++){
            names[i] = restaurantList.get(i).getNom();
        }

        return names;
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
