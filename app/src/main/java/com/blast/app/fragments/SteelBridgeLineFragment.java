package com.blast.app.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.blast.app.R;
import com.blast.app.utils.DialogUtil;
import com.blast.app.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SteelBridgeLineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SteelBridgeLineFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SteelBridgeLineFragment extends CommonFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText et_thickness, et_area;
    private Button btn_calc;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SteelBridgeLineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SteelBridgeLineFragment newInstance(String param1, String param2) {
        SteelBridgeLineFragment fragment = new SteelBridgeLineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SteelBridgeLineFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_steel_bridge_line, container, false);

        et_thickness = (EditText) view.findViewById(R.id.et_thickness);
        et_thickness.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_calc.setText(R.string.calc);
            }
        });

        et_area = (EditText) view.findViewById(R.id.et_area);
        et_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_calc.setText(R.string.calc);
            }
        });

        btn_calc = (Button) view.findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_calc:
                String thickness = et_thickness.getText().toString().trim();
                String area = et_area.getText().toString().trim();
                try {
                    double d_thickness = Double.valueOf(thickness);
                    double d_area = Double.valueOf(area);
                    double result = myCalc.steelBridgeLine(d_thickness, d_area);
//                    AppUtil.setText(tv_result, "中级炸药装药量：" + result + "(Kg)");
                    btn_calc.setText(getString(R.string.result) + result + "(g)");
                } catch (Exception e) {
                    DialogUtil.showAlertDialog(ctx, R.string.invalidNumberException);
                    LogUtil.trace(e);
                }
                break;
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}
