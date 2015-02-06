package com.blast.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.blast.BlastCalculat;
import com.blast.app.utils.MyCalcUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link com.feelyou.studiotest.fragments.CommonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {link com.feelyou.studiotest.fragments.CommonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    protected Context ctx = null;
    protected BlastCalculat blastCalc = new BlastCalculat();
    protected MyCalcUtil myCalc = new MyCalcUtil();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * param param1 Parameter 1.
     * param param2 Parameter 2.
     *
     * @return A new instance of fragment BrickFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static CommonFragment newInstance(String param1, String param2) {
//        CommonFragment fragment = new CommonFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    public static CommonFragment getInstance() {
//        return newInstance("", "");
//    }
    public CommonFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity;
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public String getRatio(String item) {
        String ret = "";
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
//        item = "干-脆弱木材[杨、柳](0.8)";
        Matcher matcher = pattern.matcher(item);
        if (matcher.find()) {
            ret = matcher.group();
//            System.out.println(matcher.group());
        }
        return ret;
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
