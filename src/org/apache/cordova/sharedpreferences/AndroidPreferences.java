package org.apache.cordova.sharedpreferences;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alpinemetrics.utilities.KeyStore;
import com.alpinemetrics.utilities.SimpleCrypto;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
 
public class AndroidPreferences extends CordovaPlugin {
 
	public static final String TAG = "AndroidPreferences"; 
 
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		String preferenceLib = "";
		String preferenceName = "";
		String preferenceValue = "";
		SharedPreferences settings = null;
		SharedPreferences.Editor editor = null;
		try {
			JSONObject params = args.getJSONObject(0);
			preferenceLib = params.getString("preferenceLib");
			preferenceName = params.getString("preferenceName");
			preferenceValue = params.getString("preferenceValue");
			if (preferenceLib != null && preferenceName != null && preferenceValue != null && preferenceLib != "" && preferenceName != "") {
				settings = cordova.getActivity().getSharedPreferences(preferenceLib, Context.MODE_PRIVATE);
				editor = settings.edit();
			} else {
				Log.e(TAG, ": Error: " + PluginResult.Status.ERROR);
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR));
			}
			if (action.equals("set") && settings != null && editor != null) {	
				editor.putString(preferenceName, SimpleCrypto.encrypt(preferenceValue));
				editor.commit();
				Log.d(TAG, ": Set value: " + preferenceLib + ":" + preferenceName + ":" + settings.getString(preferenceName, ""));
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
				return true;
			} else if (action.equals("get") && settings != null && editor != null) {
				// preference value decryption
				
				String encrypted = settings.getString(preferenceName, "");
				if(encrypted.length() == 0) {
					throw new Exception("Session information not present");
				} 
				
				String returnValue = SimpleCrypto.decrypt(encrypted);
				Log.d(TAG, ": Get value: " + preferenceLib + ":" + preferenceName + ":" + returnValue);
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, returnValue));
				return true;
			} else if (action.equals("clear") && settings != null && editor != null) {
				
				//KeyStore ks = KeyStore.getInstance();
		    	//ks.delete("AlpineMetrics");
				
				editor.clear();
				editor.commit();
				Log.d(TAG, ": Clear values ");
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
				return true;
			} else {
				Log.e(TAG, "Error: " + PluginResult.Status.INVALID_ACTION);
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
				return false;
			}
		} catch (JSONException e) {
			// e.printStackTrace();
			Log.e(TAG, "Error: " + PluginResult.Status.JSON_EXCEPTION + ", Message - "+ e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION)); 
			return false;
		} catch (Exception e) {
			// e.printStackTrace();
			Log.e(TAG, "Error: " + PluginResult.Status.IO_EXCEPTION + ", Message - "+e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.IO_EXCEPTION));
			return false;
		}
	}
}