    var exec = require("cordova/exec");
  module.exports = {
        get: function (message, win, fail) {
			exec(win, fail, "AndroidPreferences", "get", [message]);
		},
		set: function (message, win, fail) {
			exec(win, fail, "AndroidPreferences", "set", [message]);
		}, 
		clear: function (message, win, fail) {
			exec(win, fail, "AndroidPreferences", "clear", [message]);
		}
    };



function handleAndroidPreferences(action, prefLib, prefName, prefValue, success, fail) {
	  var androidPref = cordova.require("cordova/plugin/androidpreferences"),
			value;
		if (prefLib !== "" && prefName !== "") {
			if (action === "get") {
				androidPref.get(
					{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue},
					function (returnValue) {
						console.info("PhoneGap Plugin: AndroidPreferences: callback success");
						value = returnValue;
						success(value);
					},
					function () {
						console.error("PhoneGap Plugin: AndroidPreferences: callback error");
						value = "";
						fail(value);
					}
				);
			} else if (action === "set") {
				androidPref.set(
					{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue},
					function () {
						console.info("PhoneGap Plugin: AndroidPreferences: callback success");
						value = "";
						success(value);
					},
					function () {
						console.error("PhoneGap Plugin: AndroidPreferences: callback error");
						value = "";
						fail(value);
					}
				);
			} else if (action === "clear") {
				androidPref.clear(
					{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue},
					function () {
						console.info("PhoneGap Plugin: AndroidPreferences: callback success");
						value="";
						success(value);
					},
					function () {
						console.error("PhoneGap Plugin: AndroidPreferences: callback error");
						value = "";
						fail(value);
					}
				);
			} 
		}
	}

//handle callback when doing nothing is needed
function androidPreferencesCallback() {
}