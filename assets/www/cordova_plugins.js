cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/com.ibm.mobile.cordova.ibmbluemix/www/IBMBluemixHybrid.js",
        "id": "com.ibm.mobile.cordova.ibmbluemix.hybrid",
        "clobbers": [
            "IBMBluemix.hybrid"
        ]
    },
    {
        "file": "plugins/com.ibm.mobile.cordova.ibmdata/www/IBMDataHybrid.js",
        "id": "com.ibm.mobile.cordova.ibmdata.hybrid",
        "clobbers": [
            "IBMData.hybrid"
        ]
    },
    {
        "file": "plugins/com.ibm.mobile.cordova.ibmpush/www/IBMPushHybrid.js",
        "id": "com.ibm.mobile.cordova.ibmpush.hybrid",
        "clobbers": [
            "IBMPush.hybrid"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "org.apache.cordova.geolocation": "0.3.10",
    "com.ibm.mobile.cordova.ibmbluemix": "1.0.0-20141023-1310",
    "com.ibm.mobile.cordova.ibmdata": "1.0.0-20141023-1310",
    "com.ibm.mobile.cordova.ibmpush": "1.0.0-20141023-1310"
}
// BOTTOM OF METADATA
});