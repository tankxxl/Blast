package com.blast.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blast.app.activities.MainActivity;

import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class AppUtil {

    public static final String CMCC_PATTERN = "^(13[4-9]|15[0-2,7-9]|18[2,3,7,8]|147)\\d{8}$";

    public static void changeLang(Context ctx, Locale locale) {
        Configuration config = ctx.getResources().getConfiguration();
        config.locale = locale;
        ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());

        // 如果要当前显示语言切换效果，则要重启当前页：
        Intent intent = new Intent();
        intent.setClass(ctx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);

    }

    public static boolean checkPhone(String phone) {
        if (phone == null || "".equals(phone))
            return false;
        Pattern pattern = Pattern.compile(CMCC_PATTERN);
        if (pattern.matcher(phone).matches())
            return true;
        else
            return false;
    }

    public static boolean isCMCCPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String temp = phone.trim().replace(" ", ""); // 支持空格分隔的手机号格式
        if (temp.length() < 11)
            return false;
        Pattern pattern = Pattern.compile(CMCC_PATTERN);
        if (pattern.matcher(temp).matches())
            return true;
        else
            return false;
    }

    public static boolean isCMCCPhoneDlg(Context ctx, String phone) {
        boolean ret = isCMCCPhone(phone);
        if (!ret) {
            DialogUtil.showAlertDialog(ctx, "请输入正确的中国移动手机号码。",
                    DialogUtil.defaultListener);
        }
        return ret;
    }

    public static HttpHost getProxy(Context context) {
        HttpHost proxy = null;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()
                && ni.getType() == ConnectivityManager.TYPE_MOBILE) {
            String proxyHost = android.net.Proxy.getDefaultHost();
            int port = android.net.Proxy.getDefaultPort();
            LogUtil.trace("proxyHost=" + proxyHost + ",port=" + port);
            if (proxyHost != null)
                proxy = new HttpHost(proxyHost, port);
        }
        return proxy;
    }

    public static boolean isEmptyDlg(Context ctx, String text, String tip) {

        boolean ret = TextUtils.isEmpty(text);
        if (ret) {
            DialogUtil.showAlertDialog(ctx, tip, DialogUtil.defaultListener);
        }
        return ret;
    }

    public static boolean isCMCC(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        String cmcc = "46000,46002,46007"; // 中国移动专属代码
        String simSerialNumber_prefix = "898600";
        String simNo = tm.getSimSerialNumber(); // ICCID
        if (!TextUtils.isEmpty(simNo)) {
            simNo = simNo.substring(0, 6);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("getSimOperator()=" + tm.getSimOperator() + "\n");
        sb.append("getSimOperatorName()=" + tm.getSimOperatorName() + "\n");
        sb.append("getSimSerialNumber(iccid)=" + tm.getSimSerialNumber() + "\n");
        sb.append("getNetworkOperator()=" + tm.getNetworkOperator() + "\n");
        sb.append("getNetworkOperatorName(=)" + tm.getNetworkOperatorName()
                + "\n");
        sb.append("getNetworkType()=" + tm.getNetworkType() + "\n");
        LogUtil.trace(sb.toString());

        switch (tm.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                LogUtil.trace("TelephonyManager.NETWORK_TYPE_UNKNOWN");
                break;

            default:
                break;
        }

        switch (tm.getSimState()) {
            case TelephonyManager.SIM_STATE_READY:
                LogUtil.trace("TelephonyManager.SIM_STATE_READY");
                break;
            case TelephonyManager.SIM_STATE_ABSENT:
                LogUtil.trace("TelephonyManager.SIM_STATE_ABSENT");
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                LogUtil.trace("TelephonyManager.SIM_STATE_NETWORK_LOCKED");
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                LogUtil.trace("TelephonyManager.SIM_STATE_PIN_REQUIRED");
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                LogUtil.trace("TelephonyManager.SIM_STATE_PUK_REQUIRED");
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                LogUtil.trace("TelephonyManager.SIM_STATE_UNKNOWN");
                break;
            default:
                break;
        }

        // 如果没有移动网络连接则为无卡
        if (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            return false;
        }
        // 满足以下任意一个条件即可。
        if (cmcc.contains(tm.getSimOperator())
                && !TextUtils.isEmpty(tm.getSimOperator())) {
            return true;
        }
        if (cmcc.contains(tm.getNetworkOperator())
                && !TextUtils.isEmpty(tm.getNetworkOperator())) {
            return true;
        }
        if ("CMCC".equals(tm.getSimOperatorName())
                && !TextUtils.isEmpty(tm.getSimOperatorName())) {
            return true;
        }
        if ("中国移动".equals(tm.getNetworkOperatorName())
                && !TextUtils.isEmpty(tm.getNetworkOperatorName())) {
            return true;
        }
        if (simSerialNumber_prefix.equals(simNo)) {
            return true;
        }
        return false;
    }

    // drawable, id, layout, string, color, dimen
    private static int getResourceId(Context context, String name, String type) {
        Resources themeResources = null;
        PackageManager pm = context.getPackageManager();
        //
        try {
            themeResources = pm.getResourcesForApplication(context
                    .getPackageName());
            return themeResources.getIdentifier(name, type,
                    context.getPackageName());
        } catch (NameNotFoundException e) {

            LogUtil.trace(e);
        }
        return 0;
    }

    public static int getDrawableId(Context context, String name) {
        return getResourceId(context, name, "drawable");
    }

    public static int getId(Context context, String name) {
        return getResourceId(context, name, "id");
    }

    public static int getLayoutId(Context context, String name) {
        return getResourceId(context, name, "layout");
    }

    // public static int getStringId(Context context, String name) {
    // return getResourceId(context, name, "string");
    // }

    // public static String getString(Context ctx, String name) {
    // return ctx.getString(getStringId(ctx, name));
    // }

    public static int getColorId(Context context, String name) {
        return getResourceId(context, name, "color");
    }

    public static int getDimenId(Context context, String name) {
        return getResourceId(context, name, "dimen");
    }


    /**
     * 得到软件安装包信息
     *
     * @param context 上下文对象
     * @return 包信息对象
     */
    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi;
        } catch (Exception e) {
            LogUtil.trace(e);
        }
        return null;
    }

    public static String getIMEI(Context context) {
        String imei = "";
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();
        if (TextUtils.isEmpty(imei))
            imei = "";
        return imei;
    }

    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        if (TextUtils.isEmpty(imsi))
            imsi = "";
        return imsi;
    }

    public static String getICCID(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = tm.getSimSerialNumber(); // 取出ICCID
        if (TextUtils.isEmpty(iccid))
            iccid = "";
        return iccid;
    }

    /**
     * 检查网络状态，不判断网络类型
     */
    public static boolean checkNetwork(Context context) {
        boolean flag = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = cwjManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            flag = activeNetInfo.isAvailable();
        }
        return flag;
    }

    public static String getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null)
            return "";
        if (!ni.isAvailable())
            return "";
        LogUtil.trace("ni.getTypeName()=" + ni.getTypeName());
        String extraInfo = ni.getExtraInfo();
        LogUtil.trace("ni.getExtraInfo()=" + extraInfo);
        if (ni.getType() == ConnectivityManager.TYPE_WIFI)
            return ni.getTypeName();
        if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
            String extrString = ni.getExtraInfo();
            if (!TextUtils.isEmpty(extrString)) {
                return extrString;
            }
        }
        return "";
    }

    public static String getString(JSONObject jobj, String key) {
        try {
            if (jobj == null)
                return "";
            return jobj.getString(key);
        } catch (JSONException e) {
            LogUtil.trace(e);
            return "";
        }
    }

    public static int getInt(JSONObject jobj, String key) {
        try {
            if (jobj == null)
                return 0;
            return jobj.getInt(key);
        } catch (JSONException e) {
            LogUtil.trace(e);
            return 0;
        }
    }

    public static boolean validTime(String str, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date date = null;
        try {
            date = (Date) formatter.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return str.equals(formatter.format(date));
    }

    // const
    public static SpannableStringBuilder getColorString(String info,
                                                        String colorInfo, int colorId) {
        if (TextUtils.isEmpty(info))
            return null;
        if (TextUtils.isEmpty(colorInfo))
            return null;
        int indexOf = info.indexOf(colorInfo);
        int length = colorInfo.length();
        SpannableStringBuilder style = new SpannableStringBuilder(info);
        style.setSpan(new ForegroundColorSpan(colorId), indexOf, indexOf
                + length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableStringBuilder getColorString(String info,
                                                        String colorInfo) {
        return getColorString(info, colorInfo, Color.RED);
    }

    public static String getString(String temp) {
        return TextUtils.isEmpty(temp) ? "" : temp;
    }

    /*
     * 将字符串"分"转换成"元"（长格式），如：100分被转换为1.00元。
     */
    public static String convertCent2Dollar(String s) {
        if (TextUtils.isEmpty(s))
            return "";

        long l = 0;
        if (s.length() != 0) {
            // try {
            if (s.charAt(0) == '+') {
                s = s.substring(1);
            }
            l = Long.parseLong(s);
            // } catch(Exception e) {
            // e.printStackTrace();
            // return "";
            // }
        } else {
            return "";
        }
        boolean negative = false;
        if (l < 0) {
            negative = true;
            l = Math.abs(l);
        }
        s = Long.toString(l);
        if (s.length() == 1)
            return (negative ? ("-0.0" + s) : ("0.0" + s));
        if (s.length() == 2)
            return (negative ? ("-0." + s) : ("0." + s));
        else
            return (negative ? ("-" + s.substring(0, s.length() - 2) + "." + s
                    .substring(s.length() - 2)) : (s.substring(0,
                    s.length() - 2) + "." + s.substring(s.length() - 2)));
    }

    /*
     * 将字符串"分"转换成"元"（短格式），如：100分被转换为1元。
     */
    public static String convertCent2DollarShort(String s) {

        if (TextUtils.isEmpty(s))
            return "";

        String ss = convertCent2Dollar(s);
        ss = "" + Double.parseDouble(ss);
        if (ss.endsWith(".0"))
            return ss.substring(0, ss.length() - 2);
        if (ss.endsWith(".00"))
            return ss.substring(0, ss.length() - 3);
        else
            return ss;
    }

    // 给TextView设置text，如果text为空则TextView不显示
    public static void setText(TextView v, String str) {
        if (TextUtils.isEmpty(str)) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
            v.setText(str);
        }
    }

    public static void appendText(TextView v, String str) {
        v.setText(v.getText() + str);

        if (TextUtils.isEmpty(v.getText())) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
    }

    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.size() == 0)
            return true;
        return false;
    }

    public static void toast(Context ctx, String info) {
        Toast.makeText(ctx, info, Toast.LENGTH_SHORT).show();
    }

}
