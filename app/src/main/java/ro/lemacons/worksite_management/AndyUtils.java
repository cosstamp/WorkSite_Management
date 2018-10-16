package ro.lemacons.worksite_management;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class AndyUtils {

    private static ProgressDialog mProgressDialog;
    private static void showSimpleProgressDialog(Context context, String title, String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading data...", false);
    }

    static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {

            return false;
        } else {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {

                for (NetworkInfo anInfo : info) {

                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}