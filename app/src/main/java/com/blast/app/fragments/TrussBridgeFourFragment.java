package com.blast.app.fragments;



import android.app.Fragment;
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
 * Use the {@link TrussBridgeFourFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TrussBridgeFourFragment extends CommonFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText et_length;
    private Button btn_calc;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrussBridgeFourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrussBridgeFourFragment newInstance(String param1, String param2) {
        TrussBridgeFourFragment fragment = new TrussBridgeFourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public TrussBridgeFourFragment() {
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
        View view = inflater.inflate(R.layout.fragment_truss_bridge_four, container, false);

        et_length = (EditText) view.findViewById(R.id.et_length);
        et_length.addTextChangedListener(new TextWatcher() {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_calc:
                String length = et_length.getText().toString().trim();
                try {
                    double d_length = Double.valueOf(length);
                    double result = myCalc.trussBridgeFour(d_length);
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
