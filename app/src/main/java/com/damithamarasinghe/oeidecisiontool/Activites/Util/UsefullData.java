package com.damithamarasinghe.oeidecisiontool.Activites.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UsefullData implements Constants {

    private static Context _context;
    private ProgressDialog pDialog;

    public UsefullData(Context c) {
        _context = c;
    }

    // ================== DEVICE INFORMATION ============//

    public static String getCountryCodeFromDevice() {
        String countryCode = Locale.getDefault().getCountry();
        if (countryCode.equals("")) {
            countryCode = "IN";
        }
        return countryCode;
    }

    public String getDeviceId() {

        String deviceId = "";

        TelephonyManager telephonyManager = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            deviceId = telephonyManager.getDeviceId();
        } else {
            deviceId = Secure.getString(_context.getContentResolver(), Secure.ANDROID_ID);
        }
        Log("Your Device Id :" + deviceId);
        return deviceId;
    }


    public double getFormatedDouble(String number) {
        double d = 0;
        if (number.contains(",")) {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number value = null;
            try {
                value = format.parse(number);
            } catch (ParseException e) {
                e.printStackTrace();
                value = 0;
            }
            d = value.doubleValue();
        } else {
            d = Double.parseDouble(number);
        }
        return d;
    }


    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    // ================== GET TIME AND DATE ============//

    @SuppressLint("SimpleDateFormat")
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm aa");
        Calendar cal = Calendar.getInstance();
        // sdf.applyPattern("dd MMM yyyy");
        String strDate = sdf.format(cal.getTime());
        return strDate;
    }

    public boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }

    public boolean isValidUrl(String website) {
        boolean check;
        Pattern p;
        Matcher m;
        String WEBSITE_STRING = "(?:(?:https?|ftp|file):\\/\\/|www\\.|ftp\\.)(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[-A-Z0-9+&@#\\/%=~_|$?!:,.])*(?:\\([-A-Z0-9+&@#\\/%=~_|$?!:,.]*\\)|[A-Z0-9+&@#\\/%=~_|$])";

        p = Pattern.compile(WEBSITE_STRING);

        m = p.matcher(website);
        check = m.matches();

        return check;
    }

    public boolean isValidMobile(String phone) {
        boolean check = false;
        if (phone.length() < 6 || phone.length() > 13) {
            // if(phone.length() != 10) {
            check = false;

        } else {
            check = true;
        }
        return check;
    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    //Somewhere that has access to a context
    public void displayMessage(String toastString) {
        Toast.makeText(_context, toastString, Toast.LENGTH_LONG).show();
    }

    public static String getDateTime(String date) {

		/*
         * //2014-11-06 13:19:32 SimpleDateFormat sdf = new SimpleDateFormat(
		 * "dd MMM yyyy HH:mm aa"); Calendar cal = Calendar.getInstance(); //
		 * sdf.applyPattern("dd MMM yyyy"); String strDate = sdf.format(new
		 * Date(date)); return strDate;
		 */

        // String date = "2011/11/12 16:05:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS");
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm aa");
        String newFormat = formatter.format(testDate);
        // System.out.println(".....Date..."+newFormat);
        return newFormat;

    }


    public int dpToPx(int value) {
        DisplayMetrics displayMetrics = _context.getResources().getDisplayMetrics();
        int px = Math.round(value * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa");
        Calendar cal = Calendar.getInstance();
        // sdf.applyPattern("dd MMM yyyy");
        String strTime = sdf.format(cal.getTime());
        return strTime;

    }


    // ================== CREATE FILE AND RELATED ACTION ============//

//    public File getRootFile() {
//
//        File f = new File(Environment.getExternalStorageDirectory(), _context.getString(R.string.camere_image).toString());
//        if (!f.isDirectory()) {
//            f.mkdirs();
//        }
//
//        return f;
//    }

//    public void deleteRootDir(File root) {
//
//        if (root.isDirectory()) {
//            String[] children = root.list();
//            for (int i = 0; i < children.length; i++) {
//                File f = new File(root, children[i]);
//                Log("file name:" + f.getName());
//                if (f.isDirectory()) {
//                    deleteRootDir(f);
//                } else {
//                    f.delete();
//                }
//            }
//        }
//    }
//
//    public File createFile(String fileName) {
//        File f = null;
//        try {
//            f = new File(getRootFile(), fileName);
//            if (f.exists()) {
//                f.delete();
//            }
//
//            f.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return f;
//    }

    // ================ DOWNLOAD =================//

//    public void downloadAndDisplayImage(final String image_url, final ImageView v) {
//
//        new Thread() {
//
//            @Override
//            public void run() {
//                try {
//
//                    InputStream in = new URL(image_url).openConnection().getInputStream();
//                    Bitmap bm = BitmapFactory.decodeStream(in);
//                    File fileUri = new File(getRootFile(), getNameFromURL(image_url));
//                    FileOutputStream outStream = null;
//                    outStream = new FileOutputStream(fileUri);
//                    bm.compress(Bitmap.CompressFormat.JPEG, 75, outStream);
//                    outStream.flush();
//                } catch (MalformedURLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } finally {
//
//                    File f = new File(getRootFile(), "aa.jpg");
//                    if (f.exists()) {
//                        final Bitmap bmp = BitmapFactory.decodeFile(f.getPath());
//
//                        v.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                                v.setImageBitmap(bmp);
//                            }
//                        });
//
//                        Log("download images and showing ,,,,");
//
//                    }
//                }
//            }
//
//        }.start();
//    }
//
//    public String getNameFromURL(String url) {
//
//        String fileName = "item_image.jpg";
//        if (url != null) {
//            fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
//        }
//        return fileName;
//    }

    // ===================================//
    public String getFileName(String url) {

		/*
         * String str = String.valueOf(url.charAt(0)) +
		 * url.substring(url.length() / 2,url.length() / 2+2) +
		 * String.valueOf(url.charAt(url.length() - 1)) + "_" + url.length() +
		 * ".jpg";
		 */
        String str = url.substring(url.lastIndexOf('/') + 1, url.length());

        if (!str.endsWith(".jpg")) {
            str = str.replaceAll("[^\\w\\s\\-_]", "") + ".jpg";
        }

        UsefullData.Log("File name: " + str);
        return str;
    }

    // ================== LOG AND TOAST ====================//

    public static void Log(final String msg) {

/*		if (SHOW_LOG) {
            android.util.Log.e("Project Vacit", msg);
		}*/

    }

    public static void showMsgOnUI(final String msg) {
        ((Activity) _context).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // =================== INTERNET ===================//
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }


    public boolean GetGpsStatus() {
        LocationManager manager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return statusOfGPS;
    }


    // ==================== PROGRESS DIALOG ==================//

    public void showProgress(final String msg, final String title) {

        if (!((Activity) _context).isFinishing()) {
            pDialog = ProgressDialog.show(_context, "", msg, true);
        }

    }

    public void dismissProgress() {
        try {
            if (pDialog != null) {

                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                    pDialog = null;
                }
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.pDialog = null;
        }
    }

    // ==================== HIDE KEYBOARED ==================//
    public void hideKeyBoared() {

        InputMethodManager imm = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    // ====================SET FONT SIZE==================//
    public Typeface getUsedFontLucida() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(), "fonts/lucida_hand_writting.ttf");
        return typeFace;
    }

    public Typeface getUsedwebNewFont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(), "fonts/fontawesome-webfont.ttf");
        return typeFace;
    }


    public Typeface getUsedFontArial() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(), "fonts/arial.ttf");
        return typeFace;
    }

    public Typeface getPunjabiFont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(), "fonts/DroidSansFallback.ttf");
        return typeFace;
    }

    public Typeface getFontAppleChancery() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Apple Chancery.ttf");
        return typeFace;
    }

    public Typeface getChatterBolDfont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/chatter_bolds.ttf");
        return typeFace;
    }

    public Typeface getSinhalamnfont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Sinhala_MN.ttf");
        return typeFace;
    }

    public Typeface getArialfont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Arial.ttf");
        return typeFace;
    }

    public Typeface getbradley() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Bradley Hand Bold.ttf");
        return typeFace;
    }

    public Typeface getChalkboardSEfont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/ChalkboardSE Bold.ttf");
        return typeFace;
    }

    public Typeface getMerriweather_Regularfont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Merriweather_Regular.ttf");
        return typeFace;
    }

    public Typeface getGeorgiafont() {
        Typeface typeFace = Typeface.createFromAsset(_context.getAssets(),
                "fonts/Georgia.ttf");
        return typeFace;
    }


}
