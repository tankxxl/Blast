package com.blast.app.fragments;

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


public class BrickLineFragment extends CommonFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv_result, tv_ratio;
    private EditText et_destroy_r, et_line;
    private Spinner sp_resistance_ratio, sp_fill_ratio;
    private String[] resistance_ratios, fill_ratios;
    private String resistance_ratio, fill_ratio;

    private Button btn_calc;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrickFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrickLineFragment newInstance(String param1, String param2) {
        BrickLineFragment fragment = new BrickLineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static BrickLineFragment getInstance() {
        return newInstance("", "");
    }

    public BrickLineFragment() {
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
        resistance_ratios = ctx.getResources().getStringArray(R.array.brick_resistance_ratio);
        fill_ratios = ctx.getResources().getStringArray(R.array.brick_fill_ratio);
        resistance_ratio = resistance_ratios[0];
        fill_ratio = fill_ratios[0];
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brick_line, container, false);

        tv_ratio = (TextView) view.findViewById(R.id.tv_ratio);
        tv_ratio.setOnClickListener(this);
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
        sp_fill_ratio = (Spinner) view.findViewById(R.id.sp_fill_ratio);
        sp_fill_ratio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fill_ratio = fill_ratios[0];
                btn_calc.setText(R.string.calc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_destroy_r = (EditText) view.findViewById(R.id.et_destroy_r);
        et_destroy_r.addTextChangedListener(new TextWatcher() {
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
        et_line = (EditText) view.findViewById(R.id.et_line);
        et_line.addTextChangedListener(new TextWatcher() {
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
        tv_result = (TextView) view.findViewById(R.id.tv_result);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ratio:
                DialogUtil.showAlertDialog(getActivity(), R.string.brick_note);
                break;
            case R.id.btn_calc:
                String destroy_r = et_destroy_r.getText().toString().trim();
                String line = et_line.getText().toString().trim();
                try {
                    double d_resistance_ratio = Double.valueOf(getRatio(resistance_ratio));
                    double d_fill_ratio = Double.valueOf(getRatio(fill_ratio));
                    double d_destroy_r = Double.valueOf(destroy_r);
                    double d_line = Double.valueOf(line);
                    double result = blastCalc.brickBlast(d_resistance_ratio, d_fill_ratio, d_destroy_r, d_line);
//                    AppUtil.setText(tv_result, "中级炸药装药量：" + result + "(Kg)");
                    btn_calc.setText(getString(R.string.result) + result + "(Kg)");
                } catch (Exception e) {
                    DialogUtil.showAlertDialog(ctx, R.string.invalidNumberException);
                    LogUtil.trace(e);
                }
                break;
        }
    }

}
