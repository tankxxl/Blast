package com.blast.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
 * {@link SoilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoilFragment extends CommonFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Context ctx;

    private TextView tv_ratio, tv_result;
    private Spinner sp_resistance_ratio, sp_impact_ratio;
    private EditText et_min_resistance_line;

    protected Button btn_calc;

    private String[] resistance_ratios, impact_ratios;

    private String resistance_ratio, impact_ratio, min_resistance_line;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoilFragment newInstance(String param1, String param2) {
        SoilFragment fragment = new SoilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SoilFragment getInstance() {
        return newInstance("", "");
    }

    public SoilFragment() {
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
//        LogUtil.trace("onCreateView-getUserVisibleHint()=" + getUserVisibleHint());
        resistance_ratios = ctx.getResources().getStringArray(R.array.soil_resistance_ratio_value);
        impact_ratios = ctx.getResources().getStringArray(R.array.soil_impact_ratio_value);
        resistance_ratio = resistance_ratios[0];
        impact_ratio = impact_ratios[0];
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_soil, container, false);
        tv_ratio = (TextView) view.findViewById(R.id.tv_ratio);
        sp_resistance_ratio = (Spinner) view.findViewById(R.id.sp_resistance_ratio);
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

        sp_impact_ratio = (Spinner) view.findViewById(R.id.sp_impact_ratio);
        sp_impact_ratio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                impact_ratio = impact_ratios[position];
                btn_calc.setText(R.string.calc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_min_resistance_line = (EditText) view.findViewById(R.id.et_min_resistance_line);
        et_min_resistance_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_calc.setText(getString(R.string.calc));
            }
        });
        btn_calc = (Button) view.findViewById(R.id.btn_calc);
        btn_calc.setOnClickListener(this);
        tv_ratio.setOnClickListener(this);
        //
        tv_result = (TextView) view.findViewById(R.id.tv_result);
        tv_result.setVisibility(View.GONE);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ratio:
                DialogUtil.showAlertDialog(getActivity(), R.string.soil_note);
                break;
            case R.id.btn_calc:
                min_resistance_line = et_min_resistance_line.getText().toString().trim();
                try {
                    double d_resistance_ratio = Double.valueOf(resistance_ratio);
                    double d_impact_ratio = Double.valueOf(impact_ratio);
                    double d_min_resistance_line = Double.valueOf(min_resistance_line);
                    double result = blastCalc.soilBlast(d_resistance_ratio, d_impact_ratio, d_min_resistance_line);
                    btn_calc.setText(getString(R.string.result) + result + "(Kg)");
//                    AppUtil.setText(tv_result, "装药量：" + result + "(Kg)");
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
