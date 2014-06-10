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
		},
		handleAndroidPreferences: function (action, prefLib, prefName, prefValue, success, fail) {
			if (prefLib !== "" && prefName !== "") {
				if (action === "get") {				
					exec(function (returnValue) {
							console.info("PhoneGap Plugin: AndroidPreferences: callback success");
							value = returnValue;
							success(value);
						}, function () {
							console.error("PhoneGap Plugin: AndroidPreferences: callback error");
							value = "";
							fail(value);
						}, "AndroidPreferences", "get", [{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue}]);	
				} else if (action === "set") {				
						exec(function (returnValue) {
							console.info("PhoneGap Plugin: AndroidPreferences: callback success");
							value = "";
							success(value);
						}, function () {
							console.error("PhoneGap Plugin: AndroidPreferences: callback error");
							value = "";
							fail(value);
						}, "AndroidPreferences", "set", [{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue}]);
				} else if (action === "clear") {				
						exec(function (returnValue) {
							console.info("PhoneGap Plugin: AndroidPreferences: callback success");
							value = "";
							success(value);
						}, function () {
							console.error("PhoneGap Plugin: AndroidPreferences: callback error");
							value = "";
							fail(value);
						}, "AndroidPreferences", "clear", [{preferenceLib: prefLib, preferenceName: prefName, preferenceValue: prefValue}]);					
				} 
			}
 		}
    };
