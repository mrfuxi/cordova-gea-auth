package org.mrfuxi.cordova.gaeauth;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.AccountManager;
import android.accounts.Account;


public class GAEAuth extends CordovaPlugin {
    /**
    * Sets the context of the Command. This can then be used to do things like
    * get file paths associated with the Activity.
    *
    * @param cordova The context of the main Activity.
    * @param webView The CordovaWebView Cordova is running in.
    */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    /**
    * Executes the request and returns PluginResult.
    *
    * @param action            The action to execute.
    * @param args              JSONArry of arguments for the plugin.
    * @param callbackContext   The callback id used when calling back into JavaScript.
    * @return                  True if the action was valid, false if not.
    */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "getAccounts":
                callbackContext.success(getAccounts());
                break;
            case "getAuthToken":
                callbackContext.success(getAuthToken(args.getString(0)));
                break;
            default:
                callbackContext.error("GAEAuth." + action + " is not a supported function.");
                return false;
        }

        return true;
    }

    /*
        API
    */

    private JSONArray getAccounts() {
        JSONArray accounts = new JSONArray();

        for (Account account: getGoogleAccounts()) {
            JSONObject obj = new JSONObject();
            obj.put("type", a.type);
            obj.put("name", a.name);
            accounts.put(obj);
        }

        return accounts;
    }

    private String getAuthToken(String username) {
        Account account = getAccountForName(username);

        try {
            return accountManager.blockingGetAuthToken(account, "ah", true);
        } catch (AccountsException e) {
            System.out.println("AccountsException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

        return "";
    }

    /*
        Helper methods
    */

    private Account[] getGoogleAccounts() {
        AccountManager accountManager = AccountManager.get(cordova.getActivity().getApplicationContext());
        return accountManager.getAccountsByType("com.google");
    }

    private Account getAccountForName(String username) {
        Account[] accounts = getGoogleAccounts();
        if (accounts == null) {
            return null
        }

        for (Account account : accounts) {
            if (account.name.equals(username)) {
                return account;
            }
        }

        return null;
    }
}