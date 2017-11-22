package ws.tilda.anastasia.shoppingcartapp.view;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


public class ExitDialogFragment extends DialogFragment {

    public static ExitDialogFragment newInstance(String message) {
        ExitDialogFragment fragment = new ExitDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ShoppingCartActivity shoppingCartActivity = (ShoppingCartActivity) getActivity();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle("Exit Shopping Cart?");
        alertDialogBuilder.setMessage(getArguments().getString("message"));
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shoppingCartActivity.finish();
                        dialog.dismiss();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return alertDialogBuilder.create();
    }
}
