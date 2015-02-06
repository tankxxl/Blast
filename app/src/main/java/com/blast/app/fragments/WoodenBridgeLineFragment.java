package com.blast.app.fragments;



import android.os.Bundle;
import android.app.Fragment;
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
 * Use the {@link WoodenBridgeLineFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WoodenBridgeLineFragment extends CommonFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String resistance_ratio;
    private EditText et_blast_area;
    private TextView tv_result;
    private String[] resistance_ratios;
    private Button btn_calc;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WoodenBridgeLineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WoodenBridgeLineFragment newInstance(String param1, String param2) {
        WoodenBridgeLineFragment fragment = new WoodenBridgeLineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public WoodenBridgeLineFragment() {
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
        View view = inflater.inflate(R.layout.fragment_wooden_bridge_line, container, false);
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
        et_blast_area = (EditText) view.findViewById(R.id.et_blast_area);
        et_blast_area.addTextChangedListener(new TextWatcher() {
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
            case R.id.btn_calc:
                String blast_area = et_blast_area.getText().toString().trim();
                String ratio = getRatio(resistance_ratio);
                try {
                    double d_ratio = Double.valueOf(ratio);
                    double d_blast_area = Double.valueOf(blast_area);
                    double result = blastCalc.circularWoodBlast(d_ratio, d_blast_area);
//                    AppUtil.setText(tv_result, "装药量：" + result + "(g)");
                    btn_calc.setText(getString(R.string.result) + result + "(g)");
                } catch (Exception e) {
                    DialogUtil.showAlertDialog(ctx, R.string.invalidNumberException);
                    LogUtil.trace(e);
                }

                break;
        }
    }
}
