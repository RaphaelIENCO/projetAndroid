package com.example.administrateur.projetandroid;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


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
    private RecyclerView recyclerView;
    private AppAvisbase appdb;
    private AvisDAO avisDAO;

    public AvisFragment() {
        // Required empty public constructor
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
//        recyclerView.setAdapter(new AvisListAdapteur(avisList));
        appdb = AppAvisbase.getAvisbase(getContext());
        avisDAO = appdb.avisDAO();
        prepareCharacterData();

        (new GetAllAvisAsyncTask(avisDAO)).execute();
        return v;
    }

    private void prepareCharacterData() {
        Avis avis1, avis2, avis3, avis4, avis5, avis6;
        avis1 = new Avis("toto", "12/02/2019", 7,"Excellent service , rapidité efficacité  avec des pizzas diverses et de meilleures goût.\n" +
                "Bref excellent rapport prix qualité.\n" +
                "A recommandé");
        avis2 = new Avis("bob", "02/02/2019", 6,"Franchement avant c'était super livraison à l'heure qualité au rendez-vous maintenant c'est médiocre .. pizza arrive cramé , sec avec des ingrédients qui manque .. livraison super longue .. j'hesite à plus y aller");
        avis3 = new Avis("Jean", "15/02/2019", 5,"Bon accueil");
        avis4 = new Avis("Pascal", "30/03/2019", 8,"Les pizza sont bonne,mais la relation client et pas top,personnel souvent froid,ne répond pas au appel par moment,mais les prix sont corrects et on se régale a chaque fois");
        avis5 = new Avis("Pierre", "21/01/2019", 7,"Super tacos, rapport qualité-prix excellent");
        avis6 = new Avis("Sandrine", "17/10/2018", 7,"Très bon rapport qualité prix lieu propre est service rapide");
        (new InsertAsyncTask(avisDAO)).execute(avis1, avis2, avis3, avis4, avis5, avis6);
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

    private class InsertAsyncTask extends AsyncTask<Avis, Void, Void> {
        private AvisDAO dao;
        InsertAsyncTask(AvisDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Avis... avis) {
            for (Avis a : avis) {
                this.dao.insertAvis(a);
            }
            return null;
        }
    }

    private class GetAllAvisAsyncTask extends AsyncTask<Void,Void,Void> {
        private AvisDAO mAsyncTaskDao;
        ArrayList<Avis> avis;

        GetAllAvisAsyncTask(AvisDAO dao){
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            avis = new ArrayList<>(mAsyncTaskDao.getAllAvis());
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            avisList = avis;
            recyclerView.setAdapter(new AvisListAdapteur(avisList,getContext()));
        }
    }

}