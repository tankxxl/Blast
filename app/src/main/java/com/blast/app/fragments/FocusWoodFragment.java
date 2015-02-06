package com.blast.app.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.blast.app.R;
import com.blast.app.utils.DialogUtil;
import com.blast.app.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FocusWoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FocusWoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FocusWoodFragment extends CommonFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tv_result;
    private String[] resistance_ratios;
    private String resistance_ratio;
    private EditText et_max_d, et_max_r;
    private Button btn_calc;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FocusWoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FocusWoodFragment newInstance(String param1, String param2) {
        FocusWoodFragment fragment = new FocusWoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FocusWoodFragment getInstance() {
        return newInstance("", "");
    }

    public FocusWoodFragment() {
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

        resistance_ratios = ctx.getResources().getStringArray(R.array.wood_resistance_ratio);
        resistance_ratio = resistance_ratios[0];
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_focus_wood, container, false);

        Spinner sp_resistance_ratio = (Spinner) view.findViewById(R.id.sp_resistance_ratio);
        sp_resistance_ratio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resistance_ratio = resistance_ratios[position];
                btn_calc.setText(R.string.calc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_max_d = (EditText) view.findViewById(R.id.et_max_d);
        et_max_d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_calc.setText(R.string.calc);
//                btn_calc.setText(R.string.calc);
            }
        });
        et_max_r = (EditText) view.findViewById(R.id.et_max_r);
        et_max_r.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_calc.setText(R.string.calc);
//                btn_calc.setText(R.string.calc);
            }
        });

        btn_calc = (Button) view.findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(this);
        tv_result = (TextView) view.findViewById(R.id.tv_result);

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
                String max_d = et_max_d.getText().toString().trim();
                String max_r = et_max_r.getText().toString().trim();
                String ratio = getRatio(resistance_ratio);
                try {
                    double d_max_d = Double.valueOf(max_d);
                    double d_max_r = Double.valueOf(max_r);
                    double d_ratio = Double.valueOf(ratio);
                    double result = blastCalc.focusWoodBlast(d_ratio, d_max_d, d_max_r);
//                    AppUtil.setText(tv_result, "装药量：" + result + "(Kg)");
//                    getString(R.string.result);
                    btn_calc.setText(getString(R.string.result) + result + "(Kg)");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
